package ru.pfr.overpayments.aop.log;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.opfr.springbootstarterauthsso.security.UserInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.pfr.overpayments.model.overpayment.entity.log.Logi;
import ru.pfr.overpayments.model.overpayment.entity.log.TypeLog;
import ru.pfr.overpayments.service.overpayment.log.LogiService;

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

    @Pointcut("controllerRest() && controller()")
    public void controllerRestOrController() {
    }

    @Before("controllerRestOrController()")
    public void beforeAdvice(
            JoinPoint jp
    ) {
        UserInfo userInfo = getUserInfo();

        StringBuilder args = new StringBuilder();
        for (var arg :
                jp.getArgs()) {
            args.append(arg.toString()).append(" ");
        }

        service.save(
                Logi.builder()
                        .type(TypeLog.Info)
                        .text(String.format(
                                "Был вызван метод - %s, класса- %s, сигнатура- %s, аргументы- %s",
                                jp.getSignature().getName(),
                                jp.getSourceLocation().getWithinType().getName(),
                                jp.getSignature().toString(),
                                args
                        ))
                        .user(userInfo.getUsername())
                        .build()
        );
    }

    @AfterReturning(value = "controller()", returning = "returningValue")
    public void recordSuccessfulExecutionController(JoinPoint joinPoint, String returningValue) {
        UserInfo userInfo = getUserInfo();
        String text = "";
        if (returningValue != null) {
            text = String.format(
                    "Успешно выполнен метод - %s, класса- %s, с результатом выполнения - %s\n",
                    joinPoint.getSignature().getName(),
                    joinPoint.getSourceLocation().getWithinType().getName(),
                    (returningValue.length() > 100 ?
                            returningValue.substring(0, 100) :
                            returningValue) + "..."
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
                        .type(TypeLog.Success)
                        .text(text)
                        .user(userInfo.getUsername())
                        .build()
        );
    }

    @AfterReturning(value = "controllerRest()", returning = "returningValue")
    public void recordSuccessfulExecution(JoinPoint joinPoint, ResponseEntity<?> returningValue) {
        UserInfo userInfo = getUserInfo();
        String text = "";
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

    private UserInfo getUserInfo(){
        try{
            return (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }catch (Exception e){
            return new UserInfo("Anonim",null,"","");
        }
    }

/*    @Around(value = "controllerPackage()")
    public void beforeAdvice(JoinPoint jp, ProceedingJoinPoint joinPoint) {

        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        StringBuilder args = new StringBuilder();
        for (var arg :
                jp.getArgs()) {
            args.append(arg.toString()).append(" ");
        }
        String text = "Метод = " + jp.getSignature().getName() +
                " Сигнатура = " + jp.getSignature().toString() +
                " Аргументы = " + args;

        service.save(
                Logi.builder()
                        .type(TypeLog.Info)
                        .text("Вызов метода [" + text + "]")
                        .user(userInfo.getUsername())
                        .build()
        );
        try {
            joinPoint.proceed();
            service.save(
                    Logi.builder()
                            .type(TypeLog.Success)
                            .text("Метод успешно завершен [" + text + "]")
                            .user(userInfo.getUsername())
                            .build()
            );
        } catch (Throwable throwable) {
            service.save(
                    Logi.builder()
                            .type(TypeLog.Danger)
                            .text("Операция не удалась [" + text + "]")
                            .user(userInfo.getUsername())
                            .build()
            );
        }
    }*/

}
