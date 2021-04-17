package com.vps.demo.controller;

import com.vps.demo.service.MessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhulongkun20@163.com
 * @since 2021/4/16 1:24 AM
 */
@RequestMapping("/mq")
@RestController
public class MessageController {

    @Resource
    private MessageService messageService;

    @GetMapping("/sendMsgMq")
    public void sendMsgMq(@RequestParam String message) {
        messageService.sendMqMessage(message);
    }
}
