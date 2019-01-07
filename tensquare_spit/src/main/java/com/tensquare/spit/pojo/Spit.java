package com.tensquare.spit.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * @Author zhangchuan
 * @Date 2019/1/6 - 12:17
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Spit implements Serializable {
	@Id
	private String _id;
	private String content;
	private Date publishtime;
	private String userid;
	private String nickname;
	private Integer visits;
	private Integer thumbup;
	private Integer share;
	private Integer comment;
	private String state;
	private String parentid;
}
