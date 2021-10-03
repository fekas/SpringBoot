package com.zhong.blog.dao.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhong.blog.dao.pojo.Tag;

public interface TagMapper extends BaseMapper<Tag> {

	List<Tag> findTagsByArticleId(Long articleId);

	List<Long> findHotsTagIds(int limit);

	List<Tag> findTagsByTagIds(List<Long> tagIds);

}
