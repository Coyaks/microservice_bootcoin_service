package com.skoy.bootcamp_microservices.config;

import com.skoy.bootcamp_microservices.model.BootCoinWallet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public ReactiveRedisTemplate<String, BootCoinWallet> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<BootCoinWallet> serializer = new Jackson2JsonRedisSerializer<>(BootCoinWallet.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, BootCoinWallet> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, BootCoinWallet> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
}
