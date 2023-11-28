package dev.notypie.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@EnableCaching
@Configuration
public class CacheConfiguration {

    @Bean
    public CacheManager cacheManager(){
        List<CaffeineCache> caches = Arrays.stream(CacheType.values())
                .map(cacheType ->
                    new CaffeineCache(
                            cacheType.getCacheName(),
                            Caffeine.newBuilder().recordStats().expireAfterWrite(cacheType.getExpiredTimeSeconds(), TimeUnit.SECONDS)
                                    .maximumSize(cacheType.getCacheMaxSize()).build()
                    )).toList();

        SimpleCacheManager caffeineCacheManager = new SimpleCacheManager();
        caffeineCacheManager.setCaches(caches);
        return caffeineCacheManager;
    }
}
