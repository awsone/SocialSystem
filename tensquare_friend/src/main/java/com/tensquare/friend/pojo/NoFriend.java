package com.tensquare.friend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Description:
 * @Author zhangchuan
 * @Date 2019/1/18 - 16:24
 * @Version 1.0
 */
@Entity
@Table(name = "tb_nofriend")
//表示联合主键
@IdClass(NoFriend.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
//分布式，存在跨平台的问题，所以需要实现序列化接口
public class NoFriend implements Serializable {

	/**
	 * 用户id
	 */
	@Id
	private String userid;

	/**
	 *
	 */
	@Id
	private String friendid;

}
