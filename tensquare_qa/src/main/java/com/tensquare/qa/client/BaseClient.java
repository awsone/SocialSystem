package com.tensquare.qa.client;

import com.tensquare.qa.client.Impl.BaseClientImpl;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Description: 远程调用
 * @Author zhangchuan
 * @Date 2019/1/17 - 21:03
 * @Version 1.0
 */
@FeignClient(value = "tensquare-base", fallback = BaseClientImpl.class)
@Component
public interface BaseClient {

	@GetMapping(value = "/label/{labelId}")
	public Result findById(@PathVariable("labelId") String labelId);
}
