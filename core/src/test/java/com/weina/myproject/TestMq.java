package com.weina.myproject;

import com.weina.mq.MqTag;
import com.weina.mq.MqTopic;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description:
 * @Author: mayc
 * @Date: 2019/02/18 17:11
 */
public class TestMq {

    @Autowired
    private DefaultMQProducer producer;
    @Autowired
    private DefaultMQPushConsumer consumer;

    @Test
    public void testMq() {
        Message message = new Message(MqTopic.TOPIC_MSG, MqTag.normal.type, "Hello World".getBytes());
        try {
            producer.send(message);
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.fail();
    }

}
