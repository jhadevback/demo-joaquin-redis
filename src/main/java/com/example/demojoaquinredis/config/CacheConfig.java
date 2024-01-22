package com.example.demojoaquinredis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        return new LettuceConnectionFactory();
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10));
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }

//    @Value("${spring.data.redis.host}")
//    private String redisHost;
//
//    @Value("${spring.data.redis.port}")
//    private int redisPort;
//
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory(){
//        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(redisHost, redisPort);
//        return new LettuceConnectionFactory(configuration);
//    }
//
//    @Bean
//    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){
//
//        RedisCacheConfiguration cacheConfig = myDefaultCacheConfig(Duration.ofMinutes(10)).disableCachingNullValues();
//
//        return RedisCacheManager.builder(redisConnectionFactory())
//                .cacheDefaults(cacheConfig)
//                .withCacheConfiguration("findAll", myDefaultCacheConfig(Duration.ofMinutes(5)))
//                //.withCacheConfiguration("tutorial", myDefaultCacheConfig(Duration.ofMinutes(1)))
//                .build();
//
//    }
//
//    private RedisCacheConfiguration myDefaultCacheConfig(Duration duration) {
//        return RedisCacheConfiguration
//                .defaultCacheConfig()
//                .entryTtl(duration)
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
//    }


}
