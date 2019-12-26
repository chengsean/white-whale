package com.dusun.belugamall.configuration;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.config.RedirectConfig;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.junit4.SpringRunner;
import static io.restassured.config.RestAssuredConfig.config;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BelugaMallConfigurationApplicationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(BelugaMallConfigurationApplicationTests.class);

    private final FormAuthConfig formConfig = new FormAuthConfig("/login", "username", "password");
    private final String testUrl = "http://localhost:8000";

    @Before
    public void setup() {
        RestAssured.config = config().redirect(RedirectConfig.redirectConfig()
                .followRedirects(false));
    }

//    @Test
//    public void contextLoads() {
//        ResponseEntity<String> response;
//        TestRestTemplate testRestTemplate = new TestRestTemplate();
////        ResponseEntity<String> response = testRestTemplate
////                .getForEntity(testUrl, String.class);
////        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
////        Assert.assertNotNull(response.getBody());
//        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
//        form.add("username", "configuration");
//        form.add("password", "PHy34MK81czrzdja");
//        response = testRestTemplate
//                .postForEntity(testUrl + "/home/index.html", form, String.class);
//        String sessionCookie = response.getHeaders().get("Set-Cookie")
//                .get(0).split(";")[0];
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Cookie", sessionCookie);
//        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
//
//        response = testRestTemplate.exchange(testUrl,
//                HttpMethod.GET, httpEntity, String.class);
//        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
//        Assert.assertNotNull(response.getBody());
//    }

    @Test
    public void whenAccessAdminProtectedResourceThenForbidden() {
        Response response = RestAssured.get(testUrl + "/home/index.html");
        Assert.assertEquals(HttpStatus.FOUND.value(), response.getStatusCode());
        Assert.assertEquals(testUrl + "/login", response.getHeader("Location"));
    }

    @Test
    public void whenLoginAccessAdminProtectedResourceThenSuccess() {
        Response response = RestAssured.given()
                .auth().form("user", "password", formConfig)
                .get(testUrl + "/home/index.html");
        Assert.assertEquals(HttpStatus.FOUND.value(), response.getStatusCode());
        Assert.assertEquals(testUrl + "/login", response.getHeader("Location"));
    }

    @Test
    public void bcryptTest() {
        String rawPassword = "PHy34MK81czrzdja";
        String encodedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
        LOGGER.info("密码已加密："+encodedPassword);
        Assert.assertTrue(BCrypt.checkpw(rawPassword, encodedPassword));
    }
}
