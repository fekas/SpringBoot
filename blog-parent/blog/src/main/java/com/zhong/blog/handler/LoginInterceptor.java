package com.zhong.blog.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.alibaba.fastjson.JSON;
import com.qiniu.util.Json;
import com.zhong.blog.dao.pojo.SysUser;
import com.zhong.blog.service.LoginService;
import com.zhong.blog.util.UserThreadLocal;
import com.zhong.blog.vo.ErrorCode;
import com.zhong.blog.vo.Result;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor{

	@Autowired
	private LoginService loginService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//判断是否是Handler方法(Controller方法)
		if(!(handler instanceof HandlerMethod)) {
			return true;	//RequestResourceHandler (访问静态资源的请求)		------------放行
		}
		
		String token = request.getHeader("Authorization");
		
		log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");
		
		if(StringUtils.isBlank(token)) {
			Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print(JSON.toJSONString(result));
			return false;
		}
		
		SysUser sysUser = loginService.checkToken(token);
		if(sysUser == null) {
			Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print(JSON.toJSONString(result));
			return false;
		}
		
		//保存登录的用户的信息
		UserThreadLocal.put(sysUser);
		
		return true;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		UserThreadLocal.remove();
	}
}
