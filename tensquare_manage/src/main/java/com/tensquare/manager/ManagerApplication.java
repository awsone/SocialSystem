package com.tensquare.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import util.JwtUtil;

/**
 * @Description:
 * @Author zhangchuan
 * @Date 2019/1/22 - 15:49
 * @Version 1.0 000000000000
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy   //使用zull网关
public class ManagerApplication {
	public static void main(String[] args) {
		SpringApplication.run(ManagerApplication.class);
	}

	@Bean
	public JwtUtil jwtUtil() {
		return new JwtUtil();
	}


}
