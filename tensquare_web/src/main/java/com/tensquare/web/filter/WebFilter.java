package com.tensquare.web.filter;

import cn.hutool.core.util.ObjectUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.ObjectUtils;
import org.apache.http.protocol.RequestContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author zhangchuan
 * @Date 2019/1/22 - 16:30
 * @Version 1.0
 */
@Component
public class WebFilter extends ZuulFilter {

	/**
	 * logger
	 */
	private static final Logger log = LoggerFactory.getLogger(WebFilter.class);

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
		//得到上下文
		RequestContext currentContext = RequestContext.getCurrentContext();
		//得到request域
		HttpServletRequest request = currentContext.getRequest();
		//得到头信息
		String header = request.getHeader("authorization");
		//判断是否有头信息
		if (ObjectUtil.isNotNull(header) && !"".equals(header)) {
			//把头信息继续向下传
			//向header中添加鉴权令牌
			currentContext.addZuulRequestHeader("authorization", header);
		}
		log.info("经过后台过滤除器");
		return null;
	}
}
