package com.zhong.blog.vo.param;

import lombok.Data;

@Data
public class PageParams {

	private int page = 1;
	
	private int pageSize = 10;
}
