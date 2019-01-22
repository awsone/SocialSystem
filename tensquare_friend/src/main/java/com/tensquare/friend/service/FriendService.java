package com.tensquare.friend.service;

import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.hibernate.annotations.NaturalId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @Description:
 * @Author zhangchuan
 * @Date 2019/1/18 - 15:51
 * @Version 1.0
 */
@Service
@Transactional
public class FriendService {

	@Autowired
	private FriendDao friendDao;

	@Autowired
	private NoFriendDao noFriendDao;

	public int addFriend(String userid, String friendid) {
		//判断userid到friendid是否有数据，有的话就是重复添加
		Friend friend = friendDao.findByUseridAndFriendid(userid, friendid);
		if (friend != null) {
			return 0;
		}
		//直接添加好友，让好友中userid到friendId方向的type为0
		friend = new Friend();
		friend.setUserid(userid);
		friend.setFriendid(friendid);
		friend.setIslike("0");
		friendDao.save(friend);
		//判断friendid到userid是否有数据,有的话把双方的状态改为1
		if (friendDao.findByUseridAndFriendid(friendid, userid) != null) {
			//把双反的islike都改成
			friendDao.updateIsLike("1", userid, friendid);
			friendDao.updateIsLike("1", friendid, userid);
		}
		return 1;
	}


	/**
	 * @param
	 * @return
	 * @Description: 添加非好友
	 * @author zhangchuan
	 * @Date 2019/1/21 - 16:23
	 */
	public int findNoFriend(String userId, String friendid) {
		//判断是否非好友
		NoFriend noFriend = noFriendDao.findByUseridAndFriendid(userId, friendid);
		if (noFriend != null) {
			return 0;
		}
		noFriend = new NoFriend();
		noFriend.setUserid(userId);
		noFriend.setFriendid(friendid);
		noFriendDao.save(noFriend);
		return 1;
	}

	/**
	 * @param
	 * @return
	 * @Description: 删除好友
	 * @author zhangchuan
	 * @Date 2019/1/21 - 20:54
	 */
	public void deleteFriend(String userId, String friendid) {
		//删除好友表中userid->friendid的一条数据
		friendDao.deleteFriend(userId, friendid);
		//更新friend到userid的islike为0
		friendDao.updateIsLike("0", friendid, userId);
		//非好友表中添加数据
		NoFriend noFriend = new NoFriend();
		noFriend.setUserid(userId);
		noFriend.setFriendid(friendid);
		noFriendDao.save(noFriend);
	}
}
