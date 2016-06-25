package com.xiaoxi.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by YanYang on 2016/6/24.
 */
public final class RedisClient {
    private static JedisPool jedisPool; //非切片连接池

    static {
        init();
    }

    /**
     * 初始化非切片池
     */
    private static void init() {
        //池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(20);
        config.setMaxIdle(5);
        config.setMaxWaitMillis(10001);
        config.setTestOnBorrow(false);

        jedisPool = new JedisPool(config, "127.0.0.1", 6379);
    }

    public static void set(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(key, value);
        }
    }

    public static void set(String key, String value, int second) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.setex(key, second, value);
        }
    }

    public static String get(String key, String defaultValue) {
        String value = defaultValue;
        try (Jedis jedis = jedisPool.getResource()) {
            String temp = jedis.get(key);
            if (temp != null) {
                value = temp;
            }
        }
        return value;
    }

    public static void incrBy(String key, long count) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.incrBy(key, count);
        }
    }
}
