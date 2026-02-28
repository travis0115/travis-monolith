package com.travis.monolith.module.demo.webmvc.internal.controller;


import com.travis.infrastructure.framework.jackson.core.JsonUtils;
import com.travis.infrastructure.framework.web.core.model.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {
    private final RedisTemplate<String, Object> redisTemplate;

    public TestController(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/test")
    public ApiResponse<?> test(HttpServletRequest request, HttpServletResponse response) {
        // 准备参数
        var desensitizeDemo = new DesensitizeDemo();
        desensitizeDemo.setNickname("芋道源码");
        desensitizeDemo.setBankCard("9988002866797031");
        desensitizeDemo.setCarLicense("粤A66666");
        desensitizeDemo.setFixedPhone("01086551122");
        desensitizeDemo.setIdCard("530321199204074611");
        desensitizeDemo.setPassword("123456");
        desensitizeDemo.setPhoneNumber("13248765917");
        desensitizeDemo.setSlider1("ABCDEFG");
        desensitizeDemo.setSlider2("ABCDEFG");
        desensitizeDemo.setSlider3("ABCDEFG");
        desensitizeDemo.setEmail("travis0115@163.com");
        desensitizeDemo.setRegex("你好，我是芋道源码");
        desensitizeDemo.setOrigin("芋道源码");

        // 调用
        DesensitizeDemo d = JsonUtils.parseObject(JsonUtils.toJsonString(desensitizeDemo), DesensitizeDemo.class);


        return ApiResponse.success(desensitizeDemo);
    }

}
