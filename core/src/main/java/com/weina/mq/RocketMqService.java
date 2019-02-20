package com.weina.mq;

import com.weina.exception.MqException;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

/**
 * @Description:
 * @Author: mayc
 * @Date: 2019/02/18 15:54
 */
@Configuration
@PropertySource("classpath:rocketmq.properties")
public class RocketMqService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RocketMqService.class);
    private static final String ON = "on";
    private static final String OFF = "off";

    @Value("${rocketmq.producer.groupName}")
    private String productGroupName;
    @Value("${rocketmq.producer.namesrvAddr}")
    private String productNamesrvAddr;
    /**
     * 消息最大大小，默认4M
     */
    @Value("${rocketmq.producer.maxMessageSize}")
    private String maxMessageSize;
    /**
     * 消息发送超时时间，默认3秒
     */
    @Value("${rocketmq.producer.sendMsgTimeout}")
    private String sendMsgTimeout;
    /**
     * 消息发送失败重试次数
     */
    @Value("${rocketmq.producer.retryTimesWhenSendFailed}")
    private String retryTimesWhenSendFailed;
    @Value("${rocketmq.producer.isOnOff}")
    private String producerIsOnOff;

    @Value("${rocketmq.consumer.groupName}")
    private String consumerGroupName;
    @Value("${rocketmq.consumer.namesrvAddr}")
    private String consumerNamesrvAddr;
    @Value("${rocketmq.consumer.consumeThreadMin}")
    private String consumeThreadMin;
    @Value("${rocketmq.consumer.consumeThreadMax}")
    private String consumeThreadMax;
    @Value("${rocketmq.consumer.topics}")
    private String topics;
    @Value("${rocketmq.consumer.consumeMessageBatchMaxSize}")
    private String consumeMessageBatchMaxSize;
    @Value("${rocketmq.consumer.isOnOff}")
    private String consumerIsOnOff;
    private static int reConsumerTimes = 3;

    @Bean(name = "producer")
    public DefaultMQProducer producer() {
        if (this.producerIsOnOff.isEmpty() || (!this.producerIsOnOff.isEmpty() && this.producerIsOnOff.equals(OFF))) {
            LOGGER.debug("MqProducer未开启，将不启动RocketMqProducer");
            return new NoMQProducer();
        }
        if (this.productGroupName.isEmpty()) {
            throw new MqException();
        }
        if (this.productNamesrvAddr.isEmpty()) {
            throw new MqException();
        }
        DefaultMQProducer producer = new DefaultMQProducer(this.productGroupName);
        producer.setNamesrvAddr(this.productNamesrvAddr);
        if (!this.maxMessageSize.isEmpty()) {
            producer.setMaxMessageSize(Integer.valueOf(this.maxMessageSize));
        }
        if (!this.sendMsgTimeout.isEmpty()) {
            producer.setSendMsgTimeout(Integer.valueOf(this.sendMsgTimeout));
        }
        producer.setVipChannelEnabled(false);
        if (!this.retryTimesWhenSendFailed.isEmpty()) {
            producer.setRetryTimesWhenSendFailed(Integer.valueOf(this.retryTimesWhenSendFailed));
        }
        try {
            producer.start();
            LOGGER.debug("RocketMq started");
        } catch (MQClientException e) {
            LOGGER.error("RocketMq start failed", e);
        }
        return producer;
    }

    @Bean(name = "consumer")
    public DefaultMQPushConsumer consumer() {
        if (this.consumerIsOnOff.isEmpty() || (!this.consumerIsOnOff.isEmpty() && this.consumerIsOnOff.equals(OFF))) {
            LOGGER.debug("MqConsumer未开启，将不启动RockmtMqConsumer");
            return null;
        }
        if (this.consumerGroupName.isEmpty() || this.consumerNamesrvAddr.isEmpty()) {
            throw new MqException();
        }
        try {
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(this.consumerGroupName);
            consumer.setNamesrvAddr(this.consumerNamesrvAddr);
            if (!this.consumeThreadMin.isEmpty()) {
                consumer.setConsumeThreadMin(Integer.valueOf(this.consumeThreadMin));
            }
            if (!this.consumeThreadMax.isEmpty()) {
                consumer.setConsumeThreadMax(Integer.valueOf(this.consumeThreadMax));
            }
            consumer.setVipChannelEnabled(false);
            consumer.setMessageModel(MessageModel.CLUSTERING);
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            if (!this.consumeMessageBatchMaxSize.isEmpty()) {
                consumer.setConsumeMessageBatchMaxSize(Integer.valueOf(this.consumeMessageBatchMaxSize));
            }
            // 这里订阅相关主题的消息
            consumer.subscribe(MqTopic.TOPIC_MSG, "*");
            consumer.subscribe(MqTopic.TOPIC_ORDER, "*");
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    LOGGER.debug("开始消费消息！");
                    if (list.isEmpty()) {
                        LOGGER.info("接收的消息为空，不处理，直接返回成功！");
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                    for (MessageExt ext : list) {
                        if (ext.getTopic().equals(MqTopic.TOPIC_MSG) || ext.getTopic().equals(MqTopic.TOPIC_ORDER)) {
                            if (ext.getReconsumeTimes() > RocketMqService.reConsumerTimes) {
                                //消息已经重试了3次，如果不需要再次消费，则返回成功
                                //TODO("如果重试了三次还是失败则执行对于失败的业务逻辑")
                                LOGGER.info("消息重试消费失败：", ext);
                                continue;
                            }
                            byte[] body = ext.getBody();
                            String s = new String(body);
                            LOGGER.info(s);
                        }
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
            LOGGER.debug("Mq Consumer started");
            return consumer;
        } catch (MQClientException e) {
            LOGGER.error("Mq 消息订阅失败！");
        }
        return null;
    }

}
