package com.tensquare.rabbit.customer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author zhangchuan
 * @Date 2019/1/14 - 23:00
 * @Version 1.0
 */
@Component
@RabbitListener(queues = "socialTest2")
public class Customer2 {

	@RabbitHandler
	public void getMsg(String msg) {
		System.out.println("socialTest2:" + msg);
	}
}
