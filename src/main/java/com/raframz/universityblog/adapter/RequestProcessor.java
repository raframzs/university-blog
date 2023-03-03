package com.raframz.universityblog.adapter;

import com.raframz.universityblog.adapter.controller.model.ResponseRest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

public interface RequestProcessor {

    Integer DEFAULT_PAGE_NUMBER = 0;
    Integer DEAFAULT_PAGE_SIZE = 0;

    ResponseRest processRequest(EnrichedRequest request,
                                Function<EnrichedRequest, ResponseRest> operation);

    @Getter
    @RequiredArgsConstructor
    @EqualsAndHashCode
    final class EnrichedRequest {
        private final String id;
        private final HttpServletRequest request;
        private final Integer pageNumber;
        private final Integer pageSize;


        public static EnrichedRequest of(HttpServletRequest request) {
            return of("", request, DEFAULT_PAGE_NUMBER, DEAFAULT_PAGE_SIZE);
        }

        public static EnrichedRequest of(String id, HttpServletRequest request, Integer pageNumber, Integer pageSize) {
            return new EnrichedRequest(id, request, pageNumber, pageSize);
        }

    }

}
