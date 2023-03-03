package com.raframz.universityblog.adapter.controller;

import com.raframz.universityblog.adapter.RequestProcessor;
import com.raframz.universityblog.adapter.controller.model.ResponseRest;
import com.raframz.universityblog.application.in.ITeacherQuery;
import com.raframz.universityblog.domain.Teacher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.raframz.universityblog.adapter.util.UtilResonse.buildResponse;

@Slf4j
@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final ITeacherQuery query;

    private final RequestProcessor requestProcessor;

    public TeacherController(ITeacherQuery query, RequestProcessor requestProcessor) {
        this.query = query;
        this.requestProcessor = requestProcessor;
    }

    @GetMapping
    public ResponseRest getTeachers(final HttpServletRequest request) {
        log.info("Calling getTeachers");
        List<Teacher> teacherList = this.query.findAllTeachers();
        log.info("Response of getTeachers {}", teacherList);
        return buildResponse(requestProcessor, request, teacherList);
    }

}
