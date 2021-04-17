package com.vps.demo.service.impl;

import com.vps.demo.constant.ConstantValue;
import com.vps.demo.service.RocketMqConsumerService;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * @author zhulongkun20@163.com
 * @since 2021/4/16 12:48 AM
 * <p>
 * rocketmq消费者配置
 */
@Service
public class RocketMqConsumerServiceImpl implements RocketMqConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(RocketMqConsumerServiceImpl.class);

    @Value("${rocketmq.consumer.groupName}")
    private String groupName;

    @Value("${rocketmq.consumer.namesrvAddr}")
    private String namesrvAddr;

    @Value("${rocketmq.consumer.consumeThreadMin}")
    private Integer consumeThreadMin;

    @Value("${rocketmq.consumer.consumeThreadMax}")
    private Integer consumeThreadMax;

    @Value("${rocketmq.consumer.topics}")
    private String topics;

    @Value("${rocketmq.consumer.consumeMessageBatchMaxSize}")
    private Integer consumeMessageBatchMaxSize;

    @Value("${rocketmq.consumer.reConsumeTimes}")
    private Integer reConsumeTimes;

    @Bean
    @Override
    public DefaultMQPushConsumer consumer() {
        if (StringUtils.isEmpty(groupName)) {
            logger.error("groupName is empty");
            throw new RuntimeException("groupName is empty");
        }
        if (StringUtils.isEmpty(namesrvAddr)) {
            logger.error("namesrvAddr is empty");
            throw new RuntimeException("namesrvAddr is empty");
        }
        String topic = ConstantValue.ROCKET_MQ_TOPIC_MAIL;
        String tag = ConstantValue.ROCKET_MQ_TAG_MAIL_REGISTER;
        try {
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
            consumer.setNamesrvAddr(namesrvAddr);
            consumer.setConsumeThreadMin(consumeThreadMin);
            consumer.setConsumeThreadMax(consumeThreadMax);
            consumer.setVipChannelEnabled(false);
            consumer.setConsumeMessageBatchMaxSize(consumeMessageBatchMaxSize);
            consumer.setMessageModel(MessageModel.CLUSTERING);
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
            consumer.subscribe(topic, tag);

            consumer.registerMessageListener((MessageListenerConcurrently) (messageList
                    , consumeConcurrentlyContext) -> {
                if (messageList == null || messageList.isEmpty()) {
                    logger.info("接受到的消息为空，不处理，直接返回成功");
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                MessageExt messageExt = messageList.get(0);
                logger.info("接收到的消息为: {}", messageExt.toString());
                if (topic.equals(messageExt.getTopic()) && tag.equals(messageExt.getTags())) {
                    // 判断该消息是否重复消费（RocketMQ不保证消息不重复，如果你的业务需要保证严格的不重复消息，需要你自己在业务端去重）
                    // 获取该消息重试次数
                    if (messageExt.getReconsumeTimes() >= reConsumeTimes) {
                        // 消息已经重试了3次，如果不需要再次消费，则返回成功
                        // 如果重试了三次还是失败则执行对于失败的业务逻辑")
                        logger.error("消息重试消费失败: {}", messageExt);
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    } else {
                        // 如果失败重试次数还没到三次则继续重试
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                }
                // 开始正常的业务逻辑
                System.out.println(StringUtils.repeat(":", 30)
                        + new String(messageExt.getBody(), StandardCharsets.UTF_8));
                //消费成功
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
            consumer.start();
            logger.info("rocketMq Consumer start success; namesrvAddr: {}, groupName: {}, topics: {}"
                    , namesrvAddr, groupName, topics);
            return consumer;
        } catch (Exception e) {
            logger.error("rocketMq Consumer start fail;{}", e.getMessage(), e);
            return new DefaultMQPushConsumer();
        }
    }
}
