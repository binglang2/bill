package com.banma.bill.util;

import com.alibaba.fastjson.JSONObject;
import java.nio.charset.StandardCharsets;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @author binglang
 * @since 2017/11/10
 */
public class HttpUtil {

    private static final CloseableHttpClient HTTP_CLIENT = HttpClients.createDefault();

    public static String postJson(String url, Object obj) throws Exception {
        String body = JSONObject.toJSONString(obj);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("content-type", "application/json;charset=UTF-8");
        httpPost.setEntity(new StringEntity(body, "utf-8"));
        return execute(httpPost);
    }

    public static String get(String url) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        return execute(httpGet);
    }

    private static String execute(HttpUriRequest httpUriRequest) throws Exception {
        CloseableHttpResponse response = HTTP_CLIENT.execute(httpUriRequest);
        String result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        EntityUtils.consume(response.getEntity());
        return result;
    }

}
