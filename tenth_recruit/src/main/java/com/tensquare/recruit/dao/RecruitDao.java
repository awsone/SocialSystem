package com.tensquare.recruit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.recruit.pojo.Recruit;

import java.util.List;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface RecruitDao extends JpaRepository<Recruit, String>, JpaSpecificationExecutor<Recruit> {

	/**
	 * 查找推荐的职位
	 *
	 * @param state 开启的状态
	 * @return list 返回list
	 * @Tittle
	 * @author zhangchuan
	 * @Date: ${DATE} ${TIME}
	 * @Description:
	 */
	List<Recruit> findTop6ByStateOrderByCreatetime(String state);//	 * where state=? order by createtime

	/**
	 * 查找未被关闭的职位
	 *
	 * @param state 状态
	 * @return
	 */
	public List<Recruit> findTop6ByStateNotOrderByCreatetimeDesc(String state);
}
