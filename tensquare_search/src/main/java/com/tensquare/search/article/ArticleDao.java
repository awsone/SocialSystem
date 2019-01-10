package com.tensquare.search.article;

import com.tensquare.search.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Description:
 * @Author zhangchuan
 * @Date 2019/1/10 - 11:23
 * @Version 1.0
 */
public interface ArticleDao extends ElasticsearchRepository<Article,String> {

	public Page<Article> findByTitleOrContent(String title, String content, Pageable pageable);

}