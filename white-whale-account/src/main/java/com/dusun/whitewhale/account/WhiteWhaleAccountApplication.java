package com.dusun.whitewhale.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class WhiteWhaleAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhiteWhaleAccountApplication.class, args);
    }

}
