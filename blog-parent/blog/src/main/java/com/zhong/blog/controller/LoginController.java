package com.zhong.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhong.blog.service.LoginService;
import com.zhong.blog.vo.Result;
import com.zhong.blog.vo.param.LoginParam;

@RestController
@RequestMapping("login")
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@PostMapping
	public Result login(@RequestBody LoginParam loginParam) {
		
		return loginService.login(loginParam);
		
	}
}
