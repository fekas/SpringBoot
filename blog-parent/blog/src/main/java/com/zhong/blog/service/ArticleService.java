package com.zhong.blog.service;

import com.zhong.blog.vo.Result;
import com.zhong.blog.vo.param.ArticleParam;
import com.zhong.blog.vo.param.PageParams;

public interface ArticleService {

	Result listArticle(PageParams pageParam);

	Result findArResultById(Long articleId);

	Result hotArticle(int limit);

	Result newArticles(int limit);

	Result listArchives();

	Result publish(ArticleParam articleParam);

}
