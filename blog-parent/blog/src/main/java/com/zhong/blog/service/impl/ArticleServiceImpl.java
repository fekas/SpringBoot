package com.zhong.blog.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.zhong.blog.dao.dos.Archives;
import com.zhong.blog.dao.mapper.ArticleBodyMapper;
import com.zhong.blog.dao.mapper.ArticleMapper;
import com.zhong.blog.dao.mapper.ArticleTagMapper;
import com.zhong.blog.dao.pojo.Article;
import com.zhong.blog.dao.pojo.ArticleBody;
import com.zhong.blog.dao.pojo.ArticleTag;
import com.zhong.blog.dao.pojo.SysUser;
import com.zhong.blog.service.ArticleService;
import com.zhong.blog.service.CategoryService;
import com.zhong.blog.service.SysUserService;
import com.zhong.blog.service.TagService;
import com.zhong.blog.service.ThreadService;
import com.zhong.blog.util.UserThreadLocal;
import com.zhong.blog.vo.ArticleBodyVo;
import com.zhong.blog.vo.ArticleVo;
import com.zhong.blog.vo.Result;
import com.zhong.blog.vo.TagVo;
import com.zhong.blog.vo.param.ArticleParam;
import com.zhong.blog.vo.param.PageParams;

@Service
public class ArticleServiceImpl implements ArticleService{

	@Autowired
	private ArticleMapper articleMapper;
	
	@Autowired
	private TagService tagService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private ArticleTagMapper articleTagMapper;
	
	@Override
	public Result listArticle(PageParams pageParam) {
		
		Page<Article> page = new Page<>(pageParam.getPage(),pageParam.getPageSize());
		LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>();
		
		queryWrapper.orderByDesc(Article::getWeight, Article::getCreateDate);
		
		Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
		
		List<Article> records = articlePage.getRecords();
		
		List<ArticleVo> articleVoList = copyList(records, true, true);
		
		return Result.success(articleVoList);
	}

	private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor) {
		List<ArticleVo> articleVoList = new ArrayList<>();
		for (Article article : records) {
			articleVoList.add(copy(article, isTag, isAuthor, false, false));
		}
		return articleVoList;
	}
	
	private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
		List<ArticleVo> articleVoList = new ArrayList<>();
		for (Article article : records) {
			articleVoList.add(copy(article, isTag, isAuthor, isBody, isCategory));
		}
		return articleVoList;
	}
	
	
	@Autowired
	private CategoryService categoryServce;
	
	private ArticleVo copy(Article article, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {

		ArticleVo articleVo = new ArticleVo();
		BeanUtils.copyProperties(article, articleVo);
		
		articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
		
		if(isTag) {
			Long articleId = article.getId();
			articleVo.setTags(tagService.findTagsByArticleId(articleId));
		}
		if(isAuthor) {
			Long authorId = article.getAuthorId();
			articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
		}
		if(isBody) {
			Long bodyId = article.getBodyId();
			articleVo.setBody(findArticleBodyById(bodyId));
			
		}
		if(isCategory) {
			Long categoryId = article.getCategoryId();
			articleVo.setCategory(categoryServce.findCategoryById(categoryId));
		}
		
		return articleVo;
	}

	@Autowired
	private ArticleBodyMapper articleBodyMapper;
	
	private ArticleBodyVo findArticleBodyById(Long bodyId) {
		ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
		ArticleBodyVo articleBodyVo = new ArticleBodyVo();
		articleBodyVo.setContent(articleBody.getContent());
		return articleBodyVo;
	}

	@Autowired
	private ThreadService threadService;
	@Override
	public Result findArResultById(Long articleId) {
		
		Article article = this.articleMapper.selectById(articleId);
		
		ArticleVo articleVo = copy(article, true, true, true, true);
		
		//线程池
		threadService.updateArticleViewCount(articleMapper, article);
		
		return Result.success(articleVo);
	}

	@Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit);
        //select id,title from article order by view_counts desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);

        return Result.success(copyList(articles,false,false));
    }
	
    @Override
    public Result newArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit);
        //select id,title from article order by create_date desc desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);

        return Result.success(copyList(articles,false,false));
    }
    
    public Result listArchives() {
        List<Archives> archivesList = articleMapper.listArchives();
        return Result.success(archivesList);
    }

	@Override
	public Result publish(ArticleParam articleParam) {
		
		SysUser sysUser = UserThreadLocal.get();
		
		Article article =  new Article();
		
		article.setAuthorId(sysUser.getId());
		
		article.setWeight(Article.Article_Common);
		
		article.setViewCounts(0);
		
		article.setTitle(articleParam.getTitle());
		
		article.setSummary(articleParam.getSummary());
		
		article.setCommentCounts(0);
		
		article.setCreateDate(System.currentTimeMillis());
		
		article.setCategoryId(Long.valueOf(articleParam.getCategory().getId()));
		
		this.articleMapper.insert(article);
		
		List<TagVo> tags = articleParam.getTags();
		
		Long articleId = article.getId();
		
		if(null != tags) {
			
			for (TagVo tagVo : tags) {
				
				ArticleTag articleTag = new ArticleTag();
				
				articleTag.setTagId(tagVo.getId());
				articleTag.setArticleId(articleId);
				
				articleTagMapper.insert(articleTag);
			}
			
		}
		
		ArticleBody articleBody = new ArticleBody();
		
		articleBody.setArticleId(articleId);
		articleBody.setContent(articleParam.getBody().getContent());
		articleBody.setContentHtml(articleParam.getBody().getContentHtml());
		articleBodyMapper.insert(articleBody);
		
		article.setBodyId(articleBody.getId());
		articleMapper.updateById(article);
		
		Map<String, String> map = new HashMap<>();
		map.put("id", articleId.toString());
		
		return Result.success(map);
	}

}
