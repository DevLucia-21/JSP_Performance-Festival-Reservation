package com.perfortival.reservation.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.perfortival.reservation.dto.ReservationDTO;
import com.perfortival.common.db.DBUtil;

public class ReservationDAO {

    public boolean insertReservation(ReservationDTO dto) {
        String sql = "INSERT INTO reservations " +
                     "(performance_id, member_id, reservation_date, reservation_time, seat_id, quantity, days) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dto.getPerformanceId());
            pstmt.setString(2, dto.getMemberId());
            pstmt.setString(3, dto.getReservationDate());
            pstmt.setString(4, dto.getReservationTime());

            // 좌석형이라면 seatId, 자유석이면 null
            if (dto.getSeatId() != null) {
                pstmt.setInt(5, dto.getSeatId());
            } else {
                pstmt.setNull(5, java.sql.Types.INTEGER);
            }

            // 자유석이라면 수량/일수, 좌석형이면 기본값
            pstmt.setInt(6, dto.getQuantity() != null ? dto.getQuantity() : 1);
            pstmt.setInt(7, dto.getDays() != null ? dto.getDays() : 1);

            int result = pstmt.executeUpdate();
            return result == 1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
