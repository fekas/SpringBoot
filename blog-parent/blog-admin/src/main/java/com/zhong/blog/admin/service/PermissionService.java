package com.zhong.blog.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhong.blog.admin.bean.Permission;
import com.zhong.blog.admin.mapper.PermissionMapper;
import com.zhong.blog.admin.vo.PageResult;
import com.zhong.blog.admin.vo.Result;
import com.zhong.blog.admin.vo.params.PageParam;

@Service
public class PermissionService {

	@Autowired
	private PermissionMapper permissionMapper;
	
	public Result listPermission(PageParam pageParam) {

		Page<Permission> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());
		LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();
		if (StringUtils.isNotBlank(pageParam.getQueryString())) {
			queryWrapper.eq(Permission::getName, pageParam.getQueryString());
		}
		Page<Permission> permissionPage = permissionMapper.selectPage(page, queryWrapper);
		PageResult<Permission> pageResult = new PageResult<>();
		pageResult.setList(permissionPage.getRecords());
		pageResult.setTotal(permissionPage.getTotal());
		return Result.success(pageResult);
	}
	
	public Result add(Permission permission) {
        this.permissionMapper.insert(permission);
        return Result.success(null);
    }

    public Result update(Permission permission) {
        this.permissionMapper.updateById(permission);
        return Result.success(null);
    }

    public Result delete(Long id) {
        this.permissionMapper.deleteById(id);
        return Result.success(null);
    }

}
