package com.vikash.blog.services;

import com.vikash.blog.entities.Post;
import com.vikash.blog.payloads.PostDTO;
import com.vikash.blog.payloads.PostResponse;

import java.util.List;

public interface PostService {
    //create Post
    PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId);

    //update Post By id
    PostDTO updatePost(PostDTO postDTO, Integer postId);

    //delete Post By ID
    void deletePost(Integer postId);

    //get Post By ID
    PostDTO getById(Integer postId);

    //get All Post
    PostResponse getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);

    //Get All Post By Category
    PostResponse getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize);

    //Get All Post By User
    PostResponse getPostByUser(Integer userId, Integer pageNumber, Integer pageSize);

    //Search All post
    List<PostDTO> searchByString(String keyword);
}
