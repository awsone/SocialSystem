package com.tensquare.test;

import com.tensquare.rabbit.RabbitSpringApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description:
 * @Author zhangchuan
 * @Date 2019/1/14 - 22:30
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitSpringApplication.class)
public class ProductTest {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	/**
	 * 生产者
	 * 简单模式
	 */
	@Test
	public void sendMsg() {
		rabbitTemplate.convertAndSend("socialTest", "直接模式测试");
	}

	/**
	 * 分裂模式
	 */
	@Test
	public void sendMsg1() {
		rabbitTemplate.convertAndSend("jiaohuanji", "", "分裂模式");
	}


	/**
	 * 主题模式
	 */
	@Test
	public void sendMsg3() {
		rabbitTemplate.convertAndSend("topictest", "good.abc.log", "主题模式测试");
	}
}
