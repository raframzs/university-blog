package com.raframz.universityblog.adapter.controller;

import com.raframz.universityblog.adapter.RequestProcessor;
import com.raframz.universityblog.adapter.controller.model.BlogFilteredRequest;
import com.raframz.universityblog.adapter.controller.model.ResponseRest;
import com.raframz.universityblog.adapter.controller.model.SaveBlogRequest;
import com.raframz.universityblog.application.in.IBlogQuery;
import com.raframz.universityblog.config.ErrorCode;
import com.raframz.universityblog.config.exception.BadRequestResourceException;
import com.raframz.universityblog.domain.Blog;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.raframz.universityblog.adapter.controller.model.BlogResponse.of;
import static com.raframz.universityblog.adapter.util.UtilResonse.buildResponse;

@Slf4j
@RestController
@RequestMapping("/blogs")
public class BlogController {

    private final IBlogQuery query;

    private final RequestProcessor requestProcessor;

    public BlogController(IBlogQuery query, RequestProcessor requestProcessor) {
        this.query = query;
        this.requestProcessor = requestProcessor;
    }

    @GetMapping
    public ResponseRest getBlogs(final HttpServletRequest request) {
        log.info("Calling getBlogs");
        List<Blog> blogs = this.query.findAllBlogs();
        log.info("Response of getBlogs {}", blogs);
        return buildResponse(requestProcessor, request, of(blogs));
    }

    @PostMapping("/filtered")
    public ResponseRest getBlogsFiltered(final HttpServletRequest request,
                                         @Valid @RequestBody BlogFilteredRequest blogRequest) {
        log.info("Calling getBlogsFiltered");
        List<Blog> blogs = this.query.findFiltered(blogRequest);
        log.info("Response of getBlogsFiltered {}", blogs);
        return buildResponse(requestProcessor, request, of(blogs));
    }

    @GetMapping("/{id}")
    public ResponseRest getSpecificBlog(final HttpServletRequest request,
                                        @PathVariable(name = "id") Integer id) {
        log.info("Calling getSpecificBlog");
        Blog blogs = this.query.findSpecificBlog(id);
        log.info("Response of getSpecificBlog {}", blogs);
        return buildResponse(requestProcessor, request, of(blogs));
    }

    @PutMapping("/save")
    public ResponseRest saveBlog(final HttpServletRequest request,
                                 @Valid @RequestBody SaveBlogRequest blogRequest,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new BadRequestResourceException(ErrorCode.BAD_REQUEST);
        log.info("Calling saveBlog");
        Blog blogs = this.query.saveBlog(blogRequest);
        log.info("Response of saveBlog {}", blogs);
        return buildResponse(requestProcessor, request, of(blogs));
    }

}
