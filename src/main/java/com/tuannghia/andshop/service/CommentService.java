package com.tuannghia.andshop.service;

import com.tuannghia.andshop.entity.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getAll();
    Comment saveComment(String content, Long blogId, Long userId);

    List<Comment> findByBlogIdOrderByCreatedAtDesc(Long blogId);
}
