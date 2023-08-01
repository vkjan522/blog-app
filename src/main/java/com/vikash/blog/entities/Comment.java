package com.vikash.blog.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "Comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentID;
    private String commentContent;
    @JsonIgnore
    @ManyToOne
    private Post post;
    @JsonIgnore
    @ManyToOne
    private User user;
}
