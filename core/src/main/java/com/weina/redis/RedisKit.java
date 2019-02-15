package com.weina.redis;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @Author: mayc
 * @Date: 2019/02/14 10:23
 */
public class RedisKit {
    static Redis mainRedis = null;
    private static final ConcurrentHashMap<String, Redis> cacheMap = new ConcurrentHashMap<String, Redis>();

    public static void addRedis(Redis redis) {
        if (redis == null)
            throw new IllegalArgumentException("cache can not be null");
        if (cacheMap.containsKey(redis.getName()))
            throw new IllegalArgumentException("The cache name already exists");

        cacheMap.put(redis.getName(), redis);
        if (mainRedis == null)
            mainRedis = redis;
    }

    /**
     * 提供一个设置设置主缓存 mainCache 的机会，否则第一个被初始化的 Cache 将成为 mainCache
     */
    public static void setMainRedis(String redisName) {
        if (StringUtils.isBlank(redisName))
            throw new IllegalArgumentException("cacheName can not be blank");
        redisName = redisName.trim();
        Redis cache = cacheMap.get(redisName);
        if (cache == null)
            throw new IllegalArgumentException("the cache not exists: " + redisName);

        RedisKit.mainRedis = cache;
    }

    public static Redis removeCache(String cacheName) {
        return cacheMap.remove(cacheName);
    }

    public static Redis use() {
        if (mainRedis == null) {
            return cacheMap.get(RedisConfig.MAIN_REDIS_NAME);
        }
        return mainRedis;
    }

    public static Redis use(String redisName) {
        return cacheMap.get(redisName);
    }

}
