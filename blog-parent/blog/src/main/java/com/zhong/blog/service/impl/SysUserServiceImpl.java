package com.zhong.blog.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhong.blog.dao.mapper.SysUserMapper;
import com.zhong.blog.dao.pojo.SysUser;
import com.zhong.blog.service.LoginService;
import com.zhong.blog.service.SysUserService;
import com.zhong.blog.vo.ErrorCode;
import com.zhong.blog.vo.LoginUserVo;
import com.zhong.blog.vo.Result;
import com.zhong.blog.vo.UserVo;

@Service
public class SysUserServiceImpl implements SysUserService{

	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Autowired
	private LoginService loginService;
	
	@Override
	public SysUser findUserById(Long id) {
		SysUser sysUser = sysUserMapper.selectById(id);
		
		if(sysUser == null) {
			sysUser = new SysUser();
			sysUser.setNickname("霸霸");
		}
		
		return sysUser;
	}

	public SysUser findUser(String account, String password) {
		
		LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
		
		queryWrapper.eq(SysUser::getAccount, account);
		queryWrapper.eq(SysUser::getPassword, password);
		queryWrapper.select(SysUser::getAccount, SysUser::getId, SysUser::getAccount, SysUser::getNickname, SysUser::getAvatar);
		queryWrapper.last("limit 1");
		
		return sysUserMapper.selectOne(queryWrapper);
		
	}

	@Override
	public Result findUserByToken(String token) {
		
		SysUser sysUser = loginService.checkToken(token);
		
		if(null == sysUser) {
			return Result.fail(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMsg());
		}
		
		LoginUserVo loginUserVo = new LoginUserVo();
		
		BeanUtils.copyProperties(sysUser, loginUserVo);
		
		return Result.success(loginUserVo);
	}

	public SysUser findUserByAccount(String account) {
		
		LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SysUser::getAccount, account);
		queryWrapper.last("limit 1");
		return this.sysUserMapper.selectOne(queryWrapper);
	}

	public void save(SysUser sysUser) {
		
		this.sysUserMapper.insert(sysUser);
		
	}

	@Override
    public UserVo findUserVoById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser == null){
            sysUser = new SysUser();
            sysUser.setId(1L);
            sysUser.setAvatar("/static/img/logo.b3a48c0.png");
            sysUser.setNickname("码神之路");
        }
        UserVo userVo  = new UserVo();
        BeanUtils.copyProperties(sysUser,userVo);
        userVo.setId(String.valueOf(sysUser.getId()));
        return userVo;
    }

}
