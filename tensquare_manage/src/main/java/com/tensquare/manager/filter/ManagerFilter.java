package com.tensquare.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import org.apache.tomcat.util.http.parser.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author zhangchuan
 * @Date 2019/1/22 - 16:30
 * @Version 1.0
 */
@Component
public class ManagerFilter extends ZuulFilter {

	/**
	 * logger
	 */
	private static final Logger log = LoggerFactory.getLogger(ManagerFilter.class);


	@Autowired
	private JwtUtil jwtUtil;

	/**
	 * @param
	 * @return
	 * @Description: 过滤器的类型
	 * @author zhangchuan
	 * @Date 2019/1/22 - 16:31
	 */
	@Override
	public String filterType() {
		//在操作之前过滤
		return "pre";
		//在操作之后过滤
		//return "post";
	}

	/**
	 * @param
	 * @return
	 * @Description: 多个过滤的执行顺修，数字越小越先执行
	 * @author zhangchuan
	 * @Date 2019/1/22 - 16:34
	 */
	@Override
	public int filterOrder() {
		return 0;
	}

	/**
	 * @param
	 * @return
	 * @Description: 当前过滤器是否开启 true:开启  false:关闭
	 * @author zhangchuan
	 * @Date 2019/1/22 - 16:35
	 */
	@Override
	public boolean shouldFilter() {
		return true;
	}

	/**
	 * @param
	 * @return
	 * @Description: 过滤器内执行的操作，return任何类型的值都表示继续执行
	 * setsendzullResponse(false)表示不在继续执行
	 * @author zhangchuan
	 * @Date 2019/1/22 - 16:36
	 */
	@Override
	public Object run() throws ZuulException {
		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletRequest request = requestContext.getRequest();

		//zull网关转发会进行两次请求，第一是OPTIONS方法，第二个才是真实的请求
		//如果是第一个方法则放行
		if (request.getMethod().equals("OPTIONS")) {
			return null;
		}

		//放行登陆
		if (request.getRequestURL().indexOf("login") > 0) {
			log.info("登陆页面《-》用户登陆");
			return null;
		}

		//得到头信息
		String header = request.getHeader("Authorization");
		log.info("经过后台过滤器");
		if (header != null && !"".equals(header)) {
			if (header.startsWith("Bearer")) {
				//得到令牌
				String token = header.substring(7);
				//对token进行解析验证，验证是否正确
				try {
					Claims claims = jwtUtil.parseJWT(token);
					String role = (String) claims.get("roles");
					//设置管理员或者普通用户标签
					if (role != null || role.equals("admin")) {
						//把头信息转发
						requestContext.addZuulResponseHeader("Authorization", header);
						return null;
					}
				} catch (Exception e) {
					log.info("令牌解析出现错误:{}", e.getMessage());
					//终止运行
					requestContext.setSendZuulResponse(false);
				}
			}
		}
		requestContext.setSendZuulResponse(false);
		requestContext.setResponseStatusCode(403);
		requestContext.setResponseBody("权限不足");
		requestContext.getResponse().setContentType("text/html;charset=utf-8");
		return null;
	}
}
