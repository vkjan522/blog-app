package com.vikash.blog.repositories;

import com.vikash.blog.entities.Category;
import com.vikash.blog.entities.Post;
import com.vikash.blog.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    Page<Post> findByUser(User user, Pageable pageable);

    Page<Post> findByCategory(Category category, Pageable pageable);

    List<Post> findByTitleContaining(String title);

/* Rule to write customize Method for own query

    // case-in-sensitive searching
    @Query("select p from Post p where lower(p.title) like concat('%', :keyword,'%')")
    public List<Post> searchPostHavingKeyword(@Param("keyword") String keyword);

    @Query("select p from Post p where p.title like :key")
    List<Post> searchByTitle(@Param("key") String str);*/

}
