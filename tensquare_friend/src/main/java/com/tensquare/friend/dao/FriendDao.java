package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @Description:
 * @Author zhangchuan
 * @Date 2019/1/18 - 16:23
 * @Version 1.0
 */
public interface FriendDao extends JpaRepository<Friend, String> {

	public Friend findByUseridAndFriendid(String userid, String friendid);

	/**
	 * @param
	 * @return
	 * @Description: 更新islike状态
	 * @author zhangchuan
	 * @Date 2019/1/18 - 17:11
	 */
	@Modifying
	@Query(value = "update tb_friend set islike=? Where userid=? and friendid=?", nativeQuery = true)
	public void updateIsLike(String isLike, String userid, String friendid);

	/**
	 * @param
	 * @return
	 * @Description: 删除好友
	 * @author zhangchuan
	 * @Date 2019/1/21 - 20:56
	 */
	@Modifying
	@Query(value = "delete from tb_friend Where userid=? and friendid=?", nativeQuery = true)
	public void deleteFriend(String userid, String friendid);
}
