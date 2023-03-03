package com.raframz.universityblog.application.out;

import com.raframz.universityblog.domain.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBlogRepository extends JpaRepository<Blog, Integer> {

    @Query(value = "SELECT b FROM Blog b " +
            "INNER JOIN UserBlog u ON b.user.id = u.id " +
            "INNER JOIN Curse c ON b.curse.id = c.id " +
            "WHERE " +
            "b.content LIKE %:content% AND " +
            "u.username LIKE %:userName% AND " +
            "u.email LIKE %:userEmail% AND " +
            "c.name LIKE %:curseName% AND " +
            "c.program LIKE %:curseProgram% ")
    List<Blog> findBlogsFiltered(@Param("content") String content, @Param("userName") String userName,
                                 @Param("userEmail") String userEmail, @Param("curseName") String curseName,
                                 @Param("curseProgram") String curseProgram);

}
