package com.vps.demo.controller;

import com.vps.demo.po.Response;
import com.vps.demo.po.UserPo;
import com.vps.demo.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhulongkun20@163.com
 * @since 2021/4/12 6:19 AM
 */
@RestController
@RequestMapping("/api/user-management")
public class TestController {

    @Resource
    private UserService userService;

    @GetMapping("/users")
    public Response<List<UserPo>> listUsers(@RequestParam int count) {
        Response<List<UserPo>> response = new Response<>();
        response.setCode(0);
        response.setMessage("请求成功");
        response.setData(userService.listUsers(count));
        return response;
    }
}
