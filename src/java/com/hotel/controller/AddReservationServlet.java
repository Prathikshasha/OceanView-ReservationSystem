package com.hotel.controller;

import com.hotel.dao.ReservationDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AddReservationServlet", urlPatterns = {"/AddReservationServlet"})
public class AddReservationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String reservationNo = request.getParameter("reservation_no");
        String guestName = request.getParameter("guest_name");
        String address = request.getParameter("address");
        String contact = request.getParameter("contact");
        String roomType = request.getParameter("room_type");
        String checkIn = request.getParameter("check_in");
        String checkOut = request.getParameter("check_out");

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html><html><head><title>Saved</title></head>");
            out.println("<body style='font-family: Arial; margin:40px;'>");

            try {
                // ✅ DAO evidence: DB insert is handled by ReservationDAO
                ReservationDAO dao = new ReservationDAO();
                boolean saved = dao.insertReservation(
                        reservationNo, guestName, address, contact, roomType, checkIn, checkOut
                );

                if (saved) {
                    out.println("<h2 style='color:green;'>Reservation Saved to Database ✅</h2>");
                    out.println("<p><b>Reservation No:</b> " + reservationNo + "</p>");
                } else {
                    out.println("<h2 style='color:red;'>Save Failed ❌</h2>");
                }

            } catch (Exception e) {
                out.println("<h2 style='color:red;'>Database Error ❌</h2>");
                out.println("<pre>" + e.toString() + "</pre>");
            }

            out.println("<a href='addReservation.html'>Add Another</a> | ");
            out.println("<a href='DashboardServlet'>Dashboard</a>");
            out.println("</body></html>");
        }
    }
}