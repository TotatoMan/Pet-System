package com.bcb.filter;

import com.alibaba.fastjson.JSONObject;
import com.bcb.pojo.Result;
import com.bcb.utils.JwtUtils;
import com.github.pagehelper.util.StringUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;
@Slf4j
@WebFilter(urlPatterns = "/*")//拦截所有请求
public class DemoFilter implements Filter {

    @Override//拦截请求之后调用，调用多次
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest) request;
        HttpServletResponse resp=(HttpServletResponse) response;
        //1.获取url请求
        String url=req.getRequestURI().toString();
        log.info("请求的url:{}",url);

        //2.判断url中是否包含login或register,如果包含则放行
        if(url.contains("login")||url.contains("register")||url.contains("identify"))
        {
            log.info("登录或注册操作，放行");
            chain.doFilter(request,response);
            return;
        }
        //3.获取请求头中的令牌token
        String jwt=req.getHeader("token");

        //4.判断令牌是否存在，如果不存在，返回错误结果
        if(!StringUtils.hasLength(jwt))
        {
            log.info("请求头token为空，返回未登录的信息");
            Result error=Result.error("NOT_LOGIN");
            //手动将对象转为json fastJSON
            String notLogin= JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);
            return;
        }
        //5.解析token,失败则返回错误结果（未登录）
        try{
            JwtUtils.parseJWT(jwt);
        }catch (Exception e)
        {
            //解析失败
            e.printStackTrace();
            log.info("解析失败，返回未登录的错误信息");
            Result error=Result.error("NOT_LOGIN");
            //手动将对象转为json fastJSON
            String notLogin= JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);
            return;
        }
        //6.放行
        log.info("令牌合法，放行");
        chain.doFilter(request,response);
    }
}
