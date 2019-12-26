package com.dusun.whitewhale.discovery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

/**
 * 服务注册中心 spring security、redis 配置
 * @author 程绍壮
 * @dateTime 2019-09-20 01:53
 */
@Configuration
@EnableRedisHttpSession
public class ServiceSessionConfig
        extends AbstractHttpSessionApplicationInitializer {
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }
}