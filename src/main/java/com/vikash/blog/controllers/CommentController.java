package com.vikash.blog.controllers;

import com.vikash.blog.payloads.ApiResponse;
import com.vikash.blog.payloads.CommentDTO;
import com.vikash.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/comment/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("postID/{post_ID}/userID/{user_ID}/")
    public ResponseEntity<CommentDTO> createComment(
            @RequestBody CommentDTO commentDTO,
            @PathVariable(name = "post_ID") Integer postId,
            @PathVariable(name = "user_ID") Integer userId
    ) {
        CommentDTO comment = this.commentService.createComment(commentDTO, postId, userId);
        return new ResponseEntity<CommentDTO>(comment, HttpStatus.OK);
    }

    @DeleteMapping("delete/{comment_ID}/")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable("comment_ID") Integer commentId) {
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse>
                (new ApiResponse("Comment Deleted Successfully", true), HttpStatus.OK);
    }

    @GetMapping("getByID/{id}/")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Integer id) {
        CommentDTO commentByID = this.commentService.getCommentByID(id);
        return new ResponseEntity<CommentDTO>(commentByID, HttpStatus.OK);
    }


}
