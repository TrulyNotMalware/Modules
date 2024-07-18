package dev.notypie.domain;

import java.lang.annotation.*;

@Documented
@Deprecated(forRemoval = true)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DomainRootEntity {

}