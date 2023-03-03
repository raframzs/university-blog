package com.raframz.universityblog.application.in;

import com.raframz.universityblog.adapter.controller.model.BlogFilteredRequest;
import com.raframz.universityblog.adapter.controller.model.SaveBlogRequest;
import com.raframz.universityblog.domain.Blog;

import java.util.List;

public interface IBlogQuery {

    List<Blog> findAllBlogs();

    List<Blog> findFiltered(BlogFilteredRequest blog);

    Blog findSpecificBlog(Integer id);

    Blog saveBlog(SaveBlogRequest blog);


}
