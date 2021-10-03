package com.zhong.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhong.blog.service.TagService;
import com.zhong.blog.vo.Result;

@RestController
@RequestMapping("tags")
public class TagsController {
	
	@Autowired
	private TagService tagService;
	
	@GetMapping("hot")
	public Result hot() {
		int limit = 6;
		return tagService.hots(limit);
	}
	
	 @GetMapping
	    public Result findAll(){
	        return tagService.findAll();
	  }

}
