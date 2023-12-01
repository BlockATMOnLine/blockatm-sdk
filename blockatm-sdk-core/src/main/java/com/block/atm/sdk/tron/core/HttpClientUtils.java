package com.block.atm.sdk.tron.core;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

/**
 * @author Lawrence
 * @description
 * @date 2023/12/1 下午1:38
 */
public class HttpClientUtils {


    /**
     * POST发送json信息
     *
     * @param url      请求地址
     * @param jsonBody json格式请求体
     * @return
     */
    public static String postJson(String url, String jsonBody) throws IOException {
        String result = "";
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            BasicResponseHandler handler = new BasicResponseHandler();
            StringEntity entity = new StringEntity(jsonBody, "utf-8");//解决中文乱码问题
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            result = httpClient.execute(httpPost, handler);
            return result;
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                httpClient.close();
            } catch (Exception e) {
                throw e;
            }
        }
    }
}
