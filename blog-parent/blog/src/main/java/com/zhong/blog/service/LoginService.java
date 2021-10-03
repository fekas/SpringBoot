package com.zhong.blog.service;

import org.springframework.transaction.annotation.Transactional;

import com.zhong.blog.dao.pojo.SysUser;
import com.zhong.blog.vo.Result;
import com.zhong.blog.vo.param.LoginParam;

@Transactional
public interface LoginService {

	Result login(LoginParam loginParam);

	SysUser checkToken(String token);

	Result logout(String token);

	Result register(LoginParam loginParam);

}
