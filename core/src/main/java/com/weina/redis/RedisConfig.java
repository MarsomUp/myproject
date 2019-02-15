package com.weina.redis;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Description:
 * @Author: mayc
 * @Date: 2019/02/14 09:47
 */
@Configuration
@PropertySource("classpath:redis.properties")
public class RedisConfig implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisConfig.class);
    public static final String MAIN_REDIS_NAME = "mainRedis";

    @Value("${redis.host}")
    private String host = null;
    @Value("${redis.port}")
    private String port = null;
    @Value("${redis.timeout}")
    private String timeout  = null;
    @Value("${redis.password}")
    private String password = null;
    @Value("${redis.database}")
    private String database = null;
    @Value("${redis.client-name}")
    protected String clientName = null;
    protected String redisName = MAIN_REDIS_NAME;

    protected ISerializer serializer = null;
    protected IKeyNamingPolicy keyNamingPolicy = null;
    protected JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();


    @Override
    public void run(String... args) throws Exception {
        LOGGER.debug("开始初始化redis……");
        /*if (timeout != null) {
            int ms = timeout.indexOf("ms");
            timeout = timeout.substring(0, ms);
        }*/
        JedisPool jedisPool;
        if (StringUtils.isNotBlank(port) && StringUtils.isNotBlank(timeout) && StringUtils.isNotBlank(password) && StringUtils.isNotBlank(database) && StringUtils.isNotBlank(clientName))
            jedisPool = new JedisPool(jedisPoolConfig, host, Integer.valueOf(port), Integer.valueOf(timeout), password, Integer.valueOf(database), clientName);
        else if (StringUtils.isNotBlank(port) && StringUtils.isNotBlank(timeout) && StringUtils.isNotBlank(password) && StringUtils.isNotBlank(database))
            jedisPool = new JedisPool(jedisPoolConfig, host, Integer.valueOf(port), Integer.valueOf(timeout), password, Integer.valueOf(database));
        else if (StringUtils.isNotBlank(port) && StringUtils.isNotBlank(timeout) && StringUtils.isNotBlank(password))
            jedisPool = new JedisPool(jedisPoolConfig, host, Integer.valueOf(port), Integer.valueOf(timeout), password);
        else if (StringUtils.isNotBlank(port) && StringUtils.isNotBlank(timeout))
            jedisPool = new JedisPool(jedisPoolConfig, host, Integer.valueOf(port), Integer.valueOf(timeout));
        else if (StringUtils.isNotBlank(port))
            jedisPool = new JedisPool(jedisPoolConfig, host, Integer.valueOf(port));
        else
            jedisPool = new JedisPool(jedisPoolConfig, host);

        if (serializer == null)
            serializer = FstSerializer.me;
        if (keyNamingPolicy == null)
            keyNamingPolicy = IKeyNamingPolicy.defaultKeyNamingPolicy;

        Redis redis = new Redis(redisName, jedisPool, serializer, keyNamingPolicy);
        RedisKit.addRedis(redis);
    }

    public void setSerializer(ISerializer serializer) {
        this.serializer = serializer;
    }

    public void setKeyNamingPolicy(IKeyNamingPolicy keyNamingPolicy) {
        this.keyNamingPolicy = keyNamingPolicy;
    }

}
