package com.dusun.belugamall.security;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

// need both oauth-authorization-server and oauth-resource-server-1 to be running

public class TokenRevocationLiveTest {


    private static final String OAUTH_SERVER_ROOT_URL       =   "http://localhost:8082/spring-security-oauth-server";
    private static final String OAUTH_SERVER_TOKEN_URL      =   OAUTH_SERVER_ROOT_URL+"/oauth/token";
    private static final String OAUTH_SERVER_AUTHORIZE_URL  =   OAUTH_SERVER_ROOT_URL+"/oauth/authorize";
    private static final String OAUTH_RESOURCE_ROOT_URL     =   "http://localhost:8082/spring-security-oauth-resource";
    private static final String OAUTH_RESOURCE_FOOS_URL     =   OAUTH_RESOURCE_ROOT_URL+"/foos";
    private static final String OAUTH_CLIENT_ID             =   "fooClientIdPassword";
    private static final String USERNAME                    =   "john";
    private static final String PASSWORD                    =   "123";
    private static final int FOO_ID =   100;

    private static final Logger LOG = LoggerFactory.getLogger(TokenRevocationLiveTest.class);

    @Test
    public void whenObtainingAccessToken_thenCorrect() {
        final Response authServerResponse = obtainAccessToken(OAUTH_CLIENT_ID, USERNAME, PASSWORD);
        final String accessToken = authServerResponse.jsonPath().getString("access_token");
        assertNotNull(accessToken);

        //  authorizeClient("fooClientIdPassword", "john", "123");

        final Response resourceServerResponse = RestAssured.given().header("Authorization", "Bearer " + accessToken).get(OAUTH_RESOURCE_FOOS_URL);
        assertThat(resourceServerResponse.getStatusCode(), equalTo(200));
    }

    //

    private Response obtainAccessToken(String clientId, String username, String password) {
        final Map<String, String> params = new HashMap<>();
        params.put("grant_type", "password");
        params.put("client_id", clientId);
        params.put("username", username);
        params.put("password", password);
        return RestAssured.given().auth().preemptive().basic(clientId, "secret").and().with().params(params).when().post(OAUTH_SERVER_TOKEN_URL);
        // response.jsonPath().getString("refresh_token");
        // response.jsonPath().getString("access_token")
    }

    private String obtainRefreshToken(String clientId, final String refreshToken) {
        final Map<String, String> params = new HashMap<>();
        params.put("grant_type", "refresh_token");
        params.put("client_id", clientId);
        params.put("refresh_token", refreshToken);
        final Response response = RestAssured.given().auth().preemptive().basic(clientId, "secret").and().with().params(params).when().post(OAUTH_SERVER_TOKEN_URL);
        return response.jsonPath().getString("access_token");
    }

    private void authorizeClient(String clientId, String username, String password) {
        final Map<String, String> params = new HashMap<>();
        params.put("response_type", "code");
        params.put("client_id", clientId);
        params.put("scope", "read,write");
        params.put("username", username);
        params.put("password", password);
        final Response response = RestAssured.given().auth().preemptive().basic(clientId, "secret").and().with().params(params).when().post(OAUTH_SERVER_AUTHORIZE_URL);
        LOG.info(response.jsonPath().toString());
    }

    @Test
    public void whenDeleteAccessTokenThenClientUnauthorized() {
        // authorize 用户授权
        final Response authServerResponse = obtainAccessToken(OAUTH_CLIENT_ID, USERNAME, PASSWORD);
        final String accessToken = authServerResponse.jsonPath().getString("access_token");
        assertNotNull(accessToken);
        LOG.info("client: "+ OAUTH_CLIENT_ID +" and user: "+ USERNAME +" has been authorized.");

        // Access resource 用户访问资源
        final Response resourceServerResponse = RestAssured.given().header("Authorization", "Bearer " + accessToken).get(OAUTH_RESOURCE_FOOS_URL + "/" + FOO_ID);
        assertThat(resourceServerResponse.getStatusCode(), equalTo(200));
        LOG.info("client: "+ OAUTH_CLIENT_ID +" and user: "+ USERNAME +" Access resource success.");

        // delete Access token 删除访问令牌
        assertThat(this.deleteToken(OAUTH_CLIENT_ID, USERNAME, PASSWORD, accessToken).getStatusCode(), equalTo(200));
    }


    private Response deleteToken(String clientId, String username, String password, String accessToken) {
        final Map<String, String> params = new HashMap<>();
        params.put("grant_type", "password");
        params.put("client_id", clientId);
        params.put("username", username);
        params.put("password", password);
        final Response response = RestAssured.given().header("Authorization", "Bearer " + accessToken).auth().preemptive().basic(clientId, "secret").and().with().params(params).when().delete(OAUTH_SERVER_ROOT_URL);
        return response;
    }
}