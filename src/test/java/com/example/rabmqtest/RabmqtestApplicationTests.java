package com.example.rabmqtest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabmqtestApplicationTests {

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Autowired
	AmqpAdmin amqpAdmin;

	@Test
	public void createExchange(){
		//创建交换器
		amqpAdmin.declareExchange(new DirectExchange("amqpadmin.exchange"));
		//创建队列（如果存在同名，则不创建）
		amqpAdmin.declareQueue(new Queue("amqpadmin.queue",true));
		//创建绑定规则   new Binding(目的地，目的地类型，交换器名字，路由件，参数头)
		amqpAdmin.declareBinding(new Binding("amqpadmin.queue", Binding.DestinationType.QUEUE,"amqpadmin.exchange","amqp.haha",null));
		//删除队列
		//amqpAdmin.deleteQueue("amqpadmin.queue");
		System.out.println("创建成功");
	}

	@Test
	public void contextLoads() {
		Map<String,Object> map = new HashMap<>();
		map.put("msg","first msg");
		map.put("data", Arrays.asList("hello world",123,true));
		rabbitTemplate.convertAndSend("exchange.direct","atguigu.news",map);
	}

	@Test
    public void receive(){
        Object o = rabbitTemplate.receiveAndConvert("atguigu.news");
        System.out.println(o);
    }

}
