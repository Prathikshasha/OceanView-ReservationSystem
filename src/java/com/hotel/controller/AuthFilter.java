package com.hotel.filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        String ctx = req.getContextPath();

        // ✅ Allow these without login
        boolean isPublic =
                uri.equals(ctx + "/") ||
                uri.endsWith("index.html") ||
                uri.endsWith("login.html") ||
                uri.endsWith("/LoginServlet") ||
                uri.endsWith("/LogoutServlet");

        if (isPublic) {
            chain.doFilter(request, response);
            return;
        }

        // ✅ Check session (DO NOT create new session)
        HttpSession session = req.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute("loggedInUser") != null);

        if (loggedIn) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect(ctx + "/login.html");
        }
    }
}