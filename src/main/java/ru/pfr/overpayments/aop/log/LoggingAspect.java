package ru.pfr.overpayments.aop.log;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.opfr.springbootstarterauthsso.security.UserInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.pfr.overpayments.model.overpayment.entity.log.Logi;
import ru.pfr.overpayments.model.overpayment.entity.log.TypeLog;
import ru.pfr.overpayments.service.overpayment.log.LogiService;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

@Component
@Aspect
@RequiredArgsConstructor
public class LoggingAspect {

    private final LogiService service;

    // любой тип в пакете ru.pfr.overpayments.controller или его подпакете
/*    @Pointcut("within(ru.pfr.overpayments.controller..*)")
    public void controllerPackage() {
    }*/

    //первый подстановочный знак соответствует любому возвращаемому значению,
    // второй соответствует любому имени методав пакете ru.pfr.overpayments.controller или его подпакете,
    // а шаблон (..) соответствует любому количеству параметров (ноль или более)
    @Pointcut("execution(public org.springframework.http.ResponseEntity ru.pfr.overpayments.controller..*(..))")
    public void controllerRest() {
    }

    @Pointcut("execution(public String ru.pfr.overpayments.controller..*(..))")
    public void controller() {
    }

    @Pointcut("controllerRest() || controller()")
    public void controllerRestOrController() {
    }

    @Before("controllerRestOrController()")
    public void beforeAdvice(
            JoinPoint joinPoint
    ) {
        UserInfo userInfo = getUserInfo();
        String nameClass = joinPoint.getSourceLocation().getWithinType().getName();
        String nameMethod = joinPoint.getSignature().getName();
        String url = " url= " + getUrl(nameClass, nameMethod) + ", ";

        StringBuilder args = new StringBuilder();
        for (var arg :
                joinPoint.getArgs()) {
            args.append(arg.toString()).append(" ");
        }

        String beginStr = switch (getType(nameClass, nameMethod)) {
            case GET -> "Запрос на получение данных по адресу - [GET]" + url + " в методе";
            case POST -> "Запрос на получение данных по адресу - [POST]" + url + " в методе";
            case PUT -> "Запрос на обновление данных по адресу - " + url + " в методе";
            case DELETE -> "Запрос на удаление данных по адресу - " + url + " в методе";
            default -> "Был вызван метод";
        };

        service.save(
                Logi.builder()
                        .type(TypeLog.Info)
                        .text(String.format(
                                "%s - %s, класса- %s, сигнатура- %s, аргументы- %s",
                                beginStr,
                                nameMethod,
                                nameClass,
                                joinPoint.getSignature().toString(),
                                args
                        ))
                        .user(userInfo.getUsername())
                        .build()
        );
    }

    @AfterReturning(value = "controller()", returning = "returningValue")
    public void recordSuccessfulExecutionController(JoinPoint joinPoint, String returningValue) {
        UserInfo userInfo = getUserInfo();
        String text;
        String nameClass = joinPoint.getSourceLocation().getWithinType().getName();
        String nameMethod = joinPoint.getSignature().getName();
        String url = getUrl(nameClass, nameMethod);

        String beginStr = switch (getType(nameClass, nameMethod)) {
            case GET -> "Успешно выполнен запрос на получение данных по адресу - [GET]" + url + " в методе";
            case POST -> "Успешно выполнен запрос на получение данных по адресу - [POST]" + url + " в методе";
            case PUT -> "Успешно выполнен запрос на обновление данных по адресу - " + url + " в методе";
            case DELETE -> "Успешно выполнен запрос на удаление данных по адресу - " + url + " в методе";
            default -> "Успешно выполнен был вызван метод";
        };

        if (returningValue != null) {
            text = String.format(
                    "%s - %s, класса- %s, с результатом выполнения - %s\n",
                    beginStr,
                    joinPoint.getSignature().getName(),
                    nameClass,
                    (returningValue.length() > 100 ?
                            returningValue.substring(0, 100) :
                            returningValue) + "..."
            );
        } else {
            text = String.format(
                    "%s - %s, класса- %s\n",
                    beginStr,
                    joinPoint.getSignature().getName(),
                    joinPoint.getSourceLocation().getWithinType().getName()
            );
        }
        service.save(
                Logi.builder()
                        .type(TypeLog.Success)
                        .text(text)
                        .user(userInfo.getUsername())
                        .build()
        );
    }

    @AfterReturning(value = "controllerRest()", returning = "returningValue")
    public void recordSuccessfulExecution(JoinPoint joinPoint, ResponseEntity<?> returningValue) {
        UserInfo userInfo = getUserInfo();
        String text;
        TypeLog typeLog = TypeLog.Success;
        if (returningValue != null) {
            typeLog = returningValue.getStatusCodeValue() == 200 ?
                    TypeLog.Success : TypeLog.Warning;
            text = String.format(
                    "Успешно выполнен метод - %s, класса- %s, с результатом выполнения - %s\n",
                    joinPoint.getSignature().getName(),
                    joinPoint.getSourceLocation().getWithinType().getName(),
                    returningValue.getBody() == null ? "" :
                            ((returningValue.getBody().toString().length() > 100 ?
                                    returningValue.getBody().toString().substring(0, 100) :
                                    returningValue.getBody().toString()) + "...")
            );
        } else {
            text = String.format(
                    "Успешно выполнен метод - %s, класса- %s\n",
                    joinPoint.getSignature().getName(),
                    joinPoint.getSourceLocation().getWithinType().getName()
            );
        }
        service.save(
                Logi.builder()
                        .type(typeLog)
                        .text(text)
                        .user(userInfo.getUsername())
                        .build()
        );
    }

    @AfterThrowing(value = "controllerRestOrController()", throwing = "exception")
    public void recordFailedExecution(JoinPoint joinPoint, Exception exception) {
        UserInfo userInfo = getUserInfo();

        service.save(
                Logi.builder()
                        .type(TypeLog.Danger)
                        .text(String.format("Метод - %s, класса- %s, был аварийно завершен с исключением - %s\n",
                                joinPoint.getSignature().getName(),
                                joinPoint.getSourceLocation().getWithinType().getName(),
                                exception))
                        .user(userInfo.getUsername())
                        .build()
        );

    }

    private UserInfo getUserInfo() {
        try {
            return (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return new UserInfo("Anonim", null, "", "");
        }
    }

    private String getUrl(String nameClass, String nameMethod) {
        StringBuilder url = new StringBuilder();
        try {
            Class<?> thisClass = Class.forName(nameClass);
            RequestMapping thisClassAnnotation = thisClass.getAnnotation(RequestMapping.class);
            url.append(Arrays.toString(thisClassAnnotation.value()));
            Method thisMethod;
            for (Method method :
                    thisClass.getMethods()) {
                if(method.getName().equals(nameMethod)){
                    thisMethod = method;
                    switch (getType(thisMethod)) {
                        case GET -> {
                            GetMapping getMapping = thisMethod.getAnnotation(GetMapping.class);
                            url.append(Arrays.toString(getMapping.value()));
                        }
                        case POST -> {
                            PostMapping getMapping = thisMethod.getAnnotation(PostMapping.class);
                            url.append(Arrays.toString(getMapping.value()));
                        }
                        case DELETE -> {
                            DeleteMapping getMapping = thisMethod.getAnnotation(DeleteMapping.class);
                            url.append(Arrays.toString(getMapping.value()));
                        }
                        case PUT -> {
                            PutMapping getMapping = thisMethod.getAnnotation(PutMapping.class);
                            url.append(Arrays.toString(getMapping.value()));
                        }
                        default -> {
                        }
                    }
                    break;
                }
            }
            return url.toString();
        } catch (Exception e) {
            return "";
        }
    }

    private RequestMethod getType(String nameClass, String nameMethod) {
        try {
            Class<?> thisClass = Class.forName(nameClass);

            Method thisMethod;
            for (Method method :
                    thisClass.getMethods()) {
                if(method.getName().equals(nameMethod)){
                    thisMethod = method;
                    return getType(thisMethod);
                }
            }

            return RequestMethod.TRACE;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private RequestMethod getType(Method method) {
        Annotation[] thisMethodAnnotations = method.getAnnotations();
        for (Annotation a :
                thisMethodAnnotations) {
            if (a.toString().contains("GetMapping")) {
                return RequestMethod.GET;
            } else if (a.toString().contains("PutMapping")) {
                return RequestMethod.PUT;
            } else if (a.toString().contains("DeleteMapping")) {
                return RequestMethod.DELETE;
            } else if (a.toString().contains("PostMapping")) {
                return RequestMethod.POST;
            }
        }
        return RequestMethod.TRACE;
    }


}
