package com.dusun.whitewhale.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class WhiteWhaleConfigurationApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhiteWhaleConfigurationApplication.class, args);
    }

}
