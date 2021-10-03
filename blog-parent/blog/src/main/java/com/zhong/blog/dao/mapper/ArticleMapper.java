package com.zhong.blog.dao.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhong.blog.dao.dos.Archives;
import com.zhong.blog.dao.pojo.Article;

public interface ArticleMapper extends BaseMapper<Article>{

	List<Archives> listArchives();

}
