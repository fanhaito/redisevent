package com.example.redisevent;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class RediseventApplicationTests {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() {
        redisTemplate.opsForHash().put("fanhaitao2", "sensor","0.455");
        redisTemplate.opsForHash().put("fanhaitao2", "card","0.45556");
        System.out.println(redisTemplate.opsForHash().get("fanhaitao2","card"));
//        System.out.println(redisTemplate.opsForValue().get("aaa"));
//        redisTemplate.opsForValue().decrement("aaa");
//        System.out.println(redisTemplate.opsForValue().get("aaa"));

//
////        redisTemplate.opsForValue().decrement("bbb");
//        Map<String, String> bbb = redisTemplate.opsForHash().entries("bbb");
//        System.out.println(bbb);
//        System.out.println(bbb.get("sensor"));
////        Float sen = bbb.get("sensor");
////        System.out.println(sen);
//        System.out.println(redisTemplate.opsForHash().size("bbb").getClass());
//        redisTemplate.expire("bbb",5000 , TimeUnit.MILLISECONDS);
//        //        System.out.println(redisTemplate.opsForValue().get("fan"));

    }
    @Test
    public void test001(){
        Date date1 = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

//         ft.format(date1)
//        time = System.currentTimeMillis();
//        1585987087400
//        1585987119349
//        1585987149557
        long a = System.currentTimeMillis();


//        System.out.println(ft.format(date1).getClass());
        System.out.println(a);

    }

    @Test
    public void test002(){
//        String[] modelName = {"Sensor","Card","Fence","MerId"};
//        Random random = new Random();
////        for (String s : modelName) {
////            System.out.println(s+random.nextFloat());
////
////        }
//        for (int i = 0; i < 20; i++) {
//            System.out.println(random.nextInt(100));
//
//        }
////        System.out.println(random.nextInt(1000));

        float[] W = {0.45f,0.35f,0.2f};
//        for (float v : W) {
//
//        }
        System.out.println(W[0]);

    }

}
