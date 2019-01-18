package com.tensquare.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Timer;

/**
 * @Description: 生成token
 * @Author zhangchuan
 * @Date 2019/1/16 - 16:08
 * @Version 1.0
 */
public class CreateJwt {
	public static void main(String[] args) {
		JwtBuilder jwtBuilder = Jwts.builder()
				.setId("666")
				.setSubject("小马")
				.setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, "123456")
				.setExpiration(new Date(new Date().getTime() + 60 * 60 * 1000))   //设置header
				.claim("role", "admin");                        //自定义用户角色claim
		System.out.println(jwtBuilder.compact());
	}
}
