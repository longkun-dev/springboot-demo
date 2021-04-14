package com.vps.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhulongkun20@163.com
 * @since 2021/4/12 6:19 AM
 */
@RestController
@RequestMapping("/api")
public class TestController {

    @RequestMapping("/test")
    public String test(@RequestParam String param) {
        String result = "test success!";
        if (param != null && !"".equals(param)) {
            result = result + " param is " + param;
        }
        return result;
    }
}
