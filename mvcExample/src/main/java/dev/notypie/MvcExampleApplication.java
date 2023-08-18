package dev.notypie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"dev.notypie.domains.order.domain"})
@SpringBootApplication
public class MvcExampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(MvcExampleApplication.class);
    }
}
