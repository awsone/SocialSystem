package entity;

import lombok.*;

/**
 * @author zc
 * @date 2018/12/23 - 10:51
 */
@Data
@EqualsAndHashCode
@Generated
@AllArgsConstructor
@NoArgsConstructor
public class Result {
	private boolean flag;
	private Integer code;
	private String message;
	private Object data;

	public Result(boolean flag, Integer code, String message) {
		this.flag = flag;
		this.code = code;
		this.message = message;
	}
}
