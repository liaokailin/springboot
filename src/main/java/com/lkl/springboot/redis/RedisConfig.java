package com.lkl.springboot.redis;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * redis 配置
 * 
 * {@link RedisProperties}  //会自动加载配置
 * @author lkl
 * @version $Id: RedisConfig.java, v 0.1 2015年8月3日 下午1:31:09 lkl Exp $
 */
//@Configuration
@EnableCaching
//<cache:annotation-driven/>
public class RedisConfig {

    /**
     * 
     * 利用redis实现缓存
     * 也可以利用SimpleCacheManager
     */
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        cacheManager.setDefaultExpiration(300);
        return cacheManager;
    }

}
