package com.raframz.universityblog.adapter.controller.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SaveBlogRequest {
    Integer id;
    String content;
    Integer userId;
    Integer curseId;

}
