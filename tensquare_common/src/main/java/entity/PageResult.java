package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zc
 * @date 2018/12/23 - 10:59
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {
	/**
	 * 总数量
	 */
	private long total;

	/**
	 * 总列数
	 */
	private List<T> rows;
}
