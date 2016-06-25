package com.xiaoxi.web;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.ws.http.HTTPException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by YanYang on 2016/6/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
        @ContextConfiguration({"classpath:spring/spring-web.xml",
        "classpath:spring/spring-service.xml"})
public class testRateLimit {
    @Test
    public void testRateLimit() throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost("http://localhost:8080/limit/");

        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("action", "POST"));
        params.add(new BasicNameValuePair("feature", "xiaoxi"));
        long timestamp = System.currentTimeMillis();
        params.add(new BasicNameValuePair("timestamp",String.valueOf(timestamp)));
        params.add(new BasicNameValuePair("rate", "5/s"));

        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params, "utf-8");

        httpPost.setEntity(urlEncodedFormEntity);

        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

        HttpEntity httpEntity = httpResponse.getEntity();

        String result = EntityUtils.toString(httpEntity, "utf-8");

        System.out.println("结果" + result);

        httpResponse.close();

        httpClient.close();
    }
}
