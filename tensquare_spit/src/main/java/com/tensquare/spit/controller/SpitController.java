package com.tensquare.spit.controller;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * @Description:
 * @Author zhangchuan
 * @Date 2019/1/6 - 12:58
 * @Version 1.0
 */
@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {

	@Autowired
	private SpitService spitService;

	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 查询所有的记录
	 *
	 * @param
	 * @return
	 * @author zhangchuan
	 * @Date 2019/1/6 - 13:06
	 * @Description:
	 */
	@GetMapping()
	public Result findAll() {
		return new Result(true, StatusCode.OK, "查询成功", spitService.findAll());
	}

	@GetMapping("/{spitId}")
	public Result findById(@PathVariable String spitId) {
		return new Result(true, StatusCode.OK, "查询成功", spitService.findById(spitId));

	}

	/**
	 * 发布吐槽
	 *
	 * @param
	 * @return
	 * @author zhangchuan
	 * @Date 2019/1/6 - 19:03
	 * @Description:
	 */
	@PostMapping()
	public Result save(@RequestBody Spit spit) {
		spitService.save(spit);
		return new Result(true, StatusCode.OK, "保存成功");
	}

	@PutMapping("/{spitId}")
	public Result update(@RequestBody Spit spit, @PathVariable String spitId) {
		spit.set_id(spitId);
		spitService.update(spit);
		return new Result(true, StatusCode.OK, "更新成功");
	}

	@DeleteMapping("/{spitId}")
	public Result delete(@RequestBody Spit spit, @PathVariable String spitId) {
		spitService.deleteById(spitId);
		return new Result(true, StatusCode.OK, "删除成功");
	}

	@GetMapping("/comment/{parentid}/{page}/{size}")
	public Result findByParentid(@PathVariable String parentid, @PathVariable int page, @PathVariable int size) {
		Page<Spit> pageData = spitService.findByParentid(parentid, page, size);
		return new Result(true, StatusCode.OK, "查询成功", new PageResult<Spit>(pageData.getTotalElements(), pageData.getContent()));
	}

	/**
	 * 点赞
	 *
	 * @param
	 * @return
	 * @author zhangchuan
	 * @Date 2019/1/6 - 14:54
	 * @Description:
	 */
	@PutMapping("/thumbup/{spitId}")
	public Result thumbup(@PathVariable String spitId) {
		//判断当前用户是否点赞，但是我们现在没有做认证，暂时先把user写死
		String userid = "10403486";
		if (redisTemplate.opsForValue().get("thumbup_" + userid) != null) {
			return new Result(false, StatusCode.REPERROR, "不能重复点赞");
		}
		spitService.thumbup(spitId);
		//如果点赞成功，则放入一个标识
		redisTemplate.opsForValue().set("thumbup_" + userid, 1);
		return new Result(true, StatusCode.OK, "点赞成功");
	}

}

