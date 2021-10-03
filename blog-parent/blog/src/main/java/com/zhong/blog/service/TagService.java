package com.zhong.blog.service;

import java.util.List;

import com.zhong.blog.vo.Result;
import com.zhong.blog.vo.TagVo;

public interface TagService {

	List<TagVo> findTagsByArticleId(Long articleId);

	Result hots(int limit);

	Result findAll();
}
