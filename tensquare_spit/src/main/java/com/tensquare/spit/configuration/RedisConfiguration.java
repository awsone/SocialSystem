package com.tensquare.spit.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Description:
 * @Author zhangchuan
 * @Date 2019/1/6 - 18:35
 * @Version 1.0
 */
@Configuration
public class RedisConfiguration {

	@Autowired
	private RedisTemplate redisTemplate;

	@Bean
	public RedisTemplate<String, Object> stringSerializerRedisTemplate() {
		RedisSerializer<String> stringSerializer = new StringRedisSerializer();
		redisTemplate.setKeySerializer(stringSerializer);
		//redisTemplate.setValueSerializer(stringSerializer);
		redisTemplate.setHashKeySerializer(stringSerializer);
		//redisTemplate.setHashValueSerializer(stringSerializer);
		return redisTemplate;
	}

}
