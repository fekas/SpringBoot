package com.zhong.blog.service;

import com.zhong.blog.vo.Result;
import com.zhong.blog.vo.param.CommentParam;

public interface CommentsService {
    /**
     * 根据文章id 查询所有的评论列表
     * @param id
     * @return
     */
    Result commentsByArticleId(Long id);

    Result comment(CommentParam commentParam);
}
