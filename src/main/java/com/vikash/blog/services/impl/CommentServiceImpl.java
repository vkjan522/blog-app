package com.vikash.blog.services.impl;

import com.vikash.blog.entities.Comment;
import com.vikash.blog.entities.Post;
import com.vikash.blog.entities.User;
import com.vikash.blog.exceptions.ResourceNotFoundException;
import com.vikash.blog.payloads.CommentDTO;
import com.vikash.blog.repositories.CommentRepository;
import com.vikash.blog.repositories.PostRepository;
import com.vikash.blog.repositories.UserRepository;
import com.vikash.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDTO createComment(CommentDTO commentDTO, Integer postId, Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "User ID", userId));
        Post post = this.postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "Post ID", postId));
        Comment comment = this.modelMapper.map(commentDTO, Comment.class);
        comment.setPost(post);
        comment.setUser(user);
        Comment savedComment = this.commentRepository.save(comment);
        CommentDTO commentDTO1 = modelMapper.map(savedComment, CommentDTO.class);
        return commentDTO1;
    }

    @Override
    public void deleteComment(Integer id) {
        Comment comment = this.commentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "Comment ID", id));
        this.commentRepository.delete(comment);
    }

    @Override
    public CommentDTO getCommentByID(Integer id) {
        Comment comment = this.commentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "Comment ID", id));
        CommentDTO map = this.modelMapper.map(comment, CommentDTO.class);
        return map;
    }
}
