package com.jedis;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.resps.Tuple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JedisPooledTest {
    private final JedisPooled jedis = new JedisPooled("192.168.110.129", 6379);
    //value为String的情况
    @Test
    public void test01() {
        jedis.set("name", "randy");
        jedis.mset("age", "18", "depart", "consumer");
        System.out.println("name = " + jedis.get("name"));
        System.out.println("age = " + jedis.get("age"));
        System.out.println("depart = " + jedis.get("depart"));
    }

    //value为Hash的情况
    @Test
    public void test02() {
        jedis.hset("emp", "name", "Tom");
        Map<String, String> map = new HashMap<>();
        map.put("age", "25");
        map.put("depart", "policy");
        jedis.hset("emp", map);

        System.out.println("emp.name = " + jedis.hget("emp", "name"));
        List<String> emp = jedis.hmget("emp", "name", "age", "depart");
        System.out.println("emp = " + emp);
    }

    //value为List的情况
    @Test
    public void test03() {
        jedis.lpush("cities", "bj", "sh");
        jedis.rpush("cities", "gz");
        List<String> cities = jedis.lrange("cities", 0, -1);
        System.out.println("cities = " + cities);
    }

    //value为Set的情况
    @Test
    public void test04() {
        jedis.sadd("courses", "Redis", "RocketMQ", "Zookeeper", "Kafka");
        jedis.sadd("courses", "Redis");
        Set<String> courses = jedis.smembers("courses");
        System.out.println(courses);
    }

    //value为ZSet的情况
    @Test
    public void test05() {
        jedis.zadd("sales", 50, "BENZ");
        jedis.zadd("sales", 40, "BMW");
        jedis.zadd("sales", 30, "Tesla");
        jedis.zadd("sales", 20, "BYD");
        jedis.zadd("sales", 10, "Auto");

        List<String> sales = jedis.zrevrange("sales", 0, 2);

        System.out.println("top3 = " + sales);

        List<Tuple> sales1 = jedis.zrevrangeWithScores("sales", 0, -1);
        for (Tuple tuple : sales1) {
            System.out.println(tuple);
        }
    }
}
