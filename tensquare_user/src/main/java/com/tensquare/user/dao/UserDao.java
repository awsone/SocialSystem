package com.tensquare.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.user.pojo.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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


	/**
	 * @param
	 * @return
	 * @Description: 更新好友粉丝数
	 * @author zhangchuan
	 * @Date 2019/1/21 - 17:18
	 */
	@Modifying
	@Query(value = "update tb_user set fanscount=fanscount+? where id=?", nativeQuery = true)
	void updateFanscount(int x, String friendid);

	/**
	 * @param
	 * @return
	 * @Description: 更新用户的关注数
	 * @author zhangchuan
	 * @Date 2019/1/21 - 17:18
	 */
	@Modifying
	@Query(value = "update tb_user set followcount=followcount+? where id=?", nativeQuery = true)
	public void updataFollowcount(int x, String userid);
}
