package com.dusun.whitewhale.discovery.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * 服务注册中心spring security服务(dashboard)表盘配置
 * @author 程绍壮
 * @dateTime 2019-09-23 00:04
 */
@Configuration
public class ServiceAdminSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and().httpBasic().disable().authorizeRequests()
                .antMatchers(HttpMethod.GET, "/").hasRole("ADMIN")
                .antMatchers("/info", "/health").authenticated().anyRequest()
                .denyAll().and().csrf().disable();
    }
}
