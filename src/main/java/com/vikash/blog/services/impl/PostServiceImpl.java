package com.vikash.blog.services.impl;

import com.vikash.blog.entities.Category;
import com.vikash.blog.entities.Post;
import com.vikash.blog.entities.User;
import com.vikash.blog.exceptions.ResourceNotFoundException;
import com.vikash.blog.payloads.PostDTO;
import com.vikash.blog.payloads.PostResponse;
import com.vikash.blog.repositories.CategoryRepository;
import com.vikash.blog.repositories.PostRepository;
import com.vikash.blog.repositories.UserRepository;
import com.vikash.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Post dtoToPost(PostDTO postDTO) {
        Post post = this.modelMapper.map(postDTO, Post.class);
        return post;
    }

    public PostDTO postToDto(Post post) {
        PostDTO postDTO = this.modelMapper.map(post, PostDTO.class);
        return postDTO;
    }


    @Override
    public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId) {

        User user = this.userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "User ID", userId));
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "Category ID", categoryId));
        Post mapped = this.modelMapper.map(postDTO, Post.class);
        mapped.setImageName("default.png");
        mapped.setAddedDate(new Date());
        mapped.setUser(user);
        mapped.setCategory(category);
        Post saved = this.postRepository.save(mapped);
        return this.modelMapper.map(saved, PostDTO.class);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Integer postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", " Post ID", postId));
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setAddedDate(new Date());
        post.setImageName(postDTO.getImageName());
        Post saved = this.postRepository.save(post);
        return this.modelMapper.map(saved, PostDTO.class);
    }

    @Override
    public void deletePost(Integer postId) {

        Post post = this.postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "Post ID", postId));
        this.postRepository.delete(post);
    }

    @Override
    public PostDTO getById(Integer postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", " Post ID", postId));
        return this.modelMapper.map(post, PostDTO.class);
    }

    @Override
    public PostResponse getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {

        Sort sort = null;
        if (sortDirection.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        } else if (sortDirection.equalsIgnoreCase("dsc")){
            sort = Sort.by(sortBy).descending();
        }
        Pageable p = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePost = this.postRepository.findAll(p);
        List<Post> all = pagePost.getContent();
        List<PostDTO> collected = all.stream().map((post) ->
                this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(collected);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        postResponse.setTotalElements(pagePost.getTotalElements());
        return postResponse;
    }

    @Override
    public PostResponse getPostsByCategory(Integer categoryId, Integer pageN, Integer pageS) {
        Pageable p = PageRequest.of(pageN, pageS);
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "Category ID", categoryId));
        Page<Post> byCategory = this.postRepository.findByCategory(category, p);
        List<PostDTO> postDTOList = byCategory.stream().map((post) ->
                this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDTOList);
        postResponse.setPageNumber(byCategory.getNumber());
        postResponse.setPageSize(byCategory.getSize());
        postResponse.setTotalPages(byCategory.getTotalPages());
        postResponse.setLastPage(byCategory.isLast());
        postResponse.setTotalElements(byCategory.getTotalElements());
        return postResponse;
    }

    @Override
    public PostResponse getPostByUser(Integer userId, Integer pageNumber, Integer pageSize) {
        Pageable p = PageRequest.of(pageNumber, pageSize);
        User user = this.userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "User ID", userId));
        Page<Post> byUser = this.postRepository.findByUser(user, p);
        List<Post> byUserContent = byUser.getContent();
        List<PostDTO> collected = byUserContent.stream().map((post) ->
                this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(collected);
        postResponse.setPageNumber(byUser.getNumber());
        postResponse.setPageSize(byUser.getSize());
        postResponse.setTotalPages(byUser.getTotalPages());
        postResponse.setLastPage(byUser.isLast());
        postResponse.setTotalElements(byUser.getTotalElements());
        return postResponse;
    }

    @Override
    public List<PostDTO> searchByString(String keyword) {

//        List<PostDTO> posts = this.postRepository.searchByTitle("%"+keyword+"%");

        List<Post> byTitleContaining = this.postRepository.findByTitleContaining(keyword);
        List<PostDTO> collectedDto = byTitleContaining.stream().map((post) -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
        return collectedDto;
    }


}
