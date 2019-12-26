package com.dusun.belugemall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class BelugaMallConfigurationApplication {

    public static void main(String[] args) {
        SpringApplication.run(BelugaMallConfigurationApplication.class, args);
    }

}
