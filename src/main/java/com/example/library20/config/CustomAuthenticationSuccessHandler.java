package com.example.library20.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String userRole = authentication.getAuthorities().toArray()[0].toString();
        if(userRole != null){
            HttpSession session = request.getSession();
            session.setAttribute("userRole", userRole);
            response.sendRedirect("/books/public/all");
        }
    }
}
