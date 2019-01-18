package com.tensquare.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 安全配置类
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//authorizeRequest所有的security全注解配置实现的开端，表示说明开始需要的权限
		//需要的权限分为两部分，第一部分是拦截的路径，第二部分是访问该路径所需要的权限
		//antMatchers 表示拦截的路径 premitAll()表示所有的权限都可以访问，直接放行所有
		//anyRwquset()表示任何的请求，authenticated()表示认证后才能登陆
		//.and().csrf().disable(); 固定写法,表示csrf拦截师失效(认为除了自己以外的请求，其他的请求都是在攻击自己)
		http
				.authorizeRequests()
				.antMatchers("/**").permitAll()
				.anyRequest().authenticated()
				.and().csrf().disable();
	}
}
