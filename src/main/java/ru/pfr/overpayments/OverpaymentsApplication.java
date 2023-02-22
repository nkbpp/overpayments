package ru.pfr.overpayments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
public class OverpaymentsApplication {
	public static void main(String[] args) {
		SpringApplication.run(OverpaymentsApplication.class, args);
	}

}

@Configuration
@EnableJpaAuditing
class JpaAuditingConfiguration {
}

