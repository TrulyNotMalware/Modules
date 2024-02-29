package dev.notypie.lock;

import org.aspectj.lang.ProceedingJoinPoint;

public interface LockAopService {

    public Object doLock(final ProceedingJoinPoint joinPoint) throws Throwable;
}
