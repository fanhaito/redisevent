package com.example.redisevent.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Service
public class FenceAsyncTaskService{

    private static final long DEFAULT_EXPIRE = 3000; //设置过期时间 毫秒

    @Autowired
    private RedisTemplate redisTemplate;



    @Async
    public Future<Float> executeAsyncTask(String device ) throws InterruptedException {

        float fenceScore;


        Random random = new Random();//默认构造方法
        fenceScore = random.nextFloat();
        //所用时间为random.nextInt(1000)
        Integer useTime = random.nextInt(1000);
        Thread.sleep(random.nextInt(useTime));

        redisTemplate.opsForHash().put(device, "fence", String.valueOf(fenceScore));
//        System.out.println(redisTemplate.opsForHash().get("bbb", "sensor"));
//        System.out.println(redisTemplate.opsForHash().size("bbb"));
        String nameExpore = device + "_expire";
        redisTemplate.opsForValue().set(nameExpore, "1");
        redisTemplate.expire(nameExpore, DEFAULT_EXPIRE, TimeUnit.MILLISECONDS);
        //        System.out.println(redisTemplate.opsForValue().get("fan"));


        System.out.println("线程" + Thread.currentThread().getName() + " 设备号:" + device + " Get result score use time:" + useTime + " fenceScore：" + fenceScore + " save redis key：");
        return new AsyncResult<>(fenceScore);
    }

}
