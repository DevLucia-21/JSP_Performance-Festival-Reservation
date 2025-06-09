package com.perfortival.reservation.controller;

import java.io.IOException;

import com.perfortival.member.dto.MemberDTO;
import com.perfortival.performance.dao.PerformanceDAO;
import com.perfortival.performance.dto.PerformanceDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/reservation/payment")
public class ReservationFreePaymentController extends HttpServlet {

    private final PerformanceDAO performanceDAO = new PerformanceDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/member/login.jsp");
            return;
        }

        // 파라미터 받기
        String performanceId = request.getParameter("performanceId");
        String date = request.getParameter("date");
        String time = request.getParameter("time");
        String quantityStr = request.getParameter("quantity");
        String daysStr = request.getParameter("days");
        String seatPriceStr = request.getParameter("seatPrice");

        // 필수값 null/빈값 검사
        if (performanceId == null || quantityStr == null || daysStr == null || seatPriceStr == null ||
            performanceId.isEmpty() || quantityStr.isEmpty() || daysStr.isEmpty() || seatPriceStr.isEmpty()) {
            request.setAttribute("error", "필수 정보가 누락되었습니다.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            return;
        }

        // 파싱
        int quantity = Integer.parseInt(quantityStr);
        int days = Integer.parseInt(daysStr);
        int basePrice = Integer.parseInt(seatPriceStr);

        // 할인 적용
        double discount = 1.0;
        if (days == 2) discount = 0.9;
        else if (days == 3) discount = 0.85;

        int totalPrice = (int)(basePrice * days * discount * quantity);

        // 공연 정보 조회
        PerformanceDTO performance = performanceDAO.getPerformanceById(performanceId);

        // 결과 세팅
        request.setAttribute("performance", performance);
        request.setAttribute("date", date);
        request.setAttribute("time", time);
        request.setAttribute("quantity", quantity);
        request.setAttribute("days", days);
        request.setAttribute("totalPrice", totalPrice);
        request.setAttribute("seatLabel", null); // 자유석은 좌석 정보 없음

        // 결제 페이지 이동
        request.getRequestDispatcher("/WEB-INF/views/reservation/payment.jsp").forward(request, response);
    }
}
