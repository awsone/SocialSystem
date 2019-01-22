package com.tensquare.user.service;

import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import com.tensquare.user.dao.UserDao;
import com.tensquare.user.pojo.User;
import util.JwtUtil;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private IdWorker idWorker;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private BCryptPasswordEncoder bp;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private HttpServletRequest httpServletRequest;

	/**
	 * 查询全部列表
	 *
	 * @return
	 */
	public List<User> findAll() {
		return userDao.findAll();
	}


	/**
	 * 条件查询+分页
	 *
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<User> findSearch(Map whereMap, int page, int size) {
		Specification<User> specification = createSpecification(whereMap);
		PageRequest pageRequest = PageRequest.of(page - 1, size);
		return userDao.findAll(specification, pageRequest);
	}


	/**
	 * 条件查询
	 *
	 * @param whereMap
	 * @return
	 */
	public List<User> findSearch(Map whereMap) {
		Specification<User> specification = createSpecification(whereMap);
		return userDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 *
	 * @param id
	 * @return
	 */
	public User findById(String id) {
		return userDao.findById(id).get();
	}

	/**
	 * 增加
	 *
	 * @param user
	 */
	public void add(User user) {
		user.setId(idWorker.nextId() + "");
		//进行密码加密
		user.setPassword(bp.encode(user.getPassword()));
		//关注数
		user.setFollowcount(0);
		//粉丝数
		user.setFanscount(0);
		//在线时长
		user.setOnline(0L);
		//注册日期
		user.setRegdate(new Date());
		//更新日期
		user.setUpdatedate(new Date());
		//最后登陆时间
		user.setLastdate(new Date());
		userDao.save(user);
	}

	/**
	 * 修改
	 *
	 * @param user
	 */
	public void update(User user) {
		userDao.save(user);
	}

	/**
	 * 删除
	 * 必须要有admin角色才能进行删除
	 *
	 * @param id
	 */
	public void deleteById(String id) {
		String token = (String) httpServletRequest.getAttribute("claims_admin");
		if (token == null || "".equals(token)) {
			throw new RuntimeException("权限不足");
		}
		/*String header = httpServletRequest.getHeader("Authorization");
		if (header == null && "".equals(header)) {
			throw new RuntimeException("权限不足！");
		}
		if (!header.startsWith("Bearer ")) {
			throw new RuntimeException("权限不足！");
		}
		//得到令牌
		String token = header.substring(7);
		//对token进行解析,验证是否正确
		try {
			Claims claims = jwtUtil.parseJWT(token);
			String role = (String) claims.get("role");
			if (role == null || !role.equals("admin")) {
				throw new RuntimeException("权限不足");
			}
		} catch (Exception e) {
			throw new RuntimeException("权限不足！");
		}*/
		userDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 *
	 * @param searchMap
	 * @return
	 */
	private Specification<User> createSpecification(Map searchMap) {

		return new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				// ID
				if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
					predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
				}
				// 手机号码
				if (searchMap.get("mobile") != null && !"".equals(searchMap.get("mobile"))) {
					predicateList.add(cb.like(root.get("mobile").as(String.class), "%" + (String) searchMap.get("mobile") + "%"));
				}
				// 密码
				if (searchMap.get("password") != null && !"".equals(searchMap.get("password"))) {
					predicateList.add(cb.like(root.get("password").as(String.class), "%" + (String) searchMap.get("password") + "%"));
				}
				// 昵称
				if (searchMap.get("nickname") != null && !"".equals(searchMap.get("nickname"))) {
					predicateList.add(cb.like(root.get("nickname").as(String.class), "%" + (String) searchMap.get("nickname") + "%"));
				}
				// 性别
				if (searchMap.get("sex") != null && !"".equals(searchMap.get("sex"))) {
					predicateList.add(cb.like(root.get("sex").as(String.class), "%" + (String) searchMap.get("sex") + "%"));
				}
				// 头像
				if (searchMap.get("avatar") != null && !"".equals(searchMap.get("avatar"))) {
					predicateList.add(cb.like(root.get("avatar").as(String.class), "%" + (String) searchMap.get("avatar") + "%"));
				}
				// E-Mail
				if (searchMap.get("email") != null && !"".equals(searchMap.get("email"))) {
					predicateList.add(cb.like(root.get("email").as(String.class), "%" + (String) searchMap.get("email") + "%"));
				}
				// 兴趣
				if (searchMap.get("interest") != null && !"".equals(searchMap.get("interest"))) {
					predicateList.add(cb.like(root.get("interest").as(String.class), "%" + (String) searchMap.get("interest") + "%"));
				}
				// 个性
				if (searchMap.get("personality") != null && !"".equals(searchMap.get("personality"))) {
					predicateList.add(cb.like(root.get("personality").as(String.class), "%" + (String) searchMap.get("personality") + "%"));
				}

				return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

	/**
	 * 发送短信
	 *
	 * @param mobile
	 */
	public void sendVerificationCode(String mobile) {
		//生成6位随机数
		String checkCode = RandomStringUtils.randomNumeric(6);
		//想缓存中存放一份,设置过期时间为1个小时
		redisTemplate.opsForValue().set("checkCode_" + mobile, checkCode, 1, TimeUnit.HOURS);
		HashMap<String, Object> map = MapUtil.newHashMap(2);
		map.put("mobile", mobile);
		map.put("checkCode", checkCode);
		//给用户发送一份，放入消息队列中
		//	rabbitTemplate.convertAndSend("sms",map);
		//在控制台显示一份
		System.out.println("验证码为: " + checkCode + "，有效时间为1个小时");

	}

	public User login(User user) {
		//根据电话号码查询用户
		User userLogin = userDao.findByMobile(user.getMobile());
		System.out.println("userLogin" + userLogin);
		if (userLogin != null && bp.matches(user.getPassword(), userLogin.getPassword())) {
			//表示登陆成功
			return userLogin;
		}
		return null;
	}

	/**
	 * @param
	 * @return
	 * @Description: 更新粉丝数
	 * @author zhangchuan
	 * @Date 2019/1/21 - 17:08
	 */
	@Transactional
	public void updateFanscountAndFollowcount(int x, String userid, String friendid) {
		//更新好友的粉丝数
		userDao.updateFanscount(x, friendid);
		//更新自己的关注数
		userDao.updataFollowcount(x, userid);
	}

}
