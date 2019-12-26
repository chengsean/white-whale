package com.dusun.whitewhale.discovery;

import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@SpringBootTest
public class WhiteWhaleDiscoveryApplicationTests {

    @Test
    public void contextLoads() {
        LoggerFactory.getLogger(WhiteWhaleDiscoveryApplicationTests.class).info(new BCryptPasswordEncoder().encode("rjCjD1t3BQRpvcYvqs7Z"));
    }

}
