package com.hzh.reggie.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.security.RunAs;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MyTest {
    @Autowired
    private StringRedisTemplate template;
    @Test
    public void test(){
        HashOperations<String, Object, Object> hashOperations = template.opsForHash();

    }
}
