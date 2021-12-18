package com.boot.async;

import com.alibaba.fastjson.JSONObject;
import com.boot.service.SeckillService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

//秒杀消费者
@Component
public class SeckillComsumer {

    @Autowired
    private SeckillService seckillService;

    @RabbitListener(bindings = {@QueueBinding(value = @Queue,
            exchange = @Exchange(name = "seckill_Exchange", type = "direct"),
            key = "seckill_key")})
    public void seckill(String msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG)long tag) throws IOException {

        try {
            JSONObject jsonObject = JSONObject.parseObject(msg);
            long userid = Long.parseLong(String.valueOf(jsonObject.get("userid")));
            long seckillId = Long.parseLong(String.valueOf(jsonObject.get("seckillId")));

            seckillService.doSeckill(seckillId,userid);
            //下面的配置就是为了解决消费者消费消息失败而重复消费消息，进入死循环的情况
            channel.basicAck(tag,false);

        } catch (Exception e) {
            e.printStackTrace();
            //参数1：消息的tag
            // 参数2：false  多条处理
            // 参数3：requeue重发  fasle 不会重发，会把消息打入死信队列（自己建立一个死信队列、）    true会进入死循环的重发（造成重复消费），建议true的情况下，不使用try  catch 否则造成循环
            channel.basicNack(tag,false,false);
        }


    }

}
