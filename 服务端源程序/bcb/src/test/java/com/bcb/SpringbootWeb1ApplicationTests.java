package com.bcb;

import com.bcb.mapper.UserMapper;
import com.bcb.pojo.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//@SpringBootTest
class SpringbootWeb1ApplicationTests {
	@Autowired
	private UserMapper userMapper;
	@Test
	void contextLoads() {
	}
	//生成JWT令牌
	/*@Test
	public void testGenJwt()
	{
		Map<String,Object> claims =new HashMap<>();
		claims.put("id",1);
		claims.put("name","sxq");
		String jwt=Jwts.builder()
				.signWith(SignatureAlgorithm.HS256,"bcbsxq")//设置签名算法
				.setClaims(claims)//自定义内容（载荷）
				.setExpiration(new Date(System.currentTimeMillis()+3600*1000))//设置有效期为一小时
				.compact();//得到字符串
		System.out.println(jwt);

	}
	//解析jwt
	@Test
	public void testParseJwt()
	{
		Claims claims=Jwts.parser()
				.setSigningKey("bcbsxq")
				.parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoic3hxIiwiaWQiOjEsImV4cCI6MTY4MzYzNzAwM30.LXjzxYBIX1VUFyOyPbgbvl2PZrbHkAjlhA1eWfh_VhU")
				.getBody();
		System.out.println(claims);
	}*/
}
