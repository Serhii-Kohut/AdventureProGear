package com.example.adventureprogearjava.filter;

import com.example.adventureprogearjava.config.EndpointMaster;
import com.example.adventureprogearjava.services.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    JwtService jwtService;
    EndpointMaster endpointMaster;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        String method = request.getMethod();

        if ((HttpMethod.POST.matches(method) || HttpMethod.PUT.matches(method) || HttpMethod.DELETE.matches(method))

                && (path.startsWith("/api/public/sections")||path.startsWith("/api/productAttributes")|| path.startsWith("/api/public/products")|| path.startsWith("/api/public/categories"))) {

            return false;
        }

        return request.getMethod().equals(HttpMethod.GET.name()) &&
                endpointMaster.isEndpointMatchedWithPattern(request, "/api/blog/posts/**") ||
                endpointMaster.isEndpointMatchedWithPattern(request, "/api/productAttributes/**") ||
                endpointMaster.isEndpointMatchedWithPattern(request, "/api/blog/reactions/{postId}/count") ||
                endpointMaster.isEndpointMatchedWithPattern(request, "/api/public/**") ||
                endpointMaster.isEndpointMatchedWithPattern(request, "/api/v1/products/**");
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        if (request.getServletPath().startsWith("/swagger-ui") || request.getServletPath().startsWith("/v3/api-docs")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (endpointMaster.isHandlerNotExist(request)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);

            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (StringUtils.isBlank(authHeader) || !authHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing auth token");
            return;
        }

        String jwt = authHeader.split(" ")[1].trim();

        try {
            if (jwtService.extractType(jwt).equals("refreshToken")) {
                throw new JwtException("Refresh token cannot be used for authentication");
            }

            UserDetails userDetails = jwtService.getUserDetailsFromToken(jwt);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authToken);

        } catch (JwtException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid access token");
            return;
        }
        filterChain.doFilter(request, response);
    }

}