package com.raframz.universityblog.adapter.util;

import com.raframz.universityblog.adapter.RequestProcessor;
import com.raframz.universityblog.adapter.controller.model.ResponseRest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

@UtilityClass
public class UtilResonse {

    public static ResponseRest buildResponse(RequestProcessor requestProcessor,
                                             HttpServletRequest request, Object object) {
        return requestProcessor.processRequest(
                RequestProcessor.EnrichedRequest.of(request),
                result -> new ResponseRest(
                        result.getId(),
                        HttpStatus.OK.value(),
                        result.getRequest().getRequestURI(),
                        object
                ));
    }

}
