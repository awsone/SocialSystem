package com.tensquare.search.controller;

import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author zhangchuan
 * @Date 2019/1/10 - 11:32
 * @Version 1.0
 */
@RestControllerAdvice
@RequestMapping("/article")
@CrossOrigin
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	/**
	 * 添加文章
	 * @author zhangchuan
	 * @Date 2019/1/10 - 13:53
	 * @Description: 
	 * @param 
	 * @return 
	 */
	@RequestMapping(method= RequestMethod.POST)
	public Result save(@RequestBody Article article) {
		articleService.save(article);
		return new Result(true, StatusCode.OK,"添加成功");
	}
	
	/**
	 * 根据关键字查找文章
	 * @author zhangchuan
	 * @Date 2019/1/10 - 13:54
	 * @Description: 
	 * @param 
	 * @return 
	 */
	@GetMapping(value="/{key}/{page}/{size}")
	public Result findByKey(@PathVariable String key,@PathVariable int page,@PathVariable int size){
		Page<Article> pageData=articleService.findByKey(key,page,size);
		return new Result(true,StatusCode.OK,"查询成功",new PageResult<Article>(pageData.getTotalElements(),pageData.getContent()));
	}




}
