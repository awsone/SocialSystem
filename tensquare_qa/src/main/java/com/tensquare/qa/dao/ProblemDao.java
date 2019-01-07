package com.tensquare.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface ProblemDao extends JpaRepository<Problem, String>, JpaSpecificationExecutor<Problem> {

	/**
	 * 查询最新问题
	 *
	 * @param
	 * @return
	 * @author zhangchuan
	 * @Date: ${DATE} ${TIME}
	 * @Description:
	 */
	@Query(value = "SELECT * FROM `tb_problem`,tb_pl where id=problemid and labelid=? order by replytime desc", nativeQuery = true)
	public Page<Problem> newList(String labelId, Pageable pageable);

	/**
	 * 查询最新问题
	 *
	 * @param
	 * @return list
	 * @author zhangchuan
	 * @Date 2019/1/4 - 16:26
	 * @Description:
	 */
	@Query(value = "SELECT * FROM `tb_problem`,tb_pl where id=problemid and labelid=? order by reply desc", nativeQuery = true)
	public Page<Problem> hotList(String labelId, Pageable pageable);


	/**
	 * 查询
	 * @author zhangchuan
	 * @Date 2019/1/4 - 16:32
	 * @Description: 
	 * @param 
	 * @return 
	 */
	@Query(value = "SELECT * FROM `tb_problem`,tb_pl where id=problemid and labelid=? and reply=0 order by createtime desc", nativeQuery = true)
	public Page<Problem> waitList(String labelId, Pageable pageable);
}
