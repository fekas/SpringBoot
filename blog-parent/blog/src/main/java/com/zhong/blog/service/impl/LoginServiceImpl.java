package com.zhong.blog.service.impl;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.zhong.blog.dao.pojo.SysUser;
import com.zhong.blog.service.LoginService;
import com.zhong.blog.util.JWTUtils;
import com.zhong.blog.vo.ErrorCode;
import com.zhong.blog.vo.Result;
import com.zhong.blog.vo.param.LoginParam;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private SysUserServiceImpl sysUserService;
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	private static final String slat = "zhong!@#";
	
	@Override
	public Result login(LoginParam loginParam) {
		
		String account = loginParam.getAccount();
		String password = loginParam.getPassword();
		
		if(StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
			return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
		}
		
		password = DigestUtils.md5Hex(password + slat);
		
		SysUser sysUser = sysUserService.findUser(account, password);
		
		if(sysUser == null) {
			return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
		}
		String token = JWTUtils.createToken(sysUser.getId());
		
		//过期时间参数可以动态传入
		redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
		
		return Result.success(token);
	}

	@Override
	public SysUser checkToken(String token) {
		
		if(StringUtils.isBlank(token)) {
			return null;
		}
		Map<String, Object> map = JWTUtils.checkToken(token);
		
		if(null == map) {
			return null;
		}
		
		String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
		
		if(StringUtils.isBlank(userJson)) {
			return null;
		}
		
		SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
		
		return sysUser;
	}

	@Override
	public Result logout(String token) {
		
		redisTemplate.delete("TOKEN_" + token);
		
		return Result.success(null);
	}

	@Override
	public Result register(LoginParam loginParam) {
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        String nickname = loginParam.getNickname();
        if (StringUtils.isBlank(account)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(nickname)
        ){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        SysUser sysUser =  sysUserService.findUserByAccount(account);
        if (sysUser != null){
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(),ErrorCode.ACCOUNT_EXIST.getMsg());
        }
        sysUser = new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password + slat));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("/static/img/logo.b3a48c0.png");
        sysUser.setAdmin(1); //1 为true
        sysUser.setDeleted(0); // 0 为false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        this.sysUserService.save(sysUser);

        String token = JWTUtils.createToken(sysUser.getId());

        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);
    }

}
