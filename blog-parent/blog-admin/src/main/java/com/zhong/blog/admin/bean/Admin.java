package com.zhong.blog.admin.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Admin {

	//不适用分布式ID
    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;
}
