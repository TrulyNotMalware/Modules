package dev.notypie.lock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * This annotation is meant to be used for the purpose of distributed locking.
 * Reference from <a href="https://helloworld.kurly.com/blog/distributed-redisson-lock/">kurly tech blog</a>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributedLock {

    // Information of the key to be used for locking.
    String key();

    // The unit of waitTime and leaseTime. The default is seconds (SECONDS).
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    // The maximum waiting time to acquire the lock. The default is 5 seconds.
    long waitTime() default 5L;

    // The maximum time to hold the acquired lock. The default is 3 seconds.
    long leaseTime() default 3L;
}
