package dev.notypie.lock.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"local","test"}) //Test는 기본적으로 Embedded 로 실행.
@Configuration
public class LocalRedissonConfiguration {

    @Value("${core.embedded.redis.port:6379}")
    private int redisPort;

    private static final String REDIS_LOCAL_HOST = "redis://127.0.0.1";

    @Bean
    RedissonClient redissonClient(){
        Config config = new Config();
        config.useSingleServer().setAddress(REDIS_LOCAL_HOST+":"+this.redisPort);
        return Redisson.create(config);
    }
}
