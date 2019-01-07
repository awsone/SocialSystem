package com.tensquare.spit.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import util.IdWorker;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author zhangchuan
 * @Date 2019/1/6 - 12:37
 * @Version 1.0
 */
@Service
@Transactional
public class SpitService {

	@Autowired
	private SpitDao spitDao;

	@Autowired
	private IdWorker idWorker;

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	RedisTemplate redisTemplate;

	/**
	 * 查询所有的记录
	 *
	 * @param
	 * @return
	 * @author zhangchuan
	 * @Date 2019/1/6 - 12:39
	 * @Description:
	 */
	public List<Spit> findAll() {
		return spitDao.findAll();
	}

	public Spit findById(String id) {
		return spitDao.findById(id).get();
	}

	public void save(Spit spit) {
		spit.set_id(idWorker.nextId()+"");
		spit.setPublishtime(new Date());
		//浏览量
		spit.setVisits(0);
		//分享数
		spit.setShare(0);
		//点赞数
		spit.setThumbup(0);
		//回复数
		spit.setComment(0);
		//状态
		spit.setState("1");
		//如果当前添加的吐槽，有父节点，那么将父节点的吐槽回复数要加1
		if(spit.getParentid()!=null&&!"".equals(spit.getParentid())){
			Query query=new Query();
			Update update=new Update();
			query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
			update.inc("comment",1);
			mongoTemplate.updateFirst(query,update,"spit");
		}
		spitDao.save(spit);
	}

	public void update(Spit spit) {
		spitDao.save(spit);
	}

	public void deleteById(String id){
		spitDao.deleteById(id);
	}

	public Page<Spit> findByParentid(String parentid,int page ,int size){
		Pageable pageable= PageRequest.of(page-1,size);
		return spitDao.findByParentid(parentid,pageable);
	}

	public void thumbup(String spitId) {
		//方式一：存在效率问题
		/*Spit spit = spitDao.findById(spitId).get();
		spit.setThumbup(spit.getThumbup()==null?1:spit.getThumbup()+1);
		spitDao.save(spit);*/

		//方式二：使用mongo原生的命令  db.spit.update({"_id","1"},{$inc:{thumbup:NumberInt(1)}})
		Query query=new Query();
		query.addCriteria(Criteria.where("_id").is(spitId));
		Update update=new Update();
		update.inc("thumbup",1);
		mongoTemplate.updateFirst(query, update,"spit");
	}
}
