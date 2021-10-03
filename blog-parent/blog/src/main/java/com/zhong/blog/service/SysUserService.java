package com.zhong.blog.service;

import com.zhong.blog.dao.pojo.SysUser;
import com.zhong.blog.vo.Result;
import com.zhong.blog.vo.UserVo;

public interface SysUserService {

	SysUser findUserById(Long id);
	
	SysUser findUser(String account, String password);

	Result findUserByToken(String token);
	
	public SysUser findUserByAccount(String account);
	
	public void save(SysUser sysUser);

	UserVo findUserVoById(Long authorId);
}
