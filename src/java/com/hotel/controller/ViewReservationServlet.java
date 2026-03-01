package com.hotel.controller;

import com.hotel.dao.ReservationDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ViewReservationServlet", urlPatterns = {"/ViewReservationServlet"})
public class ViewReservationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String reservationNo = request.getParameter("reservation_no");

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html><html><head><title>Reservation Details</title></head>");
            out.println("<body style='font-family:Arial; margin:40px;'>");

            try {
                ReservationDAO dao = new ReservationDAO();
                ResultSet rs = dao.findByReservationNo(reservationNo); // or findReservation()

                if (rs.next()) {
                    out.println("<h2 style='color:green;'>Reservation Found ✅</h2>");
                    out.println("<p><b>Reservation No:</b> " + rs.getString("reservation_no") + "</p>");
                    out.println("<p><b>Guest Name:</b> " + rs.getString("guest_name") + "</p>");
                    out.println("<p><b>Address:</b> " + rs.getString("address") + "</p>");
                    out.println("<p><b>Contact:</b> " + rs.getString("contact") + "</p>");
                    out.println("<p><b>Room Type:</b> " + rs.getString("room_type") + "</p>");
                    out.println("<p><b>Check-in:</b> " + rs.getString("check_in") + "</p>");
                    out.println("<p><b>Check-out:</b> " + rs.getString("check_out") + "</p>");
                    out.println("<p><b>Created At:</b> " + rs.getString("created_at") + "</p>");
                } else {
                    out.println("<h2 style='color:red;'>Reservation Not Found ❌</h2>");
                    out.println("<p>No record for Reservation No: <b>" + reservationNo + "</b></p>");
                }

            } catch (Exception e) {
                out.println("<h2 style='color:red;'>Database Error ❌</h2>");
                out.println("<pre>" + e.toString() + "</pre>");
            }

            out.println("<br>");
            out.println("<a href='viewReservation.html'>Search Again</a> | ");
            out.println("<a href='DashboardServlet'>Dashboard</a>");
            out.println("</body></html>");
        }
    }
}