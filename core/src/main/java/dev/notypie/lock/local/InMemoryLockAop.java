package dev.notypie.lock.local;

import dev.notypie.lock.LockAopService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class InMemoryLockAop implements LockAopService {

    @Override
    @Around("@annotation(dev.notypie.lock.DistributedLock)")
    public Object doLock(ProceedingJoinPoint joinPoint) throws Throwable {
        return null;
    }

}
