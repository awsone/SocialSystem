package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @Description:
 * @Author zhangchuan
 * @Date 2019/1/18 - 16:23
 * @Version 1.0
 */
public interface NoFriendDao extends JpaRepository<NoFriend, String> {

	public NoFriend findByUseridAndFriendid(String userid, String friendid);


}
