package com.tensquare.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;

/**
 * @Description: 对token进行解析
 * @Author zhangchuan
 * @Date 2019/1/16 - 16:27
 * @Version 1.0
 */
public class ParseJwtTest {
	public static void main(String[] args) {
		Claims claims = Jwts.parser().setSigningKey("123456")
				.parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiLlsI_pqawiLCJpYXQiOjE1NDc2Mjg1ODAsImV4cCI6MTU0NzYzMjE4MH0.5bd-RsDfFv3F8Cok6kkNBrc3yEdoVfG28ZRl_D_KqEc")
				.getBody();
		System.out.println("用户id：" + claims.getId());
		System.out.println("用户名称：" + claims.getSubject());
		System.out.println("登陆时间：" + claims.getIssuedAt());
		System.out.println("过期时间：" + claims.getExpiration());

	}

}
