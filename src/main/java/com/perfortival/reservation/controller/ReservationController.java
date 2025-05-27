package com.perfortival.reservation.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.perfortival.common.db.DBUtil;
import com.perfortival.performance.service.PerformanceService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
        String action = request.getParameter("action");

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();

            // 예매 방식 갱신
            String updateTypeSql = "UPDATE performances SET reservation_type = ? WHERE id = ?";
            PreparedStatement updateTypeStmt = conn.prepareStatement(updateTypeSql);
            updateTypeStmt.setString(1, reservationType);
            updateTypeStmt.setString(2, performanceId);
            updateTypeStmt.executeUpdate();
            updateTypeStmt.close();

            if ("delete".equals(action)) {
                String deleteSql = "DELETE FROM seats WHERE performance_id = ?";
                pstmt = conn.prepareStatement(deleteSql);
                pstmt.setString(1, performanceId);
                pstmt.executeUpdate();

            } else {
                Map<String, Integer> priceMap = new HashMap<>();

                switch (reservationType) {
                    case "좌석A":
                        double vipPriceA = Double.parseDouble(request.getParameter("vipPriceA"));
                        double generalPriceA = Double.parseDouble(request.getParameter("generalPriceA"));
                        priceMap.put("VIP", (int) vipPriceA);
                        priceMap.put("일반석", (int) generalPriceA);
                        break;

                    case "좌석B":
                        double vipPriceB = Double.parseDouble(request.getParameter("vipPriceB"));
                        double rPrice = Double.parseDouble(request.getParameter("rPrice"));
                        double sPrice = Double.parseDouble(request.getParameter("sPrice"));
                        priceMap.put("VIP", (int) vipPriceB);
                        priceMap.put("R석", (int) rPrice);
                        priceMap.put("S석", (int) sPrice);
                        break;

                    case "혼합":
                        double standingPrice = Double.parseDouble(request.getParameter("standingPrice"));
                        double seatPrice = Double.parseDouble(request.getParameter("seatPrice"));

                        priceMap.put("스탠딩", (int) standingPrice);
                        priceMap.put("좌석", (int) seatPrice); 
                        break;

                    case "자유석":
                        int quantity = Integer.parseInt(request.getParameter("freeSeatQuantity"));
                        double price = Double.parseDouble(request.getParameter("freeSeatPrice"));

                        String freeSql = "INSERT INTO seats (performance_id, seat_type, quantity, price, status, color) VALUES (?, '자유석', ?, ?, '예약 가능', '#FFD700') ON DUPLICATE KEY UPDATE quantity = VALUES(quantity), price = VALUES(price)";
                        pstmt = conn.prepareStatement(freeSql);
                        pstmt.setString(1, performanceId);
                        pstmt.setInt(2, quantity);
                        pstmt.setDouble(3, price);
                        pstmt.executeUpdate();
                        break;
                }

                // 좌석 생성은 자유석을 제외하고만 실행
                if (!"자유석".equals(reservationType)) {
                    new PerformanceService().generateSeats(performanceId, reservationType, priceMap);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(null, pstmt, conn);
        }

        response.sendRedirect(request.getContextPath() + "/reservation");
    }
}
