package ru.pfr.overpayments.config.multipart;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

//Этот класс использован для настройки сервера приложений, чтобы получить загружаемый файл
@Configuration
// делает возможным автоматический поиск классов с @Controller аннотацией
@ComponentScan
//приложение будет также обнаруживать MultipartConfigElement бин и
// делать его работоспособным для загрузки файла
@EnableAutoConfiguration
public class MyMultipartConfig {
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.parse("40MB"));
        factory.setMaxRequestSize(DataSize.parse("40MB"));
        return factory.createMultipartConfig();
    }
}
