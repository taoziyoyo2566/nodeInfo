//package com.taoziyoyo.net.nodeInfo.config;
//
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.connection.DataType;
//import org.springframework.data.redis.connection.RedisConnection;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.*;
//
//import java.time.Duration;
//import java.util.Arrays;
//import java.util.Objects;
//import java.util.Set;
//import java.util.concurrent.TimeUnit;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//public class RedisTemplateConfigTest {
//
//    Logger logger = LoggerFactory.getLogger(RedisTemplateConfigTest.class);
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
//
//    /**
//     * 操作String类型的对象
//     */
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//    @BeforeEach
//    void setUp() {
//        // 在每个测试方法执行前清除 Redis 数据
////        Set<String> keys = redisTemplate.keys("*");
////        Objects.requireNonNull(keys).forEach(a -> logger.info(a));
//        redisTemplate.getConnectionFactory().getConnection().flushAll();
//    }
//
//    @Test
//    void test() {
//        // RedisTemplate 可以获取对应数据类型的 XxxOperations
//        ValueOperations<String, Object> objectValueOperations = redisTemplate.opsForValue();
//        ListOperations<String, Object> objectListOperations = redisTemplate.opsForList();
//        SetOperations<String, Object> objectSetOperations = redisTemplate.opsForSet();
//        HashOperations<String, String, Object> objectHashOperations = redisTemplate.opsForHash();
//        ZSetOperations<String, Object> objectZSetOperations = redisTemplate.opsForZSet();
//
//        // 准备数据
//        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
//        stringValueOperations.set("key1", "hello1");
//        stringValueOperations.set("key2", "hello2");
//        stringValueOperations.set("key3", "hello3");
//        stringValueOperations.set("key4", "hello4");
//
//        // KEYS 查找键
//        Set<String> keys = redisTemplate.keys("*");
//        assertNotNull(keys);
//        assertEquals(4, keys.size());
//
//        // EXISTS 键是否存在
//        Boolean hasKey = redisTemplate.hasKey("key1");
//        assertTrue(hasKey);
//        Long existingKeys = redisTemplate.countExistingKeys(Arrays.asList("key1", "key2"));
//        assertEquals(2, existingKeys);
//
//        // TYPE 查看键的类型
//        assertEquals(DataType.STRING, redisTemplate.type("key1"));
//
//        // DEL 删除键
//        Boolean deleteFalse = redisTemplate.delete("key");
//        assertFalse(deleteFalse);
//        Boolean deleteSuccess = redisTemplate.delete("key1");
//        assertTrue(deleteSuccess);
//        Long batchDelete = redisTemplate.delete(Arrays.asList("key", "key2"));
//        assertEquals(1, batchDelete);
//
//        // UNLINK 删除键（异步）
//        Boolean unlink = redisTemplate.unlink("key3");
//        assertTrue(unlink);
//        Long batchUnlink = redisTemplate.unlink(Arrays.asList("key1", "key2"));
//        assertEquals(0, batchUnlink);
//
//        Long expire = redisTemplate.getExpire("key4");
//        // TTL 查看键剩余生成秒
//        assertEquals(-1, expire);
//
//        // EXPIRE 设置键到期秒
//        redisTemplate.expire("key4", 10, TimeUnit.SECONDS);
//        redisTemplate.expire("key4", Duration.ofSeconds(10));
//        assertEquals(10, redisTemplate.getExpire("key4"));
//
//        // PERSIST key 设置键永不过期
//        redisTemplate.persist("key4");
//        assertEquals(-1, redisTemplate.getExpire("key4"));
//
//        // 删除数据
//        assertTrue(redisTemplate.delete("key4"));
//    }
//}