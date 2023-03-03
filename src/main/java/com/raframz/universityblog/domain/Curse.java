package com.raframz.universityblog.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "curse", schema = "ublog")
public class Curse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime created;
    private String name;
    private String program;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "teacher_curse",
            schema = "ublog",
            joinColumns = @JoinColumn(name = "curse_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    @ToString.Exclude
    private Set<Teacher> teachers;
    @JsonIgnore
    @OneToMany(mappedBy = "curse")
    @ToString.Exclude
    private Set<Blog> blogs;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Curse curse = (Curse) o;
        return id != null && Objects.equals(id, curse.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
