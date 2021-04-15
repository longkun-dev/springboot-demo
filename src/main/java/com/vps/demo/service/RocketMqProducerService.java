package com.vps.demo.service;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

/**
 * @author zhulongkun20@163.com
 * @since 2021/4/16 12:28 AM
 */
public interface RocketMqService {

    DefaultMQProducer producer();

    DefaultMQPushConsumer consumer();
}
