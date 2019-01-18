package com.tensquare.qa.client;

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Description:
 * @Author zhangchuan
 * @Date 2019/1/17 - 21:03
 * @Version 1.0
 */
@FeignClient("tensquare-base")
public interface BaseClient {

	@GetMapping(value = "/label/{labelId}")
	public Result findById(@PathVariable("labelId") String labelId);
}
