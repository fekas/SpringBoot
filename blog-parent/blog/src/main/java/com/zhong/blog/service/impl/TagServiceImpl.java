package com.zhong.blog.service.impl;

import java.security.cert.CollectionCertStoreParameters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhong.blog.dao.mapper.TagMapper;
import com.zhong.blog.dao.pojo.Tag;
import com.zhong.blog.service.TagService;
import com.zhong.blog.vo.Result;
import com.zhong.blog.vo.TagVo;

@Service
public class TagServiceImpl implements TagService{

	@Autowired
	private TagMapper tagMapper;
	
	@Override
	public List<TagVo> findTagsByArticleId(Long articleId) {
		
		List<Tag> tags = tagMapper.findTagsByArticleId(articleId);
		return copyList(tags);
	}
	
	public List<TagVo> copyList(List<Tag> tagList){
		List<TagVo> tagVoList = new ArrayList<>();
		
		for (Tag tag : tagList) {
			tagVoList.add(copy(tag));
		}
		return tagVoList;
	}

	public TagVo copy(Tag tag) {
		
		TagVo tagVo = new TagVo();
		
		BeanUtils.copyProperties(tag, tagVo);
		
		return tagVo;
	}

	@Override
	public Result hots(int limit) {
		
		List<Long> tagIds = tagMapper.findHotsTagIds(limit);
		
		if(CollectionUtils.isEmpty(tagIds)) {
			return Result.success(Collections.emptyList());
		}
		
		List<Tag> tagList = tagMapper.findTagsByTagIds(tagIds);
		
		return Result.success(tagList);
	}

	@Override
	public Result findAll() {
		List<Tag> tags = this.tagMapper.selectList(new LambdaQueryWrapper<>());
		return Result.success(copyList(tags));
	}

}
