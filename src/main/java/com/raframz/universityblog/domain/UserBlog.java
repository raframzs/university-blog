package com.raframz.universityblog.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "userblog", schema = "ublog")
public class UserBlog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime created;
    private String username;
    private String password;
    private String email;
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Blog> blog;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserBlog userBlog = (UserBlog) o;
        return id != null && Objects.equals(id, userBlog.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
