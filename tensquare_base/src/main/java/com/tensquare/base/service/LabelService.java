package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhangchuan
 * @Description:
 * @Date 2018/12/23 - 15:26
 * @Modify By:
 */
@Service
@Transactional
public class LabelService {

	@Autowired
	private LabelDao labelDao;

	@Autowired
	private IdWorker idWorker;

	/** logger */
	private static final Logger log = LoggerFactory.getLogger(LabelService.class);

	public List<Label> findAll() {
		log.info("调用findAll()方法");
		return labelDao.findAll();
	}

	public Label findById(String id) {
		return labelDao.findById(id).get();
	}

	public void save(Label label) {
		label.setId(idWorker.nextId() + "");
		labelDao.save(label);
	}

	public void update(Label label) {
		labelDao.save(label);
	}

	public void delete(String id) {
		labelDao.deleteById(id);
	}

	public List<Label> findSearch(Label label) {
		return labelDao.findAll(new Specification<Label>() {
			/**
			 *
			 * @param root
			 * @param criteriaQuery 封装的是查询的关键字，比如group by,order by
			 * @param criteriaBuilder 用来封装条件对象 如果返回为null，则表示不需要任何条件
			 * @return
			 */
			@Override
			public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				return null;
			}
		});
	}


	public Page<Label> pageQuery(Label label, int page, int size) {
		Pageable pageable = PageRequest.of(page - 1, size);
		return labelDao.findAll(new Specification<Label>() {
			@Override
			public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				//new一个list集合，用来存放所有的条件
				List<Predicate> list = new ArrayList<>();
				if (label.getLabelname() != null && !"".equals(label.getLabelname())) {
					Predicate predicate = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname());
					list.add(predicate);
				}
				if (label.getState() != null && !"".equals(label.getState())) {
					Predicate predicate = cb.like(root.get("state").as(String.class), "%" + label.getState());
					list.add(predicate);
				}
				//new一个数组作为最终返回值的条件
				Predicate[] parr = new Predicate[list.size()];
				//把list转化成数组
				parr = list.toArray(parr);
				return cb.and(parr);
			}
		}, pageable);
	}
}
