package dev.notypie.cache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static dev.notypie.constants.CacheConstants.DEFAULT_CACHE;

@Getter
@RequiredArgsConstructor
public enum CacheType {
    //1day cache
    DEFAULT(DEFAULT_CACHE, 60*60*24, 1);

    private final String cacheName;
    private final int expiredTimeSeconds;
    private final int cacheMaxSize;
}
