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

@WebServlet(name = "BillServlet", urlPatterns = {"/BillServlet"})
public class BillServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String reservationNo = request.getParameter("reservation_no");

        String sqlReservation = "SELECT reservation_no, guest_name, room_type, check_in, check_out "
                + "FROM reservations WHERE reservation_no = ?";
        String sqlRate = "SELECT rate_per_night FROM room_rates WHERE room_type = ?";

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html><html><head><title>Bill</title></head>");
            out.println("<body style='font-family:Arial; margin:40px;'>");

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement psRes = con.prepareStatement(sqlReservation)) {

                psRes.setString(1, reservationNo);

                try (ResultSet rs = psRes.executeQuery()) {

                    if (!rs.next()) {
                        out.println("<h2 style='color:red;'>Reservation Not Found ❌</h2>");
                        out.println("<a href='bill.html'>Try Again</a>");
                        out.println("</body></html>");
                        return;
                    }

                    String guestName = rs.getString("guest_name");
                    String roomType = rs.getString("room_type");
                    LocalDate checkIn = LocalDate.parse(rs.getString("check_in"));
                    LocalDate checkOut = LocalDate.parse(rs.getString("check_out"));

                    long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
                    if (nights <= 0) nights = 1; // safety

                    double ratePerNight = 0;

                    try (PreparedStatement psRate = con.prepareStatement(sqlRate)) {
                        psRate.setString(1, roomType);
                        try (ResultSet rr = psRate.executeQuery()) {
                            if (rr.next()) {
                                ratePerNight = rr.getDouble("rate_per_night");
                            }
                        }
                    }

                    double total = nights * ratePerNight;

                    // ✅ Bill output
                    out.println("<div id='billArea'>");
                    out.println("<h2>Hotel Bill</h2>");
                    out.println("<hr>");

                    out.println("<p><b>Reservation No:</b> " + reservationNo + "</p>");
                    out.println("<p><b>Guest Name:</b> " + guestName + "</p>");
                    out.println("<p><b>Room Type:</b> " + roomType + "</p>");
                    out.println("<p><b>Check-in:</b> " + checkIn + "</p>");
                    out.println("<p><b>Check-out:</b> " + checkOut + "</p>");
                    out.println("<p><b>Nights:</b> " + nights + "</p>");
                    out.println("<p><b>Rate per Night:</b> " + ratePerNight + "</p>");

                    out.println("<h3>Total: " + total + "</h3>");
                    out.println("</div>");

                    // ✅ Print button
                    out.println("<button onclick='window.print()'>Print Bill</button>");
                    out.println("<br><br>");
                    out.println("<a href='bill.html'>Generate Another</a> | ");
                    out.println("<a href='DashboardServlet'>Dashboard</a>");
                }

            } catch (Exception e) {
                out.println("<h2 style='color:red;'>Database Error ❌</h2>");
                out.println("<pre>" + e.toString() + "</pre>");
            }

            out.println("</body></html>");
        }
    }
}