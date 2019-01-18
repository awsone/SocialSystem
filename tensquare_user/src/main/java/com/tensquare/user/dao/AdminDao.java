package com.tensquare.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.user.pojo.Admin;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface AdminDao extends JpaRepository<Admin, String>, JpaSpecificationExecutor<Admin> {

	/**
	 * 根绝用户名查询用户
	 *
	 * @param
	 * @return
	 * @author zhangchuan
	 * @Date 2019/1/16 - 14:05
	 * @Description:
	 */
	public Admin findByLoginname(String loginname);
}
