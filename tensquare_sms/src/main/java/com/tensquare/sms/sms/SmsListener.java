package com.tensquare.sms.sms;

import cn.hutool.core.map.MapUtil;
import com.aliyuncs.exceptions.ClientException;
import com.tensquare.sms.util.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description:
 * @Author zhangchuan
 * @Date 2019/1/15 - 14:54
 * @Version 1.0
 */
@Component
@RabbitListener(queues = "sms")
public class SmsListener {

	@Autowired
	private SmsUtil smsUtil;

	@Value("${aliyun.sms.template_code}")
	private String template_code;

	@Value("${aliyun.sms.sign_name}")
	private String sign_name;

	@RabbitHandler
	public void executeSms(Map<String, Object> map) {
		//获取待发送的手机号
		String mobile = MapUtil.getStr(map, "mobile");
		//发送的验证码
		String checkCode = MapUtil.getStr(map, "checkCode");
		try {
			smsUtil.sendSms(mobile, template_code, sign_name, "{\"checkcode\":\"" + checkCode + "\"}");
		} catch (ClientException e) {
			e.printStackTrace();
		}
		//	System.out.println("手机号："+ MapUtil.getStr(map,"mobile"));
		//	System.out.println("验证码："+MapUtil.getStr(map,"checkCode"));
	}

}
