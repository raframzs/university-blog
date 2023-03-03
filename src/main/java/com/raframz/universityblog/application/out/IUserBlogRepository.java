package com.raframz.universityblog.application.out;

import com.raframz.universityblog.domain.UserBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserBlogRepository extends JpaRepository<UserBlog, Integer> {
    UserBlog findFirstByUsernameAndPassword(String username, String password);
}
