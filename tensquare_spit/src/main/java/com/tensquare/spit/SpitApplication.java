package com.tensquare.spit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

/**
 * @Description:
 * @Author zhangchuan
 * @Date 2019/1/6 - 12:12
 * @Version 1.0
 */
@SpringBootApplication
public class SpitApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpitApplication.class,args);
	}

	@Bean
	public IdWorker idWorker(){
		return new IdWorker();
	}
}
