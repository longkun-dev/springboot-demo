package com.vps.demo.service.impl;

import com.vps.demo.service.RocketMqService;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * @author zhulongkun20@163.com
 * @since 2021/4/16 12:27 AM
 * <p>
 * rocketmq生产者配置
 */
@Service
public class RocketMqServiceImpl implements RocketMqService {

    private static final Logger logger = LoggerFactory.getLogger(RocketMqServiceImpl.class);

    @Value("${rocketmq.producer.groupName}")
    private String groupName;

    @Value("${rocketmq.producer.namesrvAddr}")
    private String namesrvAddr;

    @Value("${rocketmq.producer.maxMessageSize}")
    private Integer maxMessageSize;

    @Value("${rocketmq.producer.sendMsgTimeOut}")
    private Integer sendMsgTimeOut;

    @Value("${rocketmq.producer.retryTimesWhenSendFailed}")
    private Integer retryTimesWhenSendFailed;

    @Bean
    @Override
    public DefaultMQProducer producer() {
        if (StringUtils.isEmpty(groupName)) {
            logger.error("groupNema is empty");
            throw new RuntimeException("groupName is empty");
        }
        if (StringUtils.isEmpty(namesrvAddr)) {
            logger.error("namesrvAddr is empty");
            throw new RuntimeException("naemsrvAddr is empty");
        }
        DefaultMQProducer producer = new DefaultMQProducer(groupName);
        producer.setNamesrvAddr(namesrvAddr);
        producer.setMaxMessageSize(maxMessageSize);
        producer.setSendMsgTimeout(sendMsgTimeOut);
        producer.setRetryTimesWhenSendFailed(retryTimesWhenSendFailed);
        producer.setVipChannelEnabled(false);
        try {
            producer.start();
            logger.info("rocketMq Producer start success; nameServer:{},producerGroupName:{}",
                    namesrvAddr, groupName);
        } catch (Exception e) {
            logger.info("rocketMq Producer start fail; {}", e.getMessage(), e);
        }
        return producer;
    }

    @Bean
    @Override
    public DefaultMQPushConsumer consumer() {
        return null;
    }
}
