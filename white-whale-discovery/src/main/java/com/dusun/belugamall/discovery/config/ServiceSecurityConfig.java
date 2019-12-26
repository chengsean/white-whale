package com.dusun.belugamall.discovery.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

/**
 * 服务注册中心spring security服务注册配置
 * @author 程绍壮
 * @dateTime 2019-09-22 22:44
 */
@Configuration
@EnableWebSecurity
@Order(1)
public class ServiceSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceSecurityConfig.class);
    private final Environment environment;

    public ServiceSecurityConfig(Environment environment) {
        this.environment = environment;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        String serverId = this.environment.getProperty("spring.security.service.registration.provider.id");
        String serverSecret = this.environment.getProperty("spring.security.service.registration.provider.secret");
        List<String> serverRoles = (List<String>) this.environment.getProperty(
                "spring.security.service.registration.provider.roles", List.class);
        if (serverId == null) {
            throw new NullPointerException();
        }
        if (serverSecret == null) {
            throw new NullPointerException();
        }
        if (serverRoles == null) {
            throw new NullPointerException();
        }
        if (!serverSecret.startsWith("{")) {
            LOG.warn("'spring.security.service.registration.provider.secret'" +
                    "该密码配置使用明文的方式存储，不建议用于生产环境！！");
        }
        auth.inMemoryAuthentication().withUser(serverId)
                .password(serverSecret).roles(serverRoles.toArray(new String[]{}));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and().requestMatchers().antMatchers("/eureka/**")
                .and().authorizeRequests().antMatchers("/eureka/**")
                .hasRole("SYSTEM").anyRequest().denyAll().and()
                .httpBasic().and().csrf().disable();
    }
}