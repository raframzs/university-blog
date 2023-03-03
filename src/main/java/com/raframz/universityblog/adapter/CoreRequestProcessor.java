package com.raframz.universityblog.adapter;

import com.raframz.universityblog.adapter.controller.model.ResponseRest;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CoreRequestProcessor implements RequestProcessor {

    @Override
    public ResponseRest processRequest(EnrichedRequest request, Function<EnrichedRequest, ResponseRest> operation) {
        return operation.apply(request);
    }

}
