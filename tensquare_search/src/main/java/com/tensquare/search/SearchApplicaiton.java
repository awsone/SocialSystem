package com.tensquare.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

/**
 * @Description:
 * @Author zhangchuan
 * @Date 2019/1/8 - 15:50
 * @Version 1.0
 */
@SpringBootApplication
public class SearchApplicaiton {

	public static void main(String[] args) {
		SpringApplication.run(SearchApplicaiton.class,args);
	}

	@Bean
	public IdWorker idWorker(){
		return new IdWorker(1,1);
	}
}
