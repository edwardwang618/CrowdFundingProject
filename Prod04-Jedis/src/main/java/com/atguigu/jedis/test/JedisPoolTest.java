package com.atguigu.jedis.test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Protocol;

public class JedisPoolTest {
    
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = Protocol.DEFAULT_PORT;
        port = 6000;
    
        JedisPool jedisPool = new JedisPool(host, port);
        
        Jedis jedis = jedisPool.getResource();
    
        String ping = jedis.ping();
        System.out.println(ping);
        
        jedis.close();
        jedisPool.close();
    }
}
