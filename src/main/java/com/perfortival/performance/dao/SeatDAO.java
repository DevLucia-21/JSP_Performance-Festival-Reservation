package com.perfortival.performance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.perfortival.common.db.DBUtil;
import com.perfortival.performance.dto.SeatDTO;

public class SeatDAO {

	public void insertOrUpdate(SeatDTO seat) {
		System.out.println("[DEBUG] zone=" + seat.getZone() + ", row=" + seat.getRow() + ", col=" + seat.getCol());

		String sql = "INSERT INTO seats (performance_id, seat_type, section, zone, entry_number, " +
	             "quantity, price, status, color, row_label, col_label) " +
	             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
	             "ON DUPLICATE KEY UPDATE " +
	             "seat_type = VALUES(seat_type), " +
	             "section = VALUES(section), " +
	             "entry_number = VALUES(entry_number), " +
	             "quantity = VALUES(quantity), " +
	             "price = VALUES(price), " +
	             "status = VALUES(status), " +
	             "color = VALUES(color), " +
	             "row_label = VALUES(row_label), " +
	             "col_label = VALUES(col_label)";
		
	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setString(1, seat.getPerformanceId());
	        pstmt.setString(2, seat.getSeatType());
	        pstmt.setString(3, seat.getSection());
	        pstmt.setString(4, seat.getZone());
	        pstmt.setInt(5, seat.getEntryNumber());
	        pstmt.setInt(6, seat.getQuantity());
	        pstmt.setInt(7, seat.getPrice());
	        pstmt.setString(8, "예약 가능");
	        pstmt.setString(9, seat.getColor());
	        pstmt.setString(10, seat.getRow());
	        pstmt.setString(11, seat.getCol());

	        pstmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public List<SeatDTO> getSeatsByPerformance(String performanceId) {
        List<SeatDTO> seatList = new ArrayList<>();

        String sql = "SELECT * FROM seats WHERE performance_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, performanceId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                SeatDTO seat = new SeatDTO();
                seat.setSeatId(rs.getInt("seat_id"));
                seat.setPerformanceId(rs.getString("performance_id"));
                seat.setSeatType(rs.getString("seat_type"));
                seat.setSection(rs.getString("section"));
                seat.setZone(rs.getString("zone"));
                seat.setEntryNumber(rs.getInt("entry_number"));
                seat.setQuantity(rs.getInt("quantity"));
                seat.setPrice(rs.getInt("price"));
                seat.setStatus(rs.getString("status"));
                seat.setColor(rs.getString("color"));
                seat.setRow(rs.getString("row_label"));
                seat.setCol(rs.getString("col_label"));
                seat.setReserved("예약 불가".equals(rs.getString("status")));
                seat.setFloor("1층".equals(seat.getSection()) ? 1 : ("2층".equals(seat.getSection()) ? 2 : 0));

                seatList.add(seat);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return seatList;
    }
	
	public void deleteByPerformanceId(String performanceId) {
	    String sql = "DELETE FROM seats WHERE performance_id = ?";

	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setString(1, performanceId);
	        pstmt.executeUpdate();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
