package com.xiaoxi.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sun.net.www.http.HttpClient;

import java.net.URL;

/**
 * Created by YanYang on 2016/6/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
        @ContextConfiguration({"classpath:spring/spring-web.xml",
        "classpath:spring/spring-service.xml"})
public class testRateLimit {
    @Test
    public void testRateLimit() throws Exception {

    }
}
