package br.com.serasa.restapi.config;

import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    public static final String LISTA_SCORES = "lista_scores";

    @Bean
    public CacheManager cacheManagerModel(@Value("${cache.ttl:300}") int ttlInSeconds) {

        return new ConcurrentMapCacheManager(LISTA_SCORES) {
            @Override
            protected Cache createConcurrentMapCache(String name) {
                return new ConcurrentMapCache(name, CacheBuilder.newBuilder().expireAfterWrite(ttlInSeconds, TimeUnit.SECONDS)
                        .build().asMap(), true);
            }
        };
    }
}
