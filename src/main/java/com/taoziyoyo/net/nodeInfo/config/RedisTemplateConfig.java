package com.taoziyoyo.net.nodeInfo.config;

import com.alibaba.fastjson2.support.spring6.data.redis.GenericFastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.nio.charset.StandardCharsets;
@Configuration
public class RedisTemplateConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 连接工厂
        template.setConnectionFactory(redisConnectionFactory);
        // key序列化
        template.setKeySerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
        // value序列化
        template.setValueSerializer(new GenericFastJsonRedisSerializer());
        // hash序列化
        template.setHashKeySerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
        template.setHashValueSerializer(new GenericFastJsonRedisSerializer());
        // 启用事务支持
        template.setEnableTransactionSupport(true);
        return template;
    }
}
