package com.perfortival.reservation.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.perfortival.common.db.DBUtil;

@WebServlet("/reservation")
public class ReservationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    ArrayList<Map<String, Object>> templates = new ArrayList<>();
    ArrayList<Map<String, Object>> performances = new ArrayList<>();

    try {
        conn = DBUtil.getConnection();

        // 공연 목록 조회
        String performanceSql = "SELECT id, title FROM performances";
        pstmt = conn.prepareStatement(performanceSql);
        rs = pstmt.executeQuery();

        while (rs.next()) {
            Map<String, Object> performance = new HashMap<>();
            performance.put("id", rs.getString("id"));
            performance.put("title", rs.getString("title"));
            performances.add(performance);
        }
        rs.close();
        pstmt.close();

        // 좌석 템플릿 조회
        String templateSql = "SELECT template_id, template_name FROM seat_templates";
        pstmt = conn.prepareStatement(templateSql);
        rs = pstmt.executeQuery();

        while (rs.next()) {
            Map<String, Object> template = new HashMap<>();
            template.put("template_id", rs.getInt("template_id"));
            template.put("template_name", rs.getString("template_name"));
            templates.add(template);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        DBUtil.close(rs, pstmt, conn);
    }

    request.setAttribute("performances", performances);
    request.setAttribute("templates", templates);
    request.getRequestDispatcher("/WEB-INF/views/reservation/reservation.jsp").forward(request, response);
}


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String performanceId = request.getParameter("performanceId");
        String reservationType = request.getParameter("reservationType");
        String action = request.getParameter("action");  // 추가된 action 파라미터 (insert, update, delete)

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();

            if ("delete".equals(action)) {
                // 좌석 삭제 쿼리
                String deleteSql = "DELETE FROM seats WHERE performance_id = ?";
                pstmt = conn.prepareStatement(deleteSql);
                pstmt.setString(1, performanceId);
                pstmt.executeUpdate();
            } else {
                switch (reservationType) {
                    case "좌석A":
                        double vipPriceA = Double.parseDouble(request.getParameter("vipPriceA"));
                        double generalPriceA = Double.parseDouble(request.getParameter("generalPriceA"));

                        String vipSqlA = "INSERT INTO seats (performance_id, seat_type, price, status, color) VALUES (?, 'VIP', ?, '예약 가능', '#FFD700') ON DUPLICATE KEY UPDATE price = VALUES(price)";
                        pstmt = conn.prepareStatement(vipSqlA);
                        pstmt.setString(1, performanceId);
                        pstmt.setDouble(2, vipPriceA);
                        pstmt.executeUpdate();

                        String generalSqlA = "INSERT INTO seats (performance_id, seat_type, price, status, color) VALUES (?, '일반석', ?, '예약 가능', '#4682B4') ON DUPLICATE KEY UPDATE price = VALUES(price)";
                        pstmt = conn.prepareStatement(generalSqlA);
                        pstmt.setString(1, performanceId);
                        pstmt.setDouble(2, generalPriceA);
                        pstmt.executeUpdate();
                        break;

                    case "좌석B":
                        double vipPriceB = Double.parseDouble(request.getParameter("vipPriceB"));
                        double rPrice = Double.parseDouble(request.getParameter("rPrice"));
                        double sPrice = Double.parseDouble(request.getParameter("sPrice"));

                        String vipSqlB = "INSERT INTO seats (performance_id, seat_type, price, status, color) VALUES (?, 'VIP', ?, '예약 가능', '#FFD700') ON DUPLICATE KEY UPDATE price = VALUES(price)";
                        pstmt = conn.prepareStatement(vipSqlB);
                        pstmt.setString(1, performanceId);
                        pstmt.setDouble(2, vipPriceB);
                        pstmt.executeUpdate();

                        String rSql = "INSERT INTO seats (performance_id, seat_type, price, status, color) VALUES (?, 'R석', ?, '예약 가능', '#FF8C00') ON DUPLICATE KEY UPDATE price = VALUES(price)";
                        pstmt = conn.prepareStatement(rSql);
                        pstmt.setString(1, performanceId);
                        pstmt.setDouble(2, rPrice);
                        pstmt.executeUpdate();

                        String sSql = "INSERT INTO seats (performance_id, seat_type, price, status, color) VALUES (?, 'S석', ?, '예약 가능', '#87CEFA') ON DUPLICATE KEY UPDATE price = VALUES(price)";
                        pstmt = conn.prepareStatement(sSql);
                        pstmt.setString(1, performanceId);
                        pstmt.setDouble(2, sPrice);
                        pstmt.executeUpdate();
                        break;

                    case "혼합":
                        double standingPrice = Double.parseDouble(request.getParameter("standingPrice"));
                        double seatPrice = Double.parseDouble(request.getParameter("seatPrice"));

                        String standingSql = "INSERT INTO seats (performance_id, seat_type, price, status, color) VALUES (?, '스탠딩', ?, '예약 가능', '#FF6347') ON DUPLICATE KEY UPDATE price = VALUES(price)";
                        pstmt = conn.prepareStatement(standingSql);
                        pstmt.setString(1, performanceId);
                        pstmt.setDouble(2, standingPrice);
                        pstmt.executeUpdate();

                        String seatSql = "INSERT INTO seats (performance_id, seat_type, price, status, color) VALUES (?, '좌석', ?, '예약 가능', '#4682B4') ON DUPLICATE KEY UPDATE price = VALUES(price)";
                        pstmt = conn.prepareStatement(seatSql);
                        pstmt.setString(1, performanceId);
                        pstmt.setDouble(2, seatPrice);
                        pstmt.executeUpdate();
                        break;

                    case "자유석":
                        int freeSeatQuantity = Integer.parseInt(request.getParameter("freeSeatQuantity"));
                        double freeSeatPrice = Double.parseDouble(request.getParameter("freeSeatPrice"));

                        String freeSeatSql = "INSERT INTO seats (performance_id, seat_type, quantity, price, status, color) VALUES (?, '자유석', ?, ?, '예약 가능', '#FFD700') ON DUPLICATE KEY UPDATE quantity = VALUES(quantity), price = VALUES(price)";
                        pstmt = conn.prepareStatement(freeSeatSql);
                        pstmt.setString(1, performanceId);
                        pstmt.setInt(2, freeSeatQuantity);
                        pstmt.setDouble(3, freeSeatPrice);
                        pstmt.executeUpdate();
                        break;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(null, pstmt, conn);
        }

        // **리다이렉트 처리**
        response.sendRedirect(request.getContextPath() + "/reservation");
    }
}