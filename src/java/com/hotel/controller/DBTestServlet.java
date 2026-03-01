package com.hotel.controller;

import com.hotel.util.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DBTestServlet", urlPatterns = {"/DBTestServlet"})
public class DBTestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            try (Connection con = DBConnection.getConnection()) {
                out.println("<h2 style='color:green;'>DB Connected ✅</h2>");
                out.println("<p>Connection OK: " + con + "</p>");
            } catch (Exception e) {
                out.println("<h2 style='color:red;'>DB Connection Failed ❌</h2>");
                out.println("<pre>" + e.getMessage() + "</pre>");
            }
        }
    }
}