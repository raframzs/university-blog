package com.raframz.universityblog.adapter.controller;

import com.raframz.universityblog.adapter.RequestProcessor;
import com.raframz.universityblog.adapter.controller.model.ResponseRest;
import com.raframz.universityblog.application.in.IUserBlogQuery;
import com.raframz.universityblog.domain.UserBlog;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.raframz.universityblog.adapter.util.UtilResonse.buildResponse;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserBlogController {
    private final IUserBlogQuery query;
    private final RequestProcessor requestProcessor;

    public UserBlogController(IUserBlogQuery query, RequestProcessor requestProcessor) {
        this.query = query;
        this.requestProcessor = requestProcessor;
    }

    @GetMapping
    public ResponseRest getUser(final HttpServletRequest request,
                                @RequestParam(name = "name", defaultValue = "0") String name,
                                @RequestParam(name = "password", defaultValue = "0") String password) {
        log.info("Calling getUser");
        UserBlog curseList = this.query.findUser(name, password);
        log.info("Response of getUser {}", curseList);
        return buildResponse(requestProcessor, request, curseList);
    }
}
