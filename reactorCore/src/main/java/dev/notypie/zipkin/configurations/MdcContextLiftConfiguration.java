package dev.notypie.zipkin.configurations;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Hooks;

@Configuration
public class MdcContextLiftConfiguration {

    @PostConstruct
    public void contextOperatorHook(){
        Hooks.enableAutomaticContextPropagation();
    }

    @PreDestroy
    public void cleanupHook(){
        Hooks.resetOnEachOperator();
    }
}
