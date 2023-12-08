package com.jedis;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

public class JedisTransactionTest {
    //JedisPooled中没有事务操作，只能使用Jedis和JedisPool操作事务
    private final JedisPool jedisPool = new JedisPool("192.168.110.135", 6379);

    @Test
    public void test01() {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set("name", "jack");
            jedis.mset("age", "23");
            Transaction tx = jedis.multi();

            try {
                tx.set("name", "leo");
                //模拟异常
                int i = 1 / 0;
                tx.mset("age", "35");
                tx.exec();
            } catch (Exception e) {
                //发生异常，全部回滚
                tx.discard();
            } finally {
                System.out.println("name = " + jedis.get("name"));
                System.out.println("age = " + jedis.get("age"));
            }
        }
    }

    @Test
    public void test02() {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set("name", "jack");
            jedis.mset("age", "23");
            Transaction tx = jedis.multi();

            try {
                tx.set("name", "leo");

                //模拟异常
                tx.incr("name");

                tx.mset("age", "35");
                tx.exec();
            } catch (Exception e) {
                //发生异常，全部回滚
                tx.discard();
            } finally {
                System.out.println("name = " + jedis.get("name"));
                System.out.println("age = " + jedis.get("age"));
            }
        }
    }

    @Test
    public void test03() {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.mset("age", "23");
            System.out.println("age before incr = " + jedis.get("age"));

            jedis.watch("age");

            Transaction tx = jedis.multi();

            try {
                tx.mset("age", "35");
                tx.exec();
            } catch (Exception e) {
                //发生异常，全部回滚
                tx.discard();
            } finally {
                System.out.println("age after incr= " + jedis.get("age"));
            }
        }
    }
}
