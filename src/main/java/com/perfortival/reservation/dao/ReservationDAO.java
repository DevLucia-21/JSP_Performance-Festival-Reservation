package com.perfortival.reservation.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.perfortival.common.db.DBUtil;
import com.perfortival.reservation.dto.ReservationDTO;

public class ReservationDAO {

	public boolean insertReservation(ReservationDTO dto) {
	    System.out.println("[DEBUG] insertReservation() 호출됨");
	    System.out.println(" - memberId: " + dto.getMemberId());
	    System.out.println(" - performanceId: " + dto.getPerformanceId());
	    System.out.println(" - date/time: " + dto.getReservationDate() + " " + dto.getReservationTime());
	    System.out.println(" - seatId: " + dto.getSeatId());
	    System.out.println(" - quantity: " + dto.getQuantity());
	    System.out.println(" - days: " + dto.getDays());
	    System.out.println(" - paymentStatus: " + dto.getPaymentStatus());

	    String sql = "INSERT INTO reservations " +
	        "(performance_id, member_id, reservation_date, reservation_time, seat_id, quantity, days, payment_status) " +
	        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setString(1, dto.getPerformanceId());
	        pstmt.setString(2, dto.getMemberId());
	        pstmt.setString(3, dto.getReservationDate());
	        pstmt.setString(4, dto.getReservationTime());

	        if (dto.getSeatId() != null) {
	            pstmt.setInt(5, dto.getSeatId());
	        } else {
	            pstmt.setNull(5, java.sql.Types.INTEGER);
	        }

	        pstmt.setInt(6, dto.getQuantity() != null ? dto.getQuantity() : 1);
	        pstmt.setInt(7, dto.getDays() != null ? dto.getDays() : 1);
	        pstmt.setString(8, dto.getPaymentStatus());

	        int result = pstmt.executeUpdate();
	        System.out.println("[DEBUG] insert 결과: " + result);

	        return result == 1;

	    } catch (Exception e) {
	        System.out.println("[ERROR] 예매 insert 실패:");
	        e.printStackTrace();
	    }

