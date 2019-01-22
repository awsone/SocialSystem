package enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author zhangchuan
 * @Date 2019/1/18 - 15:07
 * @Version 1.0
 */

@AllArgsConstructor
@NoArgsConstructor
public enum OneOrZeroEnum {

	ZERO(0, "零"),
	ONE(1, "添加好友"),
	TWO(2, "添加非好友");

	private Integer value;
	private String title;

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}}
