package com.example.adventureprogearjava.exceptions;
<<<<<<< HEAD

=======
>>>>>>> 769e973 (bug/fix/productAttributeController :)
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
<<<<<<< HEAD
        response.getWriter().write("\n" +
                "Access denied");
=======
        response.getWriter().write("The creation of products is allowed only for admins");
>>>>>>> 769e973 (bug/fix/productAttributeController :)
    }
}
