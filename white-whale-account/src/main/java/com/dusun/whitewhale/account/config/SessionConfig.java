package com.dusun.whitewhale.account.config;//package com.dusun.mall.user.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

/**
 * TODO
 *
 * @author 程绍壮
 * @dateTime 2019-09-20 01:53
 */
@Configuration
@EnableRedisHttpSession
public class SessionConfig
        extends AbstractHttpSessionApplicationInitializer {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }
}