package com.raframz.universityblog.domain;

import com.raframz.universityblog.adapter.controller.model.SaveBlogRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "blog", schema = "ublog")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime created;
    private String content;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserBlog user;
    @ManyToOne
    @JoinColumn(name = "curse_id", referencedColumnName = "id")
    private Curse curse;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Blog blog = (Blog) o;
        return id != null && Objects.equals(id, blog.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public static Blog toDomain(SaveBlogRequest request, UserBlog user, Curse curse) {
        Blog blog = new Blog();
        blog.setCreated(LocalDateTime.now());
        blog.setContent(request.getContent());
        blog.setUser(user);
        blog.setCurse(curse);
        return blog;
    }

}
