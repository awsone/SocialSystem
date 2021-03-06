package com.tensquare.user.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import util.JwtUtil;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private JwtUtil jwtUtil;


	/**
	 * @return
	 * @parame
	 * @Description: 更新好友粉丝数和用户关注数
	 * @author zhangchuan
	 * @Date 2019/1/21 - 17:02
	 */
	@PutMapping("/{userid}/{friendid}/{x}")
	public void updatFanscountAndFollowcount(@PathVariable String userid, @PathVariable String friendid, @PathVariable int x) {
		userService.updateFanscountAndFollowcount(x, userid, friendid);

	}

	/**
	 * 用户登陆
	 *
	 * @param
	 * @return
	 * @author zhangchuan
	 * @Date 2019/1/16 - 16:04
	 * @Description:
	 */
	@PostMapping("/login")
	public Result login(@RequestBody User user) {
		User loginUser = userService.login(user);
		System.out.println(loginUser);
		if (loginUser == null) {
			return new Result(false, StatusCode.LOGINERROR, "登陆失败，请重新登陆或者进行注册");
		}
		System.out.println("当前用户登陆id：" + loginUser.getId());
		System.out.println("当前用户登陆电话：" + loginUser.getMobile());

		String token = jwtUtil.createJWT(loginUser.getId(), loginUser.getMobile(), "user");
		HashMap<Object, Object> map = MapUtil.newHashMap(2);
		map.put("token", token);
		map.put("role", "user");
		return new Result(true, StatusCode.OK, "登陆成功", map);
	}

	/**
	 * 查询全部数据
	 *
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Result findAll() {
		return new Result(true, StatusCode.OK, "查询成功", userService.findAll());
	}

	/**
	 * 根据ID查询
	 *
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Result findById(@PathVariable String id) {
		return new Result(true, StatusCode.OK, "查询成功", userService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 *
	 * @param searchMap 查询条件封装
	 * @param page      页码
	 * @param size      页大小
	 * @return 分页结果
	 */
	@RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
		Page<User> pageList = userService.findSearch(searchMap, page, size);
		return new Result(true, StatusCode.OK, "查询成功", new PageResult<User>(pageList.getTotalElements(), pageList.getContent()));
	}

	/**
	 * 根据条件查询
	 *
	 * @param searchMap
	 * @return
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap) {
		return new Result(true, StatusCode.OK, "查询成功", userService.findSearch(searchMap));
	}

	/**
	 * 增加
	 *
	 * @param user
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Result add(@RequestBody User user) {
		userService.add(user);
		return new Result(true, StatusCode.OK, "增加成功");
	}

	/**
	 * 修改
	 *
	 * @param user
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Result update(@RequestBody User user, @PathVariable String id) {
		user.setId(id);
		userService.update(user);
		return new Result(true, StatusCode.OK, "修改成功");
	}

	/**
	 * 删除  必须有admin角色才能删除
	 *
	 * @param id
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Result delete(@PathVariable String id) {
		userService.deleteById(id);
		return new Result(true, StatusCode.OK, "删除成功");
	}


	/**
	 * 发送手机验证码
	 *
	 * @param
	 * @return
	 * @author zhangchuan
	 * @Date 2019/1/15 - 10:32
	 * @Description:
	 */
	@PostMapping("/sendsms/{mobile}")
	public Result sendVerificationCode(@PathVariable String mobile) {
		userService.sendVerificationCode(mobile);
		return new Result(true, StatusCode.OK, "验证码发送成功");
	}

	/**
	 * 用户注册
	 *
	 * @param
	 * @return
	 * @author zhangchuan
	 * @Date 2019/1/15 - 11:49
	 * @Description:
	 */
	@PostMapping("/register/{code}")
	public Result register(@PathVariable String code, @RequestBody User user) throws Exception {
		//得到缓存中的验证码
		String checkcodeRedis = (String) redisTemplate.opsForValue().get("checkCode_" + user.getMobile());
		if (StringUtil.isNullOrEmpty(code)) {
			return new Result(false, StatusCode.ERROR, "请先获取手机验证码");
		}
		if (!ObjectUtil.equal(code, checkcodeRedis)) {
			return new Result(false, StatusCode.ERROR, "请输入正确的验证码");
		}
		//添加用户
		userService.add(user);
		return new Result(true, StatusCode.OK, "注册成功");
	}

}
