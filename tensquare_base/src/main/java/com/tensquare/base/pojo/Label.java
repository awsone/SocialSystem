package com.tensquare.base.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author zhangchuan
 * @Description:
 * @Date 2018/12/23 - 14:58
 * @Modify By:
 */
@Entity
@Table(name = "tb_label")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Label implements Serializable {
	@Id
	private String id;
	/**
	 * 标签名称
	 */
	private String labelname; //
	private String state;//状态
	private Long count;  //使用数量
	private Long fans;  //关注数量
	private String recommend;  //是否推荐
}
