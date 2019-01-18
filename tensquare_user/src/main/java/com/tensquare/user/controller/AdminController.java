package com.tensquare.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.tensquare.user.pojo.Admin;
import com.tensquare.user.service.AdminService;

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
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private JwtUtil jwtUtil;

	/**
	 * logger
	 */
	private static final Logger log = LoggerFactory.getLogger(AdminController.class);


	/**
	 * 管理员登陆
	 *
	 * @param
	 * @return
	 * @author zhangchuan
	 * @Date 2019/1/16 - 13:30
	 * @Description:
	 */
	@PostMapping("/login")
	public Result login(@RequestBody Admin admin) {
		Admin loginAdmin = adminService.login(admin);
		if (!ObjectUtil.isNotNull(loginAdmin)) {
			return new Result(false, StatusCode.LOGINERROR, "登录失败，请重新登陆");
		}
		//使得前后端可以通话的操作,使用JWT来操作
		//生成令牌
		String token = jwtUtil.createJWT(loginAdmin.getId(), loginAdmin.getLoginname(), "admin");
		HashMap<Object, Object> map = MapUtil.newHashMap(4);
		map.put("token", token);
		map.put("role", "admin");
		map.put("name", admin.getLoginname());
		log.info(admin.getLoginname() + " 用户登陆");
		return new Result(true, StatusCode.OK, "登陆成功", map);

	}

	/**
	 * 查询全部数据
	 *
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Result findAll() {
		return new Result(true, StatusCode.OK, "查询成功", adminService.findAll());
	}

	/**
	 * 根据ID查询
	 *
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Result findById(@PathVariable String id) {
		return new Result(true, StatusCode.OK, "查询成功", adminService.findById(id));
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
		Page<Admin> pageList = adminService.findSearch(searchMap, page, size);
		return new Result(true, StatusCode.OK, "查询成功", new PageResult<Admin>(pageList.getTotalElements(), pageList.getContent()));
	}

	/**
	 * 根据条件查询
	 *
	 * @param searchMap
	 * @return
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap) {
		return new Result(true, StatusCode.OK, "查询成功", adminService.findSearch(searchMap));
	}

	/**
	 * 增加管理员
	 *
	 * @param admin
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Result add(@RequestBody Admin admin) {
		adminService.add(admin);
		return new Result(true, StatusCode.OK, "增加成功");
	}

	/**
	 * 修改
	 *
	 * @param admin
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Result update(@RequestBody Admin admin, @PathVariable String id) {
		admin.setId(id);
		adminService.update(admin);
		return new Result(true, StatusCode.OK, "修改成功");
	}

	/**
	 * 删除
	 *
	 * @param id
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Result delete(@PathVariable String id) {
		adminService.deleteById(id);
		return new Result(true, StatusCode.OK, "删除成功");
	}


}
