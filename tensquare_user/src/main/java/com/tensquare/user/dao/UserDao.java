package com.tensquare.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.user.pojo.User;
import sun.security.provider.MD2;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface UserDao extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

	/**
	 * 根据电话号码查询用户
	 *
	 * @param
	 * @return
	 * @author zhangchuan
	 * @Date 2019/1/16 - 14:32
	 * @Description:
	 */
	public User findByMobile(String mobile);
}
