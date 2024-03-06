package dev.notypie.lock.redis;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import java.io.IOException;


@Slf4j
@Profile({"local","test"})
@Configuration
public class EmbeddedRedis {

    @Value("${core.embedded.redis.port:6379}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void init() throws IOException {
        this.redisServer = new RedisServer(this.redisPort);
        this.redisServer.start();
        log.info("In-memory redis enabled at {}", this.redisPort);
    }

    @PreDestroy
    public void destroy(){
        this.redisServer.stop();
    }

}