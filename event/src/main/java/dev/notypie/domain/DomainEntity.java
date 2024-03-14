package dev.notypie.domain;

import org.axonframework.spring.stereotype.Aggregate;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Aggregate
@Inherited
public @interface DomainEntity {

}