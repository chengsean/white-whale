package com.dusun.belugemall.configuration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

/**
 * 配置服务 spring security、redis 配置
 * @author 程绍壮
 * @dateTime 2019-09-20 01:53
 */
@EnableRedisHttpSession
public class ServerSessionConfig
        extends AbstractHttpSessionApplicationInitializer {
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }
}