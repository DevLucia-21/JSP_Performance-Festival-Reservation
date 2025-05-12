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
    public void savePerformance(PerformanceDTO performance) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL)) {

            pstmt.setString(1, performance.getId()); 
            pstmt.setString(2, performance.getTitle());
            pstmt.setString(3, performance.getStartDate());
            pstmt.setString(4, performance.getEndDate());
            pstmt.setString(5, performance.getLocation());
            pstmt.setString(6, performance.getGenre());
            pstmt.setString(7, performance.getPosterUrl());
            pstmt.setBoolean(8, performance.isAdminSelected());

            // ON DUPLICATE KEY UPDATE
            pstmt.setString(9, performance.getTitle());
            pstmt.setString(10, performance.getStartDate());
            pstmt.setString(11, performance.getEndDate());
            pstmt.setString(12, performance.getLocation());
            pstmt.setString(13, performance.getGenre());
            pstmt.setString(14, performance.getPosterUrl());
            pstmt.setBoolean(15, performance.isAdminSelected());

            pstmt.executeUpdate();
            System.out.println("[INFO] 공연 저장 완료: " + performance.getTitle());

        } catch (SQLException e) {
            System.err.println("[ERROR] 공연 저장 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
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
}
