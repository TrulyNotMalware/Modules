package dev.notypie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class AssistantApplication {
    public static void main(String[] args){
        SpringApplication.run(AssistantApplication.class, args);
    }
}
