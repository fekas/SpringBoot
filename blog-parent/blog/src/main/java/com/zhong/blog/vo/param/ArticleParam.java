package com.zhong.blog.vo.param;

import java.util.List;

import com.zhong.blog.vo.CategoryVo;
import com.zhong.blog.vo.TagVo;

import lombok.Data;

@Data
public class ArticleParam {

    private Long id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;

    private String title;
}
