package com.tensquare.friend.interceptor;

import feign.Client;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description:
 * @Author zhangchuan
 * @Date 2019/1/16 - 20:32
 * @Version 1.0
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println("经过了拦截器");
		//无论无何都需要放行，具体的操作还是放在拦截器中判断
		//拦截器只是负责把有请求总包含token的进行解析
		final String header = request.getHeader("Authorization");
		if (header != null && !"".equals(header)) {
			//如果由头信息，则对他进行一个操作
			if (header.startsWith("Bearer ")) {
				//得到令牌
				String token = header.substring(7);
				//对token进行解析,验证是否正确
				try {
					Claims claims = jwtUtil.parseJWT(token);
					System.out.println("claims" + claims);
					String role = (String) claims.get("roles");
					//设置管理员或者普通用户标签
					if (role != null || role.equals("admin")) {
						request.setAttribute("claims_admin", claims);
					}
					if (role != null || role.equals("user")) {
						request.setAttribute("claims_user", claims);
					}
				} catch (Exception e) {
					throw new RuntimeException("令牌错误！");
				}
			}
		}

		return true;
	}


}
