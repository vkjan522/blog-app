package com.vikash.blog.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "Post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postID;

    @Column(name = "Post_Title")

    private String title;

    @Column(name = "Post_Content")

    private String content;

    @Column(name = "Post_Image_Name")

    private String imageName;

    @Column(name = "Post_Date_Name")

    private Date addedDate;

    @ManyToOne
    //@JoinColumn(name = "Category_ID")
    private Category category;
    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Comment> postComments = new HashSet<>();
}
