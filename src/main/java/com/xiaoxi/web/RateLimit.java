package com.xiaoxi.web;

import com.xiaoxi.enums.Rate;
import com.xiaoxi.service.RateHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by YanYang on 2016/6/25.
 */
@Controller
@RequestMapping(value = "/limit")
public class RateLimit {
    private static final Logger LOGGER = LoggerFactory.getLogger(RateLimit.class);

    @Autowired
    private RateHandle rateHandle;

    @RequestMapping(value = "/", method = RequestMethod.POST,
                    produces = {"application/json;charset=utf-8"})
    public boolean limit(@RequestParam(value = "action")String action,
                         @RequestParam(value = "feature")String feature,
                         @RequestParam(value = "timestamp")long timestamp,
                         @RequestParam(value = "rate")String rate,
                         HttpServletResponse httpServletResponse) {

        String[] rateSplit = rate.split("/");
        boolean limit = false;
        try {
            if (rateSplit.length != 2) {
                httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "请核查 rate 参数设置");
            }
            String unit = rateSplit[1].toLowerCase();
            Rate rat = Rate.stateOf(unit);
            if (rat == null) {
                httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "请核查 rate 参数设置");
            }
            int number = Integer.parseInt(rateSplit[0]);
            limit = rateHandle.isLimit(action, feature, timestamp, number, rat);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

        return limit;
    }
}
