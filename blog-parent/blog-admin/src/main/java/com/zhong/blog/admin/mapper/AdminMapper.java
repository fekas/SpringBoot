package com.zhong.blog.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhong.blog.admin.bean.Admin;
import com.zhong.blog.admin.bean.Permission;

public interface AdminMapper extends BaseMapper<Admin>{

	@Select("SELECT * FROM ms_permission where id in (select permission_id from ms_admin_permission where admin_id=#{adminId})")
	List<Permission> findPermissionByAdminId(Long adminId);

}
