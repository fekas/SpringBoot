package com.zhong.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhong.blog.service.SysUserService;
import com.zhong.blog.vo.Result;

@RestController
@RequestMapping("users")
public class UsersController {
	
	@Autowired
	private SysUserService sysUserService;
	
	@GetMapping("currentUser")
	public Result currentUser(@RequestHeader("Authorization") String token) {
		return sysUserService.findUserByToken(token);
		
	}

}
