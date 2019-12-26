package com.dusun.belugamall.security.config;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;

/**
 * 撤销令牌的框架端点
 * @author 程绍壮
 * @date 2019-10-23 00:36
*/
@FrameworkEndpoint
public class RevokeTokenEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(RevokeTokenEndpoint.class);
    @Resource(name = "tokenServices")
    ConsumerTokenServices tokenServices;


    @RequestMapping(method = RequestMethod.DELETE, value = "/oauth/token")
    @ResponseBody
    public void revokeToken(HttpServletRequest request) {
        String accessToken = request.getHeader("AccessToken");
        if (accessToken != null) {
            if (this.tokenServices.revokeToken(accessToken)) {
                LOG.info("access_token "+accessToken+" has been revoked!");
            }
            else {
                LOG.warn("can't revoke access_token "+accessToken);
            }
        }
    }

//    private String decodeForClientIdByHeader(String preString, String authorization) {
//        String auth = authorization.substring(preString.length() + 1);
//        String decode = new String(Base64.getDecoder().decode(auth));
//        if (decode.contains(":")) {
//            return decode.split(":")[0];
//        }
//        return null;
//    }
//
//    private String getValues(Object object) {
//        StringBuilder stringValue = new StringBuilder();
//        if (object instanceof String[]) {
//            String[] values = (String[])object;
//            for (int i = 0; i < values.length; i++) {
//                stringValue.append(i == 0 ? values[i] : ","+values[i]);
//            }
//        }
//        return stringValue.toString();
//    }

}