package com.weina.myproject.controller;

import com.weina.exception.BusinessException;
import com.weina.myproject.entity.MUser;
import com.weina.myproject.service.TestService;
import com.weina.redis.Redis;
import com.weina.redis.RedisKit;
import com.weina.web.ResultCodeEnum;
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
}
