package com.raframz.universityblog.adapter.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class ResponseRest {
    String id;
    Integer status;
    String resource;
    Object data;
}
