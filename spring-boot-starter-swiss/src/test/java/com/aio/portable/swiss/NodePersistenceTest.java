package com.aio.portable.swiss;

import com.aio.portable.swiss.middleware.zookeeper.ZooKeeperSugar;
import com.aio.portable.swiss.suite.storage.persistence.NodePersistence;
import com.aio.portable.swiss.suite.storage.persistence.file.FilePO;
import com.aio.portable.swiss.suite.storage.persistence.redis.RedisPO;
import com.aio.portable.swiss.suite.storage.persistence.zookeeper.ZooKeeperPO;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@TestComponent
public class NodePersistenceTest {

//    @Autowired
    public StringRedisTemplate stringRedisTemplate;

    @Test
    public void foobar() {
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        redisStandaloneConfiguration.setHostName("http://mecs.com");
//
//        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
////        redisConnectionFactory.setHostName("http://mecs.com");
//
////        new JedisConnectionFactory()
////        JedisClientConfiguration.builder().usePooling().poolConfig()
//        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(redisConnectionFactory);
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();


        Map<String, Object> map1 = new HashMap<>();
        map1.put("key1", 1111);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("key1", 1111);
        map2.put("key2", 2222);

        Map<String, Object> map3 = new HashMap<>();
        map3.put("key1", 1111);
        map3.put("key2", 2222);
        map3.put("key3", 3333);

        Map<String, Object> map4 = new HashMap<>();
        map4.put("key1", 1111);
        map4.put("key2", 2222);
        map4.put("key3", 3333);
        map4.put("key4", 4444);

        ZooKeeper build = ZooKeeperSugar.build("10.124.202.121:32181", 60000, null);
//        ZooKeeperSugar.clearIfExists(build, "/database1/group-group-task");
        NodePersistence po;
        po = ZooKeeperPO.singletonInstance(build);
        ((ZooKeeperPO) po).setDatabase("database1");
//        po(map1, map2, map3, map4, po);

        po = RedisPO.singletonInstance(stringRedisTemplate, "database1");
        po(map1, map2, map3, map4, po);

        po = FilePO.singletonInstance("database1");
        po(map1, map2, map3, map4, po);
    }

    private void po(Map<String, Object> map1, Map<String, Object> map2, Map<String, Object> map3, Map<String, Object> map4, NodePersistence po) {
        po.set("table1", map1);
        po.set("table2", map2, "table1");
        po.set("table21", map2, "table1");
        po.set("table3", map3, "table1", "table2");
        po.set("table31", map3, "table1", "table2");
        po.set("table4", map4, "table1", "table2", "table3");
        po.set("table41", map4, "table1", "table2", "table3");

        Map<String, Map> all = po.getAll("table1", Map.class);
        List<String> table1s = po.getChildren("table1");
        List<String> table2s = po.getChildren("table2", "table1");
        Map<String, Map> table2 = po.getAll("table2", Map.class, "table1");
        List<String> tables = po.tables();

        po.remove("table41", "table1", "table2", "table3");
        po.remove("table31", "table1", "table2");

        po.clear("table1");
        po.clearDatabase();
    }
}
