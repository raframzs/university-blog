package com.raframz.universityblog.application.usecase;

import com.raframz.universityblog.adapter.controller.model.BlogFilteredRequest;
import com.raframz.universityblog.adapter.controller.model.SaveBlogRequest;
import com.raframz.universityblog.application.in.IBlogQuery;
import com.raframz.universityblog.application.out.IBlogRepository;
import com.raframz.universityblog.application.out.ICurseRepository;
import com.raframz.universityblog.application.out.IUserBlogRepository;
import com.raframz.universityblog.config.ErrorCode;
import com.raframz.universityblog.config.exception.BadRequestResourceException;
import com.raframz.universityblog.config.exception.NotFoudResourceException;
import com.raframz.universityblog.domain.Blog;
import com.raframz.universityblog.domain.Curse;
import com.raframz.universityblog.domain.UserBlog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class BlogUseCase implements IBlogQuery {

    private final IBlogRepository blogRepository;

    private final ICurseRepository curseRepository;

    private final IUserBlogRepository userBlogRepository;

    public BlogUseCase(IBlogRepository blogRepository, ICurseRepository curseRepository, IUserBlogRepository userBlogRepository) {
        this.blogRepository = blogRepository;
        this.curseRepository = curseRepository;
        this.userBlogRepository = userBlogRepository;
    }

    @Override
    public List<Blog> findAllBlogs() {
        return blogRepository.findAll(Sort.by(Sort.Direction.ASC, "created"));
    }

    @Override
    public List<Blog> findFiltered(BlogFilteredRequest blog) {
        return blogRepository.findBlogsFiltered(blog.getContent(), blog.getUserName(), blog.getUserEmail(),
                blog.getCurseName(), blog.getCurseProgram());
    }

    @Override
    public Blog findSpecificBlog(Integer id) {
        return blogRepository.findById(id).orElse(null);
    }

    @Override
    public Blog saveBlog(SaveBlogRequest blog) {
        return (blog.getId().equals(0)) ? newBlog(blog) : updateBlog(blog);
    }

    private Blog updateBlog(SaveBlogRequest blog) {
        log.warn("Updating Blog");
        Blog blogToUpdate = blogRepository.findById(blog.getId()).orElse(null);
        if (blogToUpdate == null) throw new NotFoudResourceException(ErrorCode.NOT_FOUND);
        blogToUpdate.setContent(blog.getContent());
        return blogRepository.save(blogToUpdate);
    }

    private Blog newBlog(SaveBlogRequest request) {
        Curse curse = curseRepository.findById(request.getCurseId()).orElse(null);
        UserBlog userBlog = userBlogRepository.findById(request.getUserId()).orElse(null);
        if (Objects.isNull(curse) || Objects.isNull(userBlog)) {
            log.error("The course {} or the user {} where not found :", curse, userBlog);
            throw new BadRequestResourceException(ErrorCode.BAD_REQUEST);
        }
        log.warn("Creating a new blog from curse {} and user {}", curse, userBlog);
        Blog blog = blogRepository.save(Blog.toDomain(request, userBlog, curse));
        log.info("The blog has been created {} :", blog);
        return blog;
    }

}
