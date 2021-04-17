package com.vps.demo.service.impl;

import com.vps.demo.constant.ConstantValue;
import com.vps.demo.service.MessageService;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * @author zhulongkun20@163.com
 * @since 2021/4/16 1:19 AM
 */
@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Resource
    private DefaultMQProducer producer;

    @Override
    public void sendMqMessage(String msg) {
        Message message = new Message(ConstantValue.ROCKET_MQ_TOPIC_MAIL
                , ConstantValue.ROCKET_MQ_TAG_MAIL_REGISTER
                , msg.getBytes(StandardCharsets.UTF_8));
        try {
            producer.send(message);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
