package com.tensquare.base.ExceptionHandler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description:配置全部异常捕捉
 * @Author zhangchuan
 * @Date 2019/1/21 - 18:27
 * @Version 1.0
 */

@ControllerAdvice
public class MyExceptionHandler {

	public static final String ERROR_VIEW = "my_error";

	@ExceptionHandler(value = Exception.class)//指定拦截的异常
	public Object errorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
		e.printStackTrace();//打印异常信息
		ModelAndView mv = new ModelAndView();
		mv.addObject("exception", e);
		mv.addObject("url", request.getRequestURL());//发生异常的路径
		mv.setViewName(ERROR_VIEW);//指定发生异常之后跳转页面
		return mv;
	}
}
