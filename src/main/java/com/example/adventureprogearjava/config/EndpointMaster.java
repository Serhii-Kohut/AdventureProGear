package com.example.adventureprogearjava.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Component
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EndpointMaster {
    AntPathMatcher matcher;

    RequestMappingHandlerMapping requestMappingHandlerMapping;

    public EndpointMaster(RequestMappingHandlerMapping requestMappingHandlerMapping) {
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;

        matcher = new AntPathMatcher();
    }

    public boolean isHandlerNotExist(HttpServletRequest request) {
        HandlerExecutionChain handler;

        try {
            handler = requestMappingHandlerMapping.getHandler(request);
        } catch (Exception e) {
            return true;
        }

        return handler == null;
    }

    public boolean isEndpointMatchedWithPattern(HttpServletRequest request, String pattern) {
        return matcher.match(pattern, request.getServletPath());
    }

}
