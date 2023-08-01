package com.vikash.blog.services;

import com.vikash.blog.payloads.CommentDTO;

public interface CommentService {
    CommentDTO createComment(CommentDTO commentDTO, Integer postId, Integer userId);
    void deleteComment(Integer id);
    CommentDTO getCommentByID(Integer id);
}
