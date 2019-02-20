package com.weina.myproject.controller;

import com.weina.exception.BusinessException;
import com.weina.mq.MqTag;
import com.weina.mq.MqTopic;
import com.weina.myproject.entity.MUser;
import com.weina.myproject.service.TestService;
import com.weina.redis.Redis;
import com.weina.redis.RedisKit;
import com.weina.web.ResultCodeEnum;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: mayc
 * @Date: 2019/02/12 16:54
 */
@RestController
public class TestController {

    @Autowired
    private TestService testService;
    @Autowired
    private DefaultMQProducer producer;

    @GetMapping("hello")
    public String hello() {
        return "什么东西？？";
    }

    @GetMapping("add")
    public String addTest(MUser mUser) {
        testService.addMUser(mUser);
        return "Yes, It's ok";
    }

    @PostMapping("update")
    public String update(MUser mUser) {
        testService.update(mUser);
        return "update ok";
    }

    @GetMapping("ex")
    public String exception() {
        testService.testException();
        return "yes";
    }

    @GetMapping("redis")
    public String redis() {
        RedisKit.use().setex("myc", 5*60, "怎么老是乱码！");
        String s = RedisKit.use().get("myc");
        return s;
    }

    @GetMapping("mq")
    public String mq() {
        System.out.println("发送消息开始！");
        Message message = new Message(MqTopic.TOPIC_MSG, MqTag.normal.type, "Hello World".getBytes());
        Message message1 = new Message(MqTopic.TOPIC_ORDER, MqTag.system.type, "开始有一个订单".getBytes());
        System.out.println("发送消息结果："+message.toString());
        try {
            producer.send(message);
            producer.send(message1);
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "OK";
    }
}
