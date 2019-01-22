package com.tensquare.friend.controller;

import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import enums.OneOrZeroEnum;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author zhangchuan
 * @Date 2019/1/18 - 14:35
 * @Version 1.0
 */
@RestController
@RequestMapping("/friend")
public class FriendController {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private FriendService friendService;

	@Autowired
	private UserClient userClient;


	/**
	 * @param
	 * @return
	 * @Description: 添加好友或添加非好友
	 * @author zhangchuan
	 * @Date 2019/1/18 - 14:54
	 */
	@PutMapping("/like/{friendid}/{type}")
	public Result addFriend(@PathVariable String friendid, @PathVariable String type) {
		//验证是否登陆，并拿到当前登陆用户的Id
		Claims claims = (Claims) request.getAttribute("claims_user");
		if (claims == null || claims.isEmpty()) {
			return new Result(false, StatusCode.LOGINERROR, "权限不足");
		}
		//得到当前登陆的用户id
		String userId = claims.getId();
		//判断是添加好友，还是非好友
		if (type != null) {
			if ("1".equals(type)) {
				//添加好友
				int flag = friendService.addFriend(userId, friendid);
				if (OneOrZeroEnum.ZERO.getValue().equals(flag)) {
					return new Result(false, StatusCode.ERROR, "不能重复添加好友");
				}
				if (OneOrZeroEnum.ONE.getValue().equals(flag)) {
					//添加用户的关注数和粉丝数
					userClient.updatFanscountAndFollowcount(userId, friendid, 1);
					return new Result(true, StatusCode.OK, "添加成功");
				}
			} else if (OneOrZeroEnum.TWO.getValue().equals(type)) {
				//添加非好友
				int flag = friendService.findNoFriend(userId, friendid);
				if (OneOrZeroEnum.ZERO.getValue().equals(flag)) {
					return new Result(false, StatusCode.ERROR, "不能重复添加非好友");
				}
				if (OneOrZeroEnum.ONE.getValue().equals(flag)) {
					return new Result(true, StatusCode.OK, "添加成功");
				}
			}
			return new Result(false, StatusCode.ERROR, "参数错误");

		} else {
			return new Result(false, StatusCode.ERROR, "参数错误");
		}
	}

	@DeleteMapping("/{friendid}")
	public Result deleteFriend(@PathVariable String friendid) {
		Assert.notNull(friendid, "请输入要删除的好友编号");
		//验证是否登陆，并拿到当前登陆用户的Id
		Claims claims = (Claims) request.getAttribute("claims_user");
		if (claims == null || claims.isEmpty()) {
			return new Result(false, StatusCode.LOGINERROR, "权限不足");
		}
		//得到当前登陆的用户id
		String userId = claims.getId();
		friendService.deleteFriend(userId, friendid);
		userClient.updatFanscountAndFollowcount(userId, friendid, -1);
		return new Result(true, StatusCode.OK, "删除成功");
	}
}
