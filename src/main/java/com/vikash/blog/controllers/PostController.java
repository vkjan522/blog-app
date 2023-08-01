package com.vikash.blog.controllers;

import com.vikash.blog.config.AppConstants;
import com.vikash.blog.payloads.ApiResponse;
import com.vikash.blog.payloads.PostDTO;
import com.vikash.blog.payloads.PostResponse;
import com.vikash.blog.services.FileService;
import com.vikash.blog.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@RestController
@RequestMapping("/api/post/")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    //create Post
    @PostMapping("user/{user}/category/{category}/post/")
    public ResponseEntity<PostDTO> createPost(@Valid
                                              @RequestBody PostDTO postDTO,
                                              @PathVariable("user") Integer userID,
                                              @PathVariable("category") Integer CategoryID) {
        PostDTO postDTO1 = this.postService.createPost(postDTO, userID, CategoryID);
        return new ResponseEntity<PostDTO>(postDTO1, HttpStatus.CREATED);
    }

    @GetMapping("categoryID/{category_id}/pageNumber/pageSize/post/")
    //localhost:8080/api/post/categoryID/{}/pageNumber/pageSize/post/?pageNumber=?&pageSize=?
    public ResponseEntity<PostResponse> getPostCategory(@PathVariable("category_id") Integer cat_ID,
                                                        @RequestParam(name = "pageNumber", defaultValue = "0", required = false) Integer pageNum,
                                                        @RequestParam(name = "pageSize", defaultValue = "5", required = false) Integer pageSize
    ) {
        PostResponse postsByCategory = this.postService.getPostsByCategory(cat_ID, pageNum, pageSize);
        return new ResponseEntity<PostResponse>(postsByCategory, HttpStatus.OK);
    }

    @GetMapping("userID/{user_id}/post/")
    //localhost:8080/api/post/userID/{}/pageNumber/pageSize/post/?pageNumber=?&pageSize=?
    public ResponseEntity<PostResponse> getPostUser(@PathVariable("user_id") Integer userid,
                                                    @RequestParam(name = "pageNumber", defaultValue = "0", required = false) Integer pageNum,
                                                    @RequestParam(name = "pageSize", defaultValue = "5", required = false) Integer pageSi) {
        PostResponse postByUser = this.postService.getPostByUser(userid, pageNum, pageSi);
        return new ResponseEntity<PostResponse>(postByUser, HttpStatus.OK);

    }

    @GetMapping("postID/{post_ID}/post/") //localhost:8080/api/post/postID/{}/post/
    public ResponseEntity<PostDTO> getPostById(@PathVariable("post_ID") Integer id) {
        PostDTO byId = this.postService.getById(id);
        return new ResponseEntity<PostDTO>(byId, HttpStatus.OK);
    }

   /* @GetMapping("page/number/{page_Number}/size/{page_size}/posts/")
   //localhost:8080/api/post/page/number/{}/size/{}/posts/
    public ResponseEntity<List<PostDTO>> getAllPost(@PathVariable("page_Number") Integer number,
                                                    @PathVariable("page_size") Integer size) {
      */

    @GetMapping("pageNumber/pageSize/sortBy/posts/")
    //localhost:8080/api/post/pageNumber/pageSize/sortBy/posts/?page_Number=?&page_Size=?&sortBy=?
    public ResponseEntity<PostResponse> getAllPost(@RequestParam(value = "page_Number", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer number,
                                                   @RequestParam(value = "page_Size", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer size,
                                                   @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                                   @RequestParam(value = "sortDirection", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortDirection) {
        PostResponse postServiceAll = this.postService.getAll(number, size, sortBy, sortDirection);
        return new ResponseEntity<PostResponse>(postServiceAll, HttpStatus.OK);
    }

    @DeleteMapping("postID/{postID}/Delete_Post/") //localhost:8080/api/post/postID/{}/Delete_Post/
    public ResponseEntity<ApiResponse> deleteByID(@PathVariable("postID") Integer id) {
        this.postService.deletePost(id);
        return new ResponseEntity(new ApiResponse("Post Delete Successfully", true), HttpStatus.OK);
    }

    @PostMapping("postID/{post_ID}/Update_Post/") //localhost:8080/api/post/postID/{}/Update_Post/
    public ResponseEntity<PostDTO> update(@Valid @RequestBody PostDTO postDTO, @PathVariable("post_ID") Integer id) {
        PostDTO postDTO1 = this.postService.updatePost(postDTO, id);
        return new ResponseEntity<PostDTO>(postDTO1, HttpStatus.OK);

    }

    @GetMapping("search/posts/{title}")
    public ResponseEntity<List<PostDTO>> searchByTitle(@PathVariable("title") String strTitle) {
        List<PostDTO> postDTOList = this.postService.searchByString(strTitle);
        return new ResponseEntity<List<PostDTO>>(postDTOList, HttpStatus.OK);
    }


    //Post Image Upload
    @PostMapping("postID/{postID}/image/upload/")
    //localhost:8080/api/post/postID/{}/image/upload/{}/
    public ResponseEntity<PostDTO> uploadPostImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable(name = "postID") Integer id
    ) throws IOException {

        PostDTO postDto = this.postService.getById(id);
        String fileName = this.fileService.uploadImage(path, image);
        postDto.setImageName(fileName);
        PostDTO updatePost = this.postService.updatePost(postDto, id);
        return new ResponseEntity<PostDTO>(updatePost, HttpStatus.OK);

    }

    //Image Download by ID
    @GetMapping(value = "image/profiles/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String image, HttpServletResponse response) throws IOException {
        InputStream resource = this.fileService.getResource(path, image);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }


}
