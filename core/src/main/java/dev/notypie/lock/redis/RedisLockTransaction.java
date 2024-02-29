package dev.notypie.lock.redis;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Reference from <a href="https://helloworld.kurly.com/blog/distributed-redisson-lock/">kurly tech blog</a>
 */

public class RedisLockTransaction {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Object proceed(final ProceedingJoinPoint joinPoint) throws Throwable{
        return joinPoint.proceed();
    }
}
