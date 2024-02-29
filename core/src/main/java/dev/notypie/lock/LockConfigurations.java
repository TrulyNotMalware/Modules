package dev.notypie.lock;

import dev.notypie.lock.local.InMemoryLockAop;
import dev.notypie.lock.redis.DistributedLockAop;
import dev.notypie.lock.redis.RedisLockTransaction;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LockConfigurations {

    @Bean
    @ConditionalOnProperty(prefix = "spring.data", name = "redis")
    public RedisLockTransaction redisLockTransaction(){
        return new RedisLockTransaction();
    }

    @Bean
    @ConditionalOnProperty(prefix = "spring.data", name = "redis")
    public LockAopService lockAopService(
            RedissonClient redissonClient,
            RedisLockTransaction redisLockTransaction
    ){
        return new DistributedLockAop(redissonClient, redisLockTransaction);
    }

    @Bean
    @ConditionalOnMissingBean(LockAopService.class)
    public LockAopService defaultLockAopService(){
        return new InMemoryLockAop();
    }
}
