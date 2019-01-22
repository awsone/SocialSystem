package com.tensquare.qa.client.Impl;

import com.tensquare.qa.client.BaseClient;
import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author zhangchuan
 * @Date 2019/1/22 - 11:15
 * @Version 1.0
 */
@Component
public class BaseClientImpl implements BaseClient {
	@Override
	public Result findById(String labelId) {
		return new Result(false, StatusCode.ERROR, "触发熔断器了");
	}
}
