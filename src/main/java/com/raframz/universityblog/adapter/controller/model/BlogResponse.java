package com.raframz.universityblog.adapter.controller.model;

import com.raframz.universityblog.domain.Blog;
import com.raframz.universityblog.domain.Curse;
import com.raframz.universityblog.domain.UserBlog;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Value
@Builder
public class BlogResponse {

    Integer id;
    LocalDateTime created;
    String content;
    UserBlog user;
    Curse curse;
    Stats stats;

    @Value
    @Builder
    static class Stats {
        Integer likes;
        Integer up;
        Integer down;
    }

    public static BlogResponse of(Blog blog) {
        if (Objects.isNull(blog)) return null;
        AtomicInteger likes = new AtomicInteger();
        AtomicInteger ups = new AtomicInteger();
        AtomicInteger downs = new AtomicInteger();

        if (!Objects.isNull(blog.getStats()))
            blog.getStats().forEach(s -> {
                likes.addAndGet(s.getLike() ? 1 : 0);
                ups.addAndGet(s.getUp() ? 1 : 0);
                downs.addAndGet(s.getDown() ? 1 : 0);
            });

        return BlogResponse.builder()
                .id(blog.getId())
                .created(blog.getCreated())
                .content(blog.getContent())
                .user(blog.getUser())
                .curse(blog.getCurse())
                .stats(blog.getStats() != null ?
                        Stats.builder()
                                .likes(likes.get())
                                .up(ups.get())
                                .down(downs.get())
                                .build()
                        : null)
                .build();
    }

    public static List<BlogResponse> of(List<Blog> blogs) {
        List<BlogResponse> response = new ArrayList<>();
        if (Objects.isNull(blogs)) return null;
        blogs.forEach(blog -> response.add(of(blog)));
        return response;
    }

}
