package com.hotel.controller;

import com.hotel.util.DBConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String sql = "SELECT username FROM users WHERE username = ? AND password = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    // ✅ valid user → create session
                    HttpSession session = request.getSession(true);
                    session.setAttribute("loggedInUser", username);

                    // ✅ go to protected dashboard
                    response.sendRedirect("DashboardServlet");
                } else {
                    // ❌ invalid user
                    response.sendRedirect("login.html?error=1");
                }
            }

        } catch (Exception e) {
            // If DB error happens, show error quickly (for debugging)
            response.setContentType("text/plain");
            response.getWriter().println("DB Error: " + e.toString());
        }
    }
}