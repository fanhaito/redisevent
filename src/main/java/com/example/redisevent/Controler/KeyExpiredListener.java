package com.example.redisevent.Controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class KeyExpiredListener extends KeyExpirationEventMessageListener {

    @Autowired
    private RedisTemplate redisTemplate;

    private static final float[] W = {0.45f,0.35f,0.2f}; //随机添加，真实参数，参考文档定义

//    private static final Logger LOGGER = LoggerFactory.getLogger(KeyExpiredListener.class);
    public KeyExpiredListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel(),StandardCharsets.UTF_8);
        System.out.println("监听到失效的事件（原格式）："+channel);
        String key = new String(message.getBody(),StandardCharsets.UTF_8);
        String keyExpire = key.split("_")[0];
        System.out.println("redis事件监听触发后，计算综合评分");
        Map<String, String> scoreHash = (LinkedHashMap) redisTemplate.opsForHash().entries(keyExpire);
        //计算综合评分
        Float sensorScore = Float.parseFloat(scoreHash.get("sensor"));
        Float cardScore = Float.parseFloat(scoreHash.get("card"));
        Float fenceScore = Float.parseFloat(scoreHash.get("fence"));
        //计算综合评分
        Float score = sensorScore*W[0] + cardScore*W[1] + fenceScore*W[2];


        System.out.println("redis监听综合评分计算结果："+score);
        System.out.println("---------------------------------------------------------------------------");

//        System.out.println(key);
//        LOGGER.info("redis key 过期：pattern={},channel={},key={}",new String(pattern),channel,key);
    }
}