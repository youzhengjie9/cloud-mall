package com.boot.async;

import com.alibaba.fastjson.JSONObject;
import com.boot.feign.order.notFallback.OrderFeign;
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

//订单消费者
@Component
public class OrderComsumer {

    @Autowired
    private OrderFeign orderFeign;


    @RabbitListener(bindings = {@QueueBinding(value = @Queue,exchange = @Exchange(name = "seckillOrder_exchange",
    type = "direct"),key = "seckillOrder_key")})
    public void seckillOrder(String msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG)long tag) throws IOException {

        try{
            JSONObject jsonObject = JSONObject.parseObject(msg);
            long addressid = Long.parseLong(String.valueOf(jsonObject.get("addressid")));
            long seckillsuccessid = Long.parseLong(String.valueOf(jsonObject.get("seckillsuccessid")));
            long seckillid = Long.parseLong(String.valueOf(jsonObject.get("seckillid")));
            long userid = Long.parseLong(String.valueOf(jsonObject.get("userid")));

            //执行
            orderFeign.seckillOrder(addressid,seckillsuccessid,seckillid,userid);
            channel.basicAck(tag,false);

        } catch (IOException e) {
            e.printStackTrace();
            channel.basicNack(tag,false,false);
        }

    }





}
