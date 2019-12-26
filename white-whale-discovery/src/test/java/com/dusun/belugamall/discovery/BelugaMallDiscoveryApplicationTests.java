package com.dusun.belugamall.discovery;

import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@SpringBootTest
public class BelugaMallDiscoveryApplicationTests {

    @Test
    public void contextLoads() {
        LoggerFactory.getLogger(BelugaMallDiscoveryApplicationTests.class).info(new BCryptPasswordEncoder().encode("rjCjD1t3BQRpvcYvqs7Z"));
    }

}
