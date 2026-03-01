package com.hotel.controller;

import com.hotel.util.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RevenueReportServlet", urlPatterns = {"/RevenueReportServlet"})
public class RevenueReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String from = request.getParameter("from");
        String to = request.getParameter("to");

        String sql = "SELECT r.reservation_no, r.guest_name, r.room_type, r.check_in, r.check_out, rr.rate_per_night "
                   + "FROM reservations r "
                   + "JOIN room_rates rr ON r.room_type = rr.room_type "
                   + "WHERE r.check_in BETWEEN ? AND ? "
                   + "ORDER BY r.check_in ASC";

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html><html><head><title>Revenue Report</title>");
            out.println("<style>");
            out.println("body{font-family:Arial;margin:40px;}");
            out.println("table{border-collapse:collapse;width:100%;}");
            out.println("th,td{border:1px solid #ccc;padding:8px;text-align:left;}");
            out.println("th{background:#f2f2f2;}");
            out.println("</style></head><body>");

            out.println("<h2>Revenue Report</h2>");
            out.println("<p><b>From:</b> " + from + " &nbsp;&nbsp; <b>To:</b> " + to + "</p>");

            double grandTotal = 0;

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, from);
                ps.setString(2, to);

                try (ResultSet rs = ps.executeQuery()) {

                    out.println("<table>");
                    out.println("<tr><th>Res No</th><th>Guest</th><th>Room</th><th>Check-in</th><th>Check-out</th><th>Nights</th><th>Rate</th><th>Total</th></tr>");

                    while (rs.next()) {
                        String resNo = rs.getString("reservation_no");
                        String guest = rs.getString("guest_name");
                        String room = rs.getString("room_type");

                        LocalDate inDate = LocalDate.parse(rs.getString("check_in"));
                        LocalDate outDate = LocalDate.parse(rs.getString("check_out"));
                        long nights = ChronoUnit.DAYS.between(inDate, outDate);
                        if (nights <= 0) nights = 1;

                        double rate = rs.getDouble("rate_per_night");
                        double total = nights * rate;
                        grandTotal += total;

                        out.println("<tr>");
                        out.println("<td>" + resNo + "</td>");
                        out.println("<td>" + guest + "</td>");
                        out.println("<td>" + room + "</td>");
                        out.println("<td>" + inDate + "</td>");
                        out.println("<td>" + outDate + "</td>");
                        out.println("<td>" + nights + "</td>");
                        out.println("<td>" + rate + "</td>");
                        out.println("<td>" + total + "</td>");
                        out.println("</tr>");
                    }

                    out.println("</table>");
                    out.println("<h3>Grand Total Revenue: " + grandTotal + "</h3>");
                    out.println("<button onclick='window.print()'>Print Report</button>");
                }

            } catch (Exception e) {
                out.println("<h3 style='color:red;'>Database Error</h3>");
                out.println("<pre>" + e.toString() + "</pre>");
            }

            out.println("<br><br>");
            out.println("<a href='revenueReport.html'>Generate Again</a> | ");
            out.println("<a href='DashboardServlet'>Dashboard</a>");

            out.println("</body></html>");
        }
    }
}