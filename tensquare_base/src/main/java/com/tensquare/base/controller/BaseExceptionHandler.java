package com.tensquare.base.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author zhangchuan
 * @Description:
 * @Date 2018/12/23 - 16:57
 * @Modify By:
 */
@RestControllerAdvice
public class BaseExceptionHandler {

	@ExceptionHandler
	public Result exception(Exception e) {
		e.printStackTrace();
		return new Result(false, StatusCode.ERROR, e.getMessage());
	}

}
