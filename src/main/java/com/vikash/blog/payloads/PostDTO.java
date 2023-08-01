package com.vikash.blog.payloads;

import com.vikash.blog.entities.Comment;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Integer id;
    @Size(min = 5, max = 10, message = "Post Title will be 5 to 10 character!!")
    @NotEmpty
    private String title;
    @Size(min = 10, max = 20, message = "Content  size will be 10 to 20 character!!")
    @NotEmpty
    private String content;
    @Size(min = 3, max = 10, message = "Image name will 3 to 10 character!!")
    private String imageName;
    private Date addedDate;
    private CategoryDTO category;
    private UserDTO user;
    private Set<Comment> comments = new HashSet<>();
}
