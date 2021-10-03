package com.zhong.blog.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhong.blog.vo.Result;

//对加了Controller注解的方法进行处理 AOP
@ControllerAdvice
public class AllExceptionHandler {

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Result doException(Exception ex) {
		ex.printStackTrace();
		return Result.fail(-999, "系统异常");
	}
}
