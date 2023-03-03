package com.raframz.universityblog.application.in;

import com.raframz.universityblog.domain.Teacher;

import java.util.List;

public interface ITeacherQuery {

    List<Teacher> findAllTeachers();

}
