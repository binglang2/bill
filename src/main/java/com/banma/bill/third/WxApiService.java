package com.banma.bill.third;

import com.alibaba.fastjson.JSON;
import com.banma.bill.util.HttpUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URI;
import java.net.URL;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author binglang
 * @since 2017/11/12
 */
@Slf4j
@Service
public class WxApiService {

    private static final String OAUTH2_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    private static final String USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN ";

    private static final String AUTH_CODE_SESSION = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

    @Value("${wx.appid}")
    private String appId;

    @Value("${wx.appsecret}")
    private String appSecret;

    /**
     * 微信开发文档
     * https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/login.html
     */
    public WxSessionKey authCode2Session(String code) {
        String url = String.format(AUTH_CODE_SESSION, appId, appSecret, code);
        try {
            String resp = HttpUtil.get(url);
            if (resp.contains("errmsg")) {
                log.error("authCode2Session error, resp={}", resp);
                return null;
            }
            return JSON.parseObject(resp, WxSessionKey.class);
        } catch (Exception e) {
            log.error("authCode2Session error, e=", e);
        }
        return null;
    }

    /**
     * 微信开发文档
     * https://developers.weixin.qq.com/doc/oplatform/Mobile_App/WeChat_Login/Authorized_API_call_UnionID.html
     */
    public WxAccessToken getWxAccessToken(String code) {
        String url = String.format(OAUTH2_TOKEN_URL, appId, appSecret, code);
        try {
            String resp = HttpUtil.get(url);
            if (resp.contains("errmsg")) {
                log.error("getWxAccessToken error, resp={}", resp);
                return null;
            }
            return JSON.parseObject(resp, WxAccessToken.class);
        } catch (Exception e) {
            log.error("getWxAccessToken error, e=", e);
        }
        return null;
    }

    public WxUserInfo getWxUserInfo(String openId, String accessToken) {
        String strUrl = String.format(USER_INFO_URL, accessToken, openId);
        try {
            URL url = new URL(strUrl);
            URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery());
            String resp = HttpUtil.get(uri.toString().replace("#", "?"));
            if (resp.contains("errmsg")) {
                log.error("getWxUserInfo error, resp={}", resp);
                return null;
            }
            return JSON.parseObject(resp, WxUserInfo.class);
        } catch (Exception e) {
            log.error("getWxUserInfo error, e=", e);
        }
        return null;
    }

    @ToString
    @Data
    public static class WxSessionKey {

        @JsonProperty("openid")
        private String openId;

        @JsonProperty("session_key")
        private String sessionKey;
    }

    @ToString
    @Data
    public static class WxAccessToken {

        @JsonProperty("openid")
        private String openId;

        @JsonProperty("access_token")
        private String accessToken;
    }

    @Data
    public static class WxUserInfo {

        @JsonProperty("openid")
        private String openId;

        @JsonProperty("nickame")
        private String nickName;

        private Byte sex;

        private String province;

        private String city;

        private String country;

        @JsonProperty("headimgurl")
        private String headImgUrl;

        private String privilege;

        @JsonProperty("unionid")
        private String unionId;
    }
}
