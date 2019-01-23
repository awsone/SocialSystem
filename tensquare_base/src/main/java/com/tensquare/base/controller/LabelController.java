package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author zhangchuan
 * @Description:
 * @Date 2018/12/23 - 13:47
 * @Modify By:
 */
@RestController
@CrossOrigin
@RequestMapping("/label")
public class LabelController {

	@Autowired
	private LabelService labelService;

	@Autowired
	private HttpServletRequest request;

	@GetMapping
	public Result findAll() {
		//获取头信息
		String authorization = request.getHeader("Authorization");
		System.out.println("authorization:" + authorization);
		return new Result(true, StatusCode.OK, "查询成功", labelService.findAll());
	}

	@GetMapping(value = "/{labelId}")
	public Result findById(@PathVariable String labelId) {
		return new Result(true, StatusCode.OK, "查询成功", labelService.findById(labelId));
	}

	@PostMapping
	public Result save(@RequestBody Label label) {
		labelService.save(label);
		return new Result(true, StatusCode.OK, "添加成功", "");
	}

	@PutMapping("/{labelId}")
	public Result update(@PathVariable String labelId, @RequestBody Label label) {
		label.setId(labelId);
		labelService.update(label);
		return new Result(true, StatusCode.OK, "更新成功", "");
	}

	@DeleteMapping("/{labelId}")
	public Result deleteById(@PathVariable String labelId) {
		labelService.delete(labelId);
		return new Result(true, StatusCode.OK, "删除成功", "");
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Result findSearch(@RequestBody Label label) {
		List<Label> list = labelService.findSearch(label);
		return new Result(true, StatusCode.OK, "查询成功", list);
	}
/*
    @RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
    public Result findSearch1(@RequestBody Label label,@PathVariable int page,@PathVariable int size){
        Page<Label> pageData =labelService.pageQuery(label,page,size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<Label>(pageData.getTotalElements(),pageData.getContent()));
    }*/

	@RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
	public Result PageQuery(@RequestBody Label label, @PathVariable int page, @PathVariable int size) {
		Page<Label> pageDatas = labelService.pageQuery(label, page, size);
		return new Result(true, StatusCode.OK, "查询成功", new PageResult<Label>(pageDatas.getTotalElements(), pageDatas.getContent()));
	}

}
