package com.zhong.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhong.blog.common.aop.LogAnnotation;
import com.zhong.blog.common.cache.Cache;
import com.zhong.blog.service.ArticleService;
import com.zhong.blog.vo.Result;
import com.zhong.blog.vo.param.ArticleParam;
import com.zhong.blog.vo.param.PageParams;

@RestController
@RequestMapping("articles")
public class ArticleController {
	
	@Autowired
	private ArticleService articleService;
	
	@PostMapping
	@LogAnnotation(module="article", operator="Get Article List")
	public Result listAtricles(@RequestBody PageParams pageParam) {
		return articleService.listArticle(pageParam);
	}
	
	@PostMapping("view/{id}")
	public Result findArResultById(@PathVariable("id") Long articleId) {
		return articleService.findArResultById(articleId);
	}
	
	@PostMapping("hot")
	@Cache(expire = 60 * 1000 * 5, name="hot_article")
    public Result hotArticle(){
        int limit = 5;
        return articleService.hotArticle(limit);
    }

	@PostMapping("new")
	@Cache(expire = 60 * 1000 * 5, name="new_article")
    public Result newArticles(){
        int limit = 5;
        return articleService.newArticles(limit);
    }
	
	@PostMapping("listArchives")
    public Result listArchives(){
        return articleService.listArchives();
    }
	
	@PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam){

        return articleService.publish(articleParam);
    }
}