	    return false;
	}

    // 예매 중복 확인 메서드 추가
    public boolean isDuplicateReservation(String memberId, String performanceId, String date, String time, int seatId) {
        boolean isDuplicate = false;
        String sql = "SELECT COUNT(*) FROM reservations " +
                     "WHERE member_id = ? AND performance_id = ? AND reservation_date = ? AND reservation_time = ? AND seat_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, memberId);
            pstmt.setString(2, performanceId);
            pstmt.setString(3, date);
            pstmt.setString(4, time);
            pstmt.setInt(5, seatId);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                isDuplicate = rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDuplicate;
    }

    public Set<Integer> getReservedSeatIds(String performanceId, String date, String time) {
        Set<Integer> reservedSeatIds = new HashSet<>();

        String sql = "SELECT seat_id FROM reservations " +
                "WHERE performance_id = ? AND reservation_date = ? AND reservation_time = ? " +
                "AND seat_id IS NOT NULL AND payment_status = '결제완료'";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, performanceId);
            pstmt.setString(2, date);
            pstmt.setString(3, time);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                reservedSeatIds.add(rs.getInt("seat_id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reservedSeatIds;
    }

    // 자유석 예매된 총 수량 구하는 메서드
    public int getReservedFreeQuantity(String performanceId, String date, String time) {
        String sql = "SELECT SUM(quantity) FROM reservations " +
                     "WHERE performance_id = ? AND reservation_date = ? AND reservation_time = ? AND seat_id IS NULL";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, performanceId);
            pstmt.setString(2, date);
            pstmt.setString(3, time);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);  // 합계 반환 (null이면 0으로 처리됨)
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
    
    // 회원 ID로 예매 내역 조회
    public List<ReservationDTO> getReservationsByMemberId(String memberId) {
        List<ReservationDTO> list = new ArrayList<>();

        String sql = "SELECT r.*, p.title AS performance_title, " +
                "s.seat_id, s.zone, s.seat_type, " +
                "s.row_label AS `row`, s.col_label AS col " +
                "FROM reservations r " +
                "JOIN performances p ON r.performance_id = p.id " +
                "LEFT JOIN seats s ON r.seat_id = s.seat_id " +
                "WHERE r.member_id = ? AND r.payment_status = '결제완료' " +
                "ORDER BY r.reservation_date DESC, r.reservation_time DESC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, memberId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ReservationDTO dto = new ReservationDTO();
                dto.setId(rs.getInt("id"));
                dto.setPerformanceId(rs.getString("performance_id"));
                dto.setMemberId(rs.getString("member_id"));
                dto.setReservationDate(rs.getString("reservation_date"));
                dto.setReservationTime(rs.getString("reservation_time"));
                dto.setSeatId(rs.getObject("seat_id") != null ? rs.getInt("seat_id") : null);
                dto.setQuantity(rs.getInt("quantity"));
                dto.setDays(rs.getInt("days"));
                dto.setPaymentStatus(rs.getString("payment_status"));
                dto.setPerformanceTitle(rs.getString("performance_title"));

                // 좌석 정보가 있으면 SeatDTO로 구성
                if (dto.getSeatId() != null) {
                    com.perfortival.performance.dto.SeatDTO seat = new com.perfortival.performance.dto.SeatDTO();
                    seat.setSeatId(rs.getInt("seat_id"));
                    seat.setZone(rs.getString("zone"));
                    seat.setSeatType(rs.getString("seat_type"));
                    seat.setRow(rs.getString("row"));
                    seat.setCol(rs.getString("col"));

                    dto.setSeat(seat);  // ✅ ReservationDTO에 set
                }

                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public boolean markReservationAsCancelled(int reservationId) {
        String sql = "UPDATE reservations SET payment_status = '결제취소' WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, reservationId);
            int result = pstmt.executeUpdate();
            return result == 1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<ReservationDTO> getAllReservations() {
        List<ReservationDTO> list = new ArrayList<>();

        String sql = "SELECT r.*, p.title AS performance_title, " +
                "s.seat_id, s.zone, s.seat_type, s.row_label AS `row`, s.col_label AS col " +
                "FROM reservations r " +
                "JOIN performances p ON r.performance_id = p.id " +
                "LEFT JOIN seats s ON r.seat_id = s.seat_id " +
                "ORDER BY r.id ASC";  // 리스트 순서 유지

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                ReservationDTO dto = new ReservationDTO();
                dto.setId(rs.getInt("id"));
                dto.setPerformanceId(rs.getString("performance_id"));
                dto.setMemberId(rs.getString("member_id"));
                dto.setReservationDate(rs.getString("reservation_date"));
                dto.setReservationTime(rs.getString("reservation_time"));
                dto.setSeatId(rs.getObject("seat_id") != null ? rs.getInt("seat_id") : null);
                dto.setQuantity(rs.getInt("quantity"));
                dto.setDays(rs.getInt("days"));
                dto.setPaymentStatus(rs.getString("payment_status"));
                dto.setPerformanceTitle(rs.getString("performance_title"));

                if (dto.getSeatId() != null) {
                    com.perfortival.performance.dto.SeatDTO seat = new com.perfortival.performance.dto.SeatDTO();
                    seat.setSeatId(rs.getInt("seat_id"));
                    seat.setZone(rs.getString("zone"));
                    seat.setSeatType(rs.getString("seat_type"));
                    seat.setRow(rs.getString("row"));
                    seat.setCol(rs.getString("col"));
                    dto.setSeat(seat);
                }

                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public Integer getSeatIdByReservationId(int reservationId) {
        String sql = "SELECT seat_id FROM reservations WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, reservationId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int seatId = rs.getInt("seat_id");
                return rs.wasNull() ? null : seatId;  
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}