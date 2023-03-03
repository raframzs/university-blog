package com.raframz.universityblog.application.in;

import com.raframz.universityblog.domain.UserBlog;

public interface IUserBlogQuery {
    UserBlog findUser(String username, String password);
}
