package com.perfortival.performance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.perfortival.common.db.DBUtil;
import com.perfortival.performance.dto.PerformanceDTO;
import com.perfortival.performance.dto.SeatDTO;

public class PerformanceDAO {

    // 공연 저장
    public int savePerformance(PerformanceDTO performance) {
        String sql = "INSERT INTO performances (id, title, start_date, end_date, location, genre, poster_url, admin_selected, reservation_type) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE " +
                     "title = VALUES(title), " +
                     "start_date = VALUES(start_date), " +
                     "end_date = VALUES(endDate), " +
                     "location = VALUES(location), " +
                     "genre = VALUES(genre), " +
                     "poster_url = VALUES(poster_url), " +
                     "admin_selected = VALUES(admin_selected), " +
                     "reservation_type = VALUES(reservation_type)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, performance.getId());
            pstmt.setString(2, performance.getTitle());
            pstmt.setString(3, performance.getStartDate());
            pstmt.setString(4, performance.getEndDate());
            pstmt.setString(5, performance.getLocation());
            pstmt.setString(6, performance.getGenre());
            pstmt.setString(7, performance.getPosterUrl());
            pstmt.setBoolean(8, performance.isAdminSelected());
            pstmt.setString(9, performance.getReservationType());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    // 공연 상세 조회
    public PerformanceDTO getPerformanceById(String id) {
        PerformanceDTO performance = null;

        String query = "SELECT id, title, start_date, end_date, location, genre, poster_url, admin_selected, reservation_type FROM performances WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    performance = new PerformanceDTO();
                    performance.setId(rs.getString("id"));
                    performance.setTitle(rs.getString("title"));
                    performance.setStartDate(rs.getString("start_date"));
                    performance.setEndDate(rs.getString("end_date"));
                    performance.setLocation(rs.getString("location"));
                    performance.setGenre(rs.getString("genre"));
                    performance.setPosterUrl(rs.getString("poster_url"));
                    performance.setAdminSelected(rs.getBoolean("admin_selected"));
                    performance.setReservationType(rs.getString("reservation_type"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return performance;
    }

    // 공연 목록 조회 (검색/필터용)
    public List<PerformanceDTO> getPerformances(String startDate, String endDate, String searchKeyword) {
        List<PerformanceDTO> performances = new ArrayList<>();

        String query = "SELECT id, title, start_date, end_date, location, genre, poster_url, reservation_type FROM performances WHERE admin_selected = TRUE ";

        if (startDate != null && !startDate.isEmpty()) {
            query += "AND start_date >= ? ";
        }

        if (endDate != null && !endDate.isEmpty()) {
            query += "AND end_date <= ? ";
        }

        if (searchKeyword != null && !searchKeyword.isEmpty()) {
            query += "AND title LIKE ? ";
        }

        query += "ORDER BY start_date ASC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            int index = 1;

            if (startDate != null && !startDate.isEmpty()) {
                pstmt.setString(index++, startDate);
            }
            if (endDate != null && !endDate.isEmpty()) {
                pstmt.setString(index++, endDate);
            }
            if (searchKeyword != null && !searchKeyword.isEmpty()) {
                pstmt.setString(index++, "%" + searchKeyword + "%");
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                PerformanceDTO p = new PerformanceDTO();
                p.setId(rs.getString("id"));
                p.setTitle(rs.getString("title"));
                p.setStartDate(rs.getString("start_date"));
                p.setEndDate(rs.getString("end_date"));
                p.setLocation(rs.getString("location"));
                p.setGenre(rs.getString("genre"));
                p.setPosterUrl(rs.getString("poster_url"));
                p.setReservationType(rs.getString("reservation_type")); // 🔧 추가
                performances.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return performances;
    }

    // 메인용 공연 목록
    public List<PerformanceDTO> getMainPagePerformances() {
        List<PerformanceDTO> performances = new ArrayList<>();

        String query = "SELECT id, title, start_date, end_date, location, genre, poster_url, reservation_type " +
                       "FROM performances " +
                       "WHERE admin_selected = TRUE AND end_date >= CURDATE() " +
                       "ORDER BY start_date ASC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                PerformanceDTO p = new PerformanceDTO();
                p.setId(rs.getString("id"));
                p.setTitle(rs.getString("title"));
                p.setStartDate(rs.getString("start_date"));
                p.setEndDate(rs.getString("end_date"));
                p.setLocation(rs.getString("location"));
                p.setGenre(rs.getString("genre"));
                p.setPosterUrl(rs.getString("poster_url"));
                p.setReservationType(rs.getString("reservation_type")); 
                performances.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return performances;
    }

    // 전체 공연 조회
    public List<PerformanceDTO> getAllPerformances() {
        List<PerformanceDTO> list = new ArrayList<>();
        String sql = "SELECT id, title, start_date, end_date, location, genre, poster_url, reservation_type FROM performances WHERE admin_selected = 1";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                PerformanceDTO p = new PerformanceDTO();
                p.setId(rs.getString("id"));
                p.setTitle(rs.getString("title"));
                p.setStartDate(rs.getString("start_date"));
                p.setEndDate(rs.getString("end_date"));
                p.setLocation(rs.getString("location"));
                p.setGenre(rs.getString("genre"));
                p.setPosterUrl(rs.getString("poster_url"));
                p.setReservationType(rs.getString("reservation_type")); // 🔧 추가
                list.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // 선택된 공연 ID 목록 조회
    public List<PerformanceDTO> findByIds(String[] ids) {
        List<PerformanceDTO> list = new ArrayList<>();
        if (ids == null || ids.length == 0) return list;

        StringBuilder sql = new StringBuilder("SELECT id, title, start_date, end_date, location, genre, poster_url, reservation_type FROM performances WHERE id IN (");
        for (int i = 0; i < ids.length; i++) {
            sql.append("?");
            if (i < ids.length - 1) sql.append(",");
        }
        sql.append(")");

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < ids.length; i++) {
                pstmt.setString(i + 1, ids[i]);
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                PerformanceDTO p = new PerformanceDTO();
                p.setId(rs.getString("id"));
                p.setTitle(rs.getString("title"));
                p.setStartDate(rs.getString("start_date"));
                p.setEndDate(rs.getString("end_date"));
                p.setLocation(rs.getString("location"));
                p.setGenre(rs.getString("genre"));
                p.setPosterUrl(rs.getString("poster_url"));
                p.setReservationType(rs.getString("reservation_type")); 
                list.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // 예매 방식 업데이트
    public boolean updateReservationType(String performanceId, String reservationType) {
        String sql = "UPDATE performances SET reservation_type = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, reservationType);
            pstmt.setString(2, performanceId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 자유석 데이터 insert or update
    public boolean insertOrUpdateFreeSeat(String performanceId, int quantity, int price) {
        String sql = "INSERT INTO seats (performance_id, seat_type, quantity, price, status) " +
                     "VALUES (?, '자유석', ?, ?, '예약 가능') " +
                     "ON DUPLICATE KEY UPDATE quantity = ?, price = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, performanceId);
            pstmt.setInt(2, quantity);
            pstmt.setInt(3, price);
            pstmt.setInt(4, quantity);
            pstmt.setInt(5, price);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 좌석 정보 조회
    public List<SeatDTO> getSeatsByPerformanceId(String performanceId) {
        List<SeatDTO> seatList = new ArrayList<>();

        String sql = "SELECT seat_id, section, zone, entry_number, price, seat_type, status " +
                     "FROM seats WHERE performance_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, performanceId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                SeatDTO seat = new SeatDTO();
                seat.setSeatId(rs.getInt("seat_id"));
                seat.setZone(rs.getString("zone"));
                seat.setRow("R");
                seat.setCol(String.valueOf(rs.getInt("entry_number")));
                seat.setPrice(rs.getInt("price"));
                seat.setSeatType(rs.getString("seat_type"));
                seat.setReserved("예약 완료".equals(rs.getString("status")));

                String section = rs.getString("section");
                if (section != null && section.startsWith("1층")) seat.setFloor(1);
                else if (section != null && section.startsWith("2층")) seat.setFloor(2);
                else if (section != null && section.startsWith("3층")) seat.setFloor(3);

                seatList.add(seat);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return seatList;
    }
    
    public Integer getFreeSeatPrice(String performanceId) {
        String sql = "SELECT price FROM seats WHERE performance_id = ? AND seat_type = '자유석' LIMIT 1";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, performanceId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("price");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
