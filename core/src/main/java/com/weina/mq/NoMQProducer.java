package com.weina.mq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:
 * @Author: mayc
 * @Date: 2019/02/20 13:56
 */
public class NoMQProducer extends DefaultMQProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(NoMQProducer.class);

    @Override
    public SendResult send(Message msg) throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
        LOGGER.info("MQProducer服务未开启，无法使用消息中间件RockMq");
        return null;
    }
}
