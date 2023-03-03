package com.raframz.universityblog.application.usecase;

import com.raframz.universityblog.application.in.IUserBlogQuery;
import com.raframz.universityblog.application.out.IUserBlogRepository;
import com.raframz.universityblog.config.ErrorCode;
import com.raframz.universityblog.config.exception.BadRequestResourceException;
import com.raframz.universityblog.config.exception.NotFoudResourceException;
import com.raframz.universityblog.domain.UserBlog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class UserUseCase implements IUserBlogQuery {

    private final IUserBlogRepository repository;

    public UserUseCase(IUserBlogRepository repository) {
        this.repository = repository;
    }

    private static final String ZERO = "0";

    @Override
    public UserBlog findUser(String username, String password) {
        if (username.equals(ZERO) || password.equals(ZERO))
            throw new BadRequestResourceException(ErrorCode.BAD_REQUEST);
        log.info("Looking for user {} : ", username);
        UserBlog user = repository.findFirstByUsernameAndPassword(username, password);
        if (Objects.isNull(user)) {
            log.warn("Not Founded user {} :", username);
            throw new NotFoudResourceException(ErrorCode.NOT_FOUND);
        }
        log.info("Founded user {}, {}:", user.getUsername(), user.getEmail());
        return user;
    }
}
