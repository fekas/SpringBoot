package com.zhong.blog.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.zhong.blog.dao.mapper.ArticleMapper;
import com.zhong.blog.dao.pojo.Article;

@Component
public class ThreadService {

	@Async("taskExecutor")
	public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {
		Integer viewCounts = article.getViewCounts();
		Article articleUpdate = new Article();
		articleUpdate.setViewCounts(viewCounts + 1);
		LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<Article>();
		updateWrapper.eq(Article::getId, article.getId());
		
		updateWrapper.eq(Article::getViewCounts, article.getViewCounts());
		
		articleMapper.update(articleUpdate, updateWrapper);
		
	}
	

}
