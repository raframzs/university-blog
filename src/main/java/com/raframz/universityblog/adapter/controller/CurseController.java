package com.raframz.universityblog.adapter.controller;

import com.raframz.universityblog.adapter.RequestProcessor;
import com.raframz.universityblog.adapter.controller.model.ResponseRest;
import com.raframz.universityblog.application.in.ICurseQuery;
import com.raframz.universityblog.domain.Curse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.raframz.universityblog.adapter.util.UtilResonse.buildResponse;

@Slf4j
@RestController
@RequestMapping("/curses")
public class CurseController {
    private final ICurseQuery query;

    private final RequestProcessor requestProcessor;

    public CurseController(ICurseQuery query, RequestProcessor requestProcessor) {
        this.query = query;
        this.requestProcessor = requestProcessor;
    }

    @GetMapping
    public ResponseRest getCurses(final HttpServletRequest request) {
        log.info("Calling getCurses");
        List<Curse> curseList = this.query.findAllCurses();
        log.info("Response of getCurses {}", curseList);
        return buildResponse(requestProcessor, request, curseList);
    }
}
