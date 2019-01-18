package com.tensquare.qa.config;

import com.tensquare.qa.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @Description:
 * @Author zhangchuan
 * @Date 2019/1/16 - 20:38
 * @Version 1.0tors
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {
	@Autowired
	private JwtInterceptor jwtInterceptor;

	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		//注册拦截器要声明拦截器对象和要申明的拦截器请求
		registry.addInterceptor(jwtInterceptor)
				.addPathPatterns("/**")
				.excludePathPatterns("/**/login/**");
	}

}
