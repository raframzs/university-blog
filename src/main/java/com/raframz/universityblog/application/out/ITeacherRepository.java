package com.raframz.universityblog.application.out;

import com.raframz.universityblog.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITeacherRepository extends JpaRepository<Teacher, Integer> {
}
