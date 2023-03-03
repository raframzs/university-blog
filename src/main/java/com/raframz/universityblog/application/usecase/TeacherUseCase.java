package com.raframz.universityblog.application.usecase;

import com.raframz.universityblog.application.in.ITeacherQuery;
import com.raframz.universityblog.application.out.ITeacherRepository;
import com.raframz.universityblog.domain.Teacher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeacherUseCase implements ITeacherQuery {

    private final ITeacherRepository repository;

    public TeacherUseCase(ITeacherRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Teacher> findAllTeachers() {
        return repository.findAll();
    }
}
