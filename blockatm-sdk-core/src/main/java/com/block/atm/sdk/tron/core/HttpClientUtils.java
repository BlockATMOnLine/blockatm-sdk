package com.block.atm.sdk.tron.core;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author Lawrence
 * @description
 * @date 2023/12/1 下午1:38
 */
@Slf4j
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


    public static String doGet(String url, Map<String, String> header) throws Exception {
        HttpGet httpPost = new HttpGet(url);
        if (header != null && !header.isEmpty()){
            header.forEach((k,v)->{
                // 设置请求头
                httpPost.setHeader(k,v);
            });
        }
        return execute(httpPost);
    }

    private static String execute(HttpRequestBase request) throws IOException, ClientProtocolException {
        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(globalConfig).build();
        CloseableHttpResponse response = httpclient.execute(request);
        if (200 == response.getStatusLine().getStatusCode()) {
            return EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));
        } else {
            String data = EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));
            log.debug(data);
            return data;
        }
    }
}
