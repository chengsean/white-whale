package com.dusun.belugamall.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class BelugaMallDiscoveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(BelugaMallDiscoveryApplication.class, args);
    }

}
