package com.perfortival.performance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.perfortival.common.db.DBUtil;
import com.perfortival.performance.dto.PerformanceDTO;

public class PerformanceDAO {

    // 1. 공연 정보 저장 (관리자가 선택한 공연만 저장)
    private static final String INSERT_SQL = 
        "INSERT INTO performances (id, title, start_date, end_date, location, genre, poster_url, admin_selected) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
        "ON DUPLICATE KEY UPDATE title = ?, start_date = ?, end_date = ?, location = ?, genre = ?, poster_url = ?, admin_selected = ?";

    // 공연 저장 메서드- 관리자가 선택한 공연만 저장
    public int savePerformance(PerformanceDTO performance) {
        String sql = "INSERT INTO performances (id, title, start_date, end_date, location, genre, poster_url, admin_selected) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE " +
                     "title = VALUES(title), " +
                     "start_date = VALUES(start_date), " +
                     "end_date = VALUES(end_date), " +
                     "location = VALUES(location), " +
                     "genre = VALUES(genre), " +
                     "poster_url = VALUES(poster_url), " +
                     "admin_selected = VALUES(admin_selected)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        int rowsAffected = 0;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, performance.getId());
            pstmt.setString(2, performance.getTitle());
            pstmt.setString(3, performance.getStartDate());
            pstmt.setString(4, performance.getEndDate());
            pstmt.setString(5, performance.getLocation());
            pstmt.setString(6, performance.getGenre());
            pstmt.setString(7, performance.getPosterUrl());
            pstmt.setBoolean(8, performance.isAdminSelected());

            rowsAffected = pstmt.executeUpdate();
            System.out.println("[INFO] DB 저장/업데이트 완료 - 영향받은 행: " + rowsAffected);

        } catch (SQLException e) {
            System.err.println("[ERROR] 공연 저장 중 SQL 오류: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtil.close(null, pstmt, conn);
        }

        return rowsAffected;
    }

    // 2. 공연 목록 조회 (기간 및 검색어 기반, 관리자가 선택한 공연만 조회)
    public List<PerformanceDTO> getPerformances(String startDate, String endDate, String searchKeyword) {
        List<PerformanceDTO> performances = new ArrayList<>();

        String query = "SELECT * FROM performances WHERE admin_selected = TRUE ";

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

            int paramIndex = 1;

            if (startDate != null && !startDate.isEmpty()) {
                pstmt.setString(paramIndex++, startDate);
            }

            if (endDate != null && !endDate.isEmpty()) {
                pstmt.setString(paramIndex++, endDate);
            }

            if (searchKeyword != null && !searchKeyword.isEmpty()) {
                pstmt.setString(paramIndex++, "%" + searchKeyword + "%");
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                PerformanceDTO performance = new PerformanceDTO();
                performance.setId(rs.getString("id"));
                performance.setTitle(rs.getString("title"));
                performance.setStartDate(rs.getString("start_date"));
                performance.setEndDate(rs.getString("end_date"));
                performance.setLocation(rs.getString("location"));
                performance.setGenre(rs.getString("genre"));
                performance.setPosterUrl(rs.getString("poster_url"));
                performances.add(performance);
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] 공연 목록 조회 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }

        return performances;
    }

    // 3. 메인 페이지에 출력할 공연 목록 조회 (현재 날짜 이후의 공연만 조회)
    public List<PerformanceDTO> getMainPagePerformances() {
        List<PerformanceDTO> performances = new ArrayList<>();

        String query = 
            "SELECT id, title, start_date, end_date, location, genre, poster_url " +
            "FROM performances " +
            "WHERE admin_selected = TRUE AND start_date >= CURDATE() " +
            "ORDER BY start_date ASC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                PerformanceDTO performance = new PerformanceDTO();
                performance.setId(rs.getString("id"));
                performance.setTitle(rs.getString("title"));
                performance.setStartDate(rs.getString("start_date"));
                performance.setEndDate(rs.getString("end_date"));
                performance.setLocation(rs.getString("location"));
                performance.setGenre(rs.getString("genre"));
                performance.setPosterUrl(rs.getString("poster_url"));
                performances.add(performance);
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] 메인 페이지 공연 목록 조회 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }

        return performances;
    }

    // DB에서 모든 공연을 조회하는 메서드
    public List<PerformanceDTO> getAllPerformances() {
        List<PerformanceDTO> performanceList = new ArrayList<>();
        String sql = "SELECT id, title, start_date, end_date, location, genre, poster_url FROM performances WHERE admin_selected = 1";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                PerformanceDTO performance = new PerformanceDTO();
                performance.setId(rs.getString("id"));
                performance.setTitle(rs.getString("title"));
                performance.setStartDate(rs.getString("start_date"));
                performance.setEndDate(rs.getString("end_date"));
                performance.setLocation(rs.getString("location"));
                performance.setGenre(rs.getString("genre"));
                performance.setPosterUrl(rs.getString("poster_url"));
                performanceList.add(performance);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return performanceList;
    }

    // 특정 ID로 공연 조회
    public PerformanceDTO getPerformanceById(String id) {
        PerformanceDTO performance = null;

        String query = "SELECT id, title, start_date, end_date, location, genre, poster_url, admin_selected FROM performances WHERE id = ?";

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
                }
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] 공연 조회 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }

        return performance;
    }

    // 선택된 ID들로 공연 목록 조회
    public List<PerformanceDTO> findByIds(String[] ids) {
        List<PerformanceDTO> performanceList = new ArrayList<>();

        if (ids == null || ids.length == 0) return performanceList;

        StringBuilder query = new StringBuilder("SELECT id, title, start_date, end_date, location, genre, poster_url FROM performances WHERE id IN (");
        for (int i = 0; i < ids.length; i++) {
            query.append("?");
            if (i < ids.length - 1) query.append(",");
        }
        query.append(")");

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query.toString())) {

            for (int i = 0; i < ids.length; i++) {
                pstmt.setString(i + 1, ids[i]);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    PerformanceDTO performance = new PerformanceDTO();
                    performance.setId(rs.getString("id"));
                    performance.setTitle(rs.getString("title"));
                    performance.setStartDate(rs.getString("start_date"));
                    performance.setEndDate(rs.getString("end_date"));
                    performance.setLocation(rs.getString("location"));
                    performance.setGenre(rs.getString("genre"));
                    performance.setPosterUrl(rs.getString("poster_url"));
                    performanceList.add(performance);
                }
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] 공연 ID 목록 조회 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }

        return performanceList;
    }
}
