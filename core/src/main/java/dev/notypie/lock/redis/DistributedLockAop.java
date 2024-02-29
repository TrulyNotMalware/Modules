package dev.notypie.lock.redis;

import dev.notypie.lock.DistributedLock;
import dev.notypie.lock.LockAopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 * Reference from <a href="https://helloworld.kurly.com/blog/distributed-redisson-lock/">kurly tech blog</a>
 */
@Aspect
@RequiredArgsConstructor
@Slf4j
public class DistributedLockAop implements LockAopService {
    private static final String REDISSON_LOCK_PREFIX = "LOCK:";

    private final RedissonClient redissonClient;
    private final RedisLockTransaction redisLockTransaction;


    @Override
    @Around("@annotation(dev.notypie.lock.DistributedLock)")
    public Object doLock(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DistributedLock redisLock = method.getAnnotation(DistributedLock.class);

        String key = REDISSON_LOCK_PREFIX + this.getDynamicValue(signature.getParameterNames(), joinPoint.getArgs(), redisLock.key());
        //Get lock
        RLock rLock = redissonClient.getLock(key);

        try {
            boolean available = rLock.tryLock(redisLock.waitTime(), redisLock.leaseTime(), redisLock.timeUnit());
            if (!available) {
                return false;
            }
            return this.redisLockTransaction.proceed(joinPoint);
        } catch (InterruptedException e) {
            throw new InterruptedException();
        } finally {
            try {
                rLock.unlock();
            } catch (IllegalMonitorStateException e) {

            }
        }
    }


    private Object getDynamicValue(String[] parameterNames, Object[] args, String key) {
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();

        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }

        return parser.parseExpression(key).getValue(context, Object.class);
    }
}
