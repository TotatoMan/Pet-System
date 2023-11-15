package com.bcb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan//开启了对servlet组件的支持
@SpringBootApplication
public class SpringbootWeb1Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootWeb1Application.class, args);
	}

}
