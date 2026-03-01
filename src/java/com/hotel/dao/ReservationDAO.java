package com.hotel.dao;

import com.hotel.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReservationDAO {

    // Insert reservation
    public boolean insertReservation(String reservationNo, String guestName, String address,
                                     String contact, String roomType, String checkIn, String checkOut) throws Exception {

        String sql = "INSERT INTO reservations (reservation_no, guest_name, address, contact, room_type, check_in, check_out) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, reservationNo);
            ps.setString(2, guestName);
            ps.setString(3, address);
            ps.setString(4, contact);
            ps.setString(5, roomType);
            ps.setString(6, checkIn);
            ps.setString(7, checkOut);

            return ps.executeUpdate() > 0;
        }
    }

    // Find reservation by reservation no
    public ResultSet findByReservationNo(String reservationNo) throws Exception {
        String sql = "SELECT * FROM reservations WHERE reservation_no = ?";

        Connection con = DBConnection.getConnection(); // keep open while ResultSet used
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, reservationNo);
        

        return ps.executeQuery();
        
        
    }
}
