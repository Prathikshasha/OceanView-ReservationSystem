package com.hotel.controller;

import com.hotel.util.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ReservationReportServlet", urlPatterns = {"/ReservationReportServlet"})
public class ReservationReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String from = request.getParameter("from");
        String to = request.getParameter("to");

        String sql = "SELECT reservation_no, guest_name, room_type, check_in, check_out "
                   + "FROM reservations "
                   + "WHERE check_in BETWEEN ? AND ? "
                   + "ORDER BY check_in ASC";

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html><html><head><title>Reservation Report</title>");
            out.println("<style>");
            out.println("body{font-family:Arial;margin:40px;}");
            out.println("table{border-collapse:collapse;width:100%;}");
            out.println("th,td{border:1px solid #ccc;padding:8px;text-align:left;}");
            out.println("th{background:#f2f2f2;}");
            out.println("</style></head><body>");

            out.println("<h2>Reservation Report</h2>");
            out.println("<p><b>From:</b> " + from + " &nbsp;&nbsp; <b>To:</b> " + to + "</p>");

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, from);
                ps.setString(2, to);

                try (ResultSet rs = ps.executeQuery()) {
                    out.println("<table>");
                    out.println("<tr><th>Reservation No</th><th>Guest Name</th><th>Room Type</th><th>Check-in</th><th>Check-out</th></tr>");

                    int count = 0;
                    while (rs.next()) {
                        count++;
                        out.println("<tr>");
                        out.println("<td>" + rs.getString("reservation_no") + "</td>");
                        out.println("<td>" + rs.getString("guest_name") + "</td>");
                        out.println("<td>" + rs.getString("room_type") + "</td>");
                        out.println("<td>" + rs.getString("check_in") + "</td>");
                        out.println("<td>" + rs.getString("check_out") + "</td>");
                        out.println("</tr>");
                    }
                    out.println("</table>");

                    out.println("<p><b>Total Reservations:</b> " + count + "</p>");
                    out.println("<button onclick='window.print()'>Print Report</button>");
                }

            } catch (Exception e) {
                out.println("<h3 style='color:red;'>Database Error</h3>");
                out.println("<pre>" + e.toString() + "</pre>");
            }

            out.println("<br><br>");
            out.println("<a href='reservationReport.html'>Generate Again</a> | ");
            out.println("<a href='DashboardServlet'>Dashboard</a>");

            out.println("</body></html>");
        }
    }
}