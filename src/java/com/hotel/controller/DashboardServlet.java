package com.hotel.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "DashboardServlet", urlPatterns = {"/DashboardServlet"})
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ✅ Get session (do NOT create new one if not exists)
        HttpSession session = request.getSession(false);

        // ✅ If session not found OR user not logged in → go to login
        if (session == null || session.getAttribute("loggedInUser") == null) {
            response.sendRedirect("login.html");
            return;
        }

        // ✅ User is logged in → show dashboard HTML
        String username = (String) session.getAttribute("loggedInUser");

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html><head><title>Dashboard</title>");
            out.println("<style>");
            out.println("body{font-family:Arial;margin:40px;}");
            out.println("a{display:inline-block;margin:8px 10px 0 0;padding:10px 14px;border:1px solid #333;border-radius:6px;text-decoration:none;color:#000;}");
            out.println("</style></head><body>");

            out.println("<h1>Dashboard</h1>");
            out.println("<p>Welcome, <b>" + username + "</b>! Choose an action.</p>");

            out.println("<a href='addReservation.html'>Add Reservation</a>");
            out.println("<a href='viewReservation.html'>View Reservation</a>");
            out.println("<a href='bill.html'>Calculate Bill</a>");
            out.println("<a href='reservationReport.html'>Reservation Report</a>");
            out.println("<a href='revenueReport.html'>Revenue Report</a>");
            out.println("<a href='LogoutServlet'>Logout</a>");

            out.println("</body></html>");
        }
    }
}