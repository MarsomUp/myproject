package com.weina.interceptor;

import com.weina.redis.Redis;
import com.weina.redis.RedisKit;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description:RedisInterceptor 用于在同一线程中共享同一个 jedis 对象，提升性能.
 * 目前只支持主缓存 mainCache，若想更多支持，参考此拦截器创建新的拦截器
 * 改一下Redis.use() 为 Redis.use(otherCache) 即可
 * @Author: mayc
 * @Date: 2019/02/14 10:30
 */
public class RedisInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Redis redis = getRedis();
        Jedis jedis = redis.getThreadLocalJedis();
        if (jedis == null) {
            try {
                jedis = redis.getJedisPool().getResource();
                redis.setThreadLocalJedis(jedis);
            } catch (RuntimeException e) {
                e.printStackTrace();
            } finally {
                redis.removeThreadLocalJedis();
                jedis.close();
            }
        }
        return true;
    }

    protected Redis getRedis() {
        return RedisKit.use();
    }
}
