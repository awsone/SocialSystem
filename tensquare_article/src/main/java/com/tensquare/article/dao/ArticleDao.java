package com.tensquare.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.article.pojo.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface ArticleDao extends JpaRepository<Article, String>, JpaSpecificationExecutor<Article> {

	/**
	 * 文章状态修改
	 *
	 * @param
	 * @return
	 * @author zhangchuan
	 * @Date 2019/1/4 - 17:59
	 * @Description:
	 */
	@Modifying
	@Query(value = "update tb_article set state=1 where id=?1", nativeQuery = true)
	public void updateStatus(String id);

	/**
	 * 新增点赞
	 * @author zhangchuan
	 * @Date 2019/1/4 - 18:15
	 * @Description:
	 * @param id 需要点赞的文章id
	 * @return
	 */
	@Modifying
	@Query(value="update tb_article set thumbup=thumbup+1 where id=?1",nativeQuery = true)
	public void addThumbup(String id);
}
