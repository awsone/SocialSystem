package com.tensquare.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @Description:
 * @Author zhangchuan
 * @Date 2019/1/21 - 17:59
 * @Version 1.0
 */
@FeignClient("tensquare-user")
@Component
public interface UserClient {


	@PutMapping("/user/{userid}/{friendid}/{x}")
	public void updatFanscountAndFollowcount(@PathVariable("userid") String userid, @PathVariable("friendid") String friendid, @PathVariable("x") int x);

}
