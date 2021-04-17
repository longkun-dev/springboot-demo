package com.vps.demo.service;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;

/**
 * @author zhulongkun20@163.com
 * @since 2021/4/16 12:47 AM
 */
public interface RocketMqConsumerService {

    /**
     * rocketmq 消费者配置
     *
     * @return defaultRocketMqConsumer
     */
    DefaultMQPushConsumer consumer();

}
