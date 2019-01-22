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
@Table(name = "tb_friend")
//表示联合主键
@IdClass(Friend.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Friend implements Serializable {

	@Id
	private String userid;

	@Id
	private String friendid;

	private String islike;

}
