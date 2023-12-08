package com.jedis;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.resps.Tuple;

import java.util.*;

public class JedisClusterTest {
    private final JedisCluster jedisCluster;

    {
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.110.128", 6380));
        nodes.add(new HostAndPort("192.168.110.128", 6381));
        nodes.add(new HostAndPort("192.168.110.128", 6382));
        nodes.add(new HostAndPort("192.168.110.128", 6383));
        nodes.add(new HostAndPort("192.168.110.128", 6384));
        nodes.add(new HostAndPort("192.168.110.128", 6385));
        jedisCluster = new JedisCluster(nodes);
    }

    @Test
    public void test01() {
        jedisCluster.set("name", "randy");
        //跨槽时写如何读取都要加分组{xxx}
        jedisCluster.mset("age{emp}", "18", "depart{emp}", "consumer");
        System.out.println("name = " + jedisCluster.get("name"));
        System.out.println("age = " + jedisCluster.get("age{emp}"));
        System.out.println("depart = " + jedisCluster.get("depart{emp}"));
    }

    //value为Hash的情况
    @Test
    public void test02() {
        jedisCluster.hset("emp", "name", "Tom");
        Map<String, String> map = new HashMap<>();
        map.put("age", "25");
        map.put("depart", "policy");
        jedisCluster.hset("emp", map);

        System.out.println("emp.name = " + jedisCluster.hget("emp", "name"));
        List<String> emp = jedisCluster.hmget("emp", "name", "age", "depart");
        System.out.println("emp = " + emp);
    }

    //value为List的情况
    @Test
    public void test03() {
        jedisCluster.lpush("cities", "bj", "sh");
        jedisCluster.rpush("cities", "gz");
        List<String> cities = jedisCluster.lrange("cities", 0, -1);
        System.out.println("cities = " + cities);
    }

    //value为Set的情况
    @Test
    public void test04() {
        jedisCluster.sadd("courses", "Redis", "RocketMQ", "Zookeeper", "Kafka");
        jedisCluster.sadd("courses", "Redis");
        Set<String> courses = jedisCluster.smembers("courses");
        System.out.println(courses);
    }

    //value为ZSet的情况
    @Test
    public void test05() {
        jedisCluster.zadd("sales", 50, "BENZ");
        jedisCluster.zadd("sales", 40, "BMW");
        jedisCluster.zadd("sales", 30, "Tesla");
        jedisCluster.zadd("sales", 20, "BYD");
        jedisCluster.zadd("sales", 10, "Auto");

        List<String> sales = jedisCluster.zrevrange("sales", 0, 2);

        System.out.println("top3 = " + sales);

        List<Tuple> sales1 = jedisCluster.zrevrangeWithScores("sales", 0, -1);
        for (Tuple tuple : sales1) {
            System.out.println(tuple);
        }
    }
}
