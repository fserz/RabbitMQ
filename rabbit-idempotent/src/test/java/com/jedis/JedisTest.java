package com.jedis;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.resps.Tuple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JedisTest {
    //value为String的情况
    @Test
    public void test01() {
        Jedis jedis = new Jedis("192.168.110.129", 6379);
        jedis.set("name", "lisi");
        jedis.mset("age", "18", "depart", "consumer");

        System.out.println("name = " + jedis.get("name"));
        System.out.println("age = " + jedis.get("age"));
        System.out.println("depart = " + jedis.get("depart"));
        jedis.close();
    }

    //value为Hash的情况
    @Test
    public void test02() {
        Jedis jedis = new Jedis("192.168.110.129", 6379);
        jedis.hset("emp", "name", "Tom");
        Map<String, String> map = new HashMap<>();
        map.put("age", "25");
        map.put("depart", "policy");
        jedis.hset("emp", map);

        System.out.println("emp.name = " + jedis.hget("emp", "name"));
        List<String> emp = jedis.hmget("emp", "name", "age", "depart");
        System.out.println("emp = " + emp);

        jedis.close();
    }

    //value为List的情况
    @Test
    public void test03() {
        Jedis jedis = new Jedis("192.168.110.129", 6379);
        jedis.lpush("cities", "bj", "sh");
        jedis.rpush("cities", "gz");
        List<String> cities = jedis.lrange("cities", 0, -1);
        System.out.println("cities = " + cities);
        jedis.close();
    }

    //value为Set的情况
    @Test
    public void test04() {
        Jedis jedis = new Jedis("192.168.110.129", 6379);
        jedis.sadd("courses", "Redis", "RocketMQ", "Zookeeper", "Kafka");
        jedis.sadd("courses", "Redis");
        Set<String> courses = jedis.smembers("courses");
        System.out.println(courses);
        jedis.close();
    }

    //value为ZSet的情况
    @Test
    public void test05() {
        Jedis jedis = new Jedis("192.168.110.129", 6379);
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

        jedis.close();
    }
}
