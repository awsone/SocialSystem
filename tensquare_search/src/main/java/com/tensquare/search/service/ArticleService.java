package com.tensquare.search.service;

import com.tensquare.search.article.ArticleDao;
import com.tensquare.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import util.IdWorker;

/**
 * @Description:
 * @Author zhangchuan
 * @Date 2019/1/10 - 11:26
 * @Version 1.0
 */
@Service
public class ArticleService {

	@Autowired
	private ArticleDao articleDao;

	@Autowired
	private IdWorker idWorker;

	/**
	 * 增加文章
	 * @author zhangchuan
	 * @Date 2019/1/10 - 11:32
	 * @Description: 
	 * @param 
	 * @return 
	 */
	public void save(Article article){
		article.setId(idWorker.nextId()+"");
		articleDao.save(article);
	}


	/**
	 * 根据关键字查询文章
	 * @author zhangchuan
	 * @Date 2019/1/10 - 14:11
	 * @Description: 
	 * @param 
	 * @return 
	 */
	public Page<Article> findByKey(String key, int page, int size) {
		Pageable pageable= PageRequest.of(page-1,size);
		return articleDao.findByTitleOrContent(key,key,pageable);
	}
}
