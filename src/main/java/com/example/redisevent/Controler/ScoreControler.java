package com.example.redisevent.Controler;


import com.example.redisevent.server.CardAsyncTaskService;
import com.example.redisevent.server.FenceAsyncTaskService;
import com.example.redisevent.server.SensorAsyncTaskService;
import entity.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class ScoreControler {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SensorAsyncTaskService sensorAsyncTaskService;
    @Autowired
    private CardAsyncTaskService cardAsyncTaskService;
    @Autowired
    private FenceAsyncTaskService fenceAsyncTaskService;

    private static final float[] W = {0.45f,0.35f,0.2f}; //随机添加，真实参数，参考文档定义



    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult queryScore(@RequestParam("deviceId") String deviceId) throws InterruptedException, ExecutionException {

        System.out.println("（1）移动端开始发起请求，设备号"+deviceId);
        Future<Float> sensor = sensorAsyncTaskService.executeAsyncTask(deviceId);
        Future<Float> card = cardAsyncTaskService.executeAsyncTask(deviceId);
        Future<Float> fence = fenceAsyncTaskService.executeAsyncTask(deviceId);

        sensor.get();
        card.get();
        fence.get();
        System.out.println("线程模拟数据完毕");
//        float b = sensorAsyncTaskService.executeAsyncTask();
//        float c = sensorAsyncTaskService.executeAsyncTask();
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("（2）开始综合计算");
        int size = Math.toIntExact(redisTemplate.opsForHash().size(deviceId));
        //获取银行卡分数
        String res = (String) redisTemplate.opsForHash().get(deviceId, "card");
        float cardRedis = Float.parseFloat(res);
        System.out.println("从redis中获得数据数量："+size);
        System.out.println("从redis中获得银行卡分数："+cardRedis);
        System.out.println("---------------------------------------------------------------------------");
        //判断是否满足综合评分的条件
        System.out.println("（3）综合评分计算规则条件：1. 满足综合评分的条件，则计算综合评分，2. 不满足，等待下一次判断或redis触发计算");

        JsonResult jsonResult = new JsonResult();
        jsonResult.setDeviceId(deviceId);
        if (size == 3 && cardRedis >= 0.6){
            System.out.println("满足综合评分计算条件");
            Map<String, String> scoreHash = (LinkedHashMap) redisTemplate.opsForHash().entries(deviceId);
            //计算综合评分
            Float sensorScore = Float.parseFloat(scoreHash.get("sensor"));
            Float cardScore = Float.parseFloat(scoreHash.get("card"));
            Float fenceScore = Float.parseFloat(scoreHash.get("fence"));
            //计算综合评分
            Float score = sensorScore*W[0] + cardScore*W[1] + fenceScore*W[2];
            redisTemplate.delete(deviceId+"_expire");
            jsonResult.setCode(200);
            jsonResult.setScore(score);

            System.out.println("综合评分计算结果："+score);
            System.out.println("---------------------------------------------------------------------------");
            return jsonResult;
        }else {
            System.out.println("不满足综合评分计算条件，等待下一次判断或是redis过期监听触发");
            jsonResult.setCode(205);
//            System.out.println("---------------------------------------------------------------------------");
            return jsonResult;

        }
        //判断是否满足综合评分的条件






//        float x1 = sensorAsyncTaskService.executeAsyncTask();
//        float x2 = sensorAsyncTaskService.executeAsyncTask();
//        System.out.println("x1"+x1);
//        System.out.println("x2"+x2);

//        sensorAsyncTaskService.executeAsyncTask()

//        String[] modelName = {"Sensor","Card","Fence"};
//        for (String s : modelName) {
//            System.out.println(s);
//            sensorAsyncTaskService.executeAsyncTask(s);
//        }

    }


}
