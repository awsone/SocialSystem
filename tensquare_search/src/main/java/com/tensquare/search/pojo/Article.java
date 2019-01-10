package com.tensquare.search.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

/**
 * @Description:
 * @Author zhangchuan
 * @Date 2019/1/10 - 10:56
 * @Version 1.0
 */
@Document(indexName="socialsystem_article",type="article")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Article implements Serializable {

	@Id
	private String id;

	/**
	 * 标题
	 * index=true ——>这一列的值进行索引
	 * 是否索引：看该词是否能被搜索
	 * 是否分词：表示搜索的时候整体匹配还是单词匹匹
	 * 是否存储：就是是否在页面上显示
	 */
	@Field(index= true,analyzer="ik_max_word",searchAnalyzer="ik_max_word")
	private String title;

	/**
	 * 内容
	 */
	@Field(index= true,analyzer="ik_max_word",searchAnalyzer="ik_max_word")
	private String content;

	/**
	 * 审核状态
	 */
	private String state;


}
