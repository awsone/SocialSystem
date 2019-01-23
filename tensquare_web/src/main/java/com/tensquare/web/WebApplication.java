package com.tensquare.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @Description:
 * @Author zhangchuan
 * @Date 2019/1/22 - 16:21
 * @Version 1.0
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class WebApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class);
	}
}
