package com.tensquare.spit.dao;

import com.tensquare.spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Description:
 * @Author zhangchuan
 * @Date 2019/1/6 - 12:35
 * @Version 1.0
 */
public interface SpitDao extends MongoRepository<Spit,String> {

	public Page<Spit> findByParentid(String parentid, Pageable pageable);

}
