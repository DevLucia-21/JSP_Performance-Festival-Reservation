package com.perfortival.reservation.controller;

import java.io.IOException;

import com.perfortival.member.dto.MemberDTO;
import com.perfortival.performance.dao.PerformanceDAO;
import com.perfortival.performance.dto.PerformanceDTO;
import com.perfortival.reservation.dto.ReservationDTO;
import com.perfortival.reservation.service.ReservationService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/reservation/free/complete")
public class ReservationFreeCompleteController extends HttpServlet {

    private final ReservationService reservationService = new ReservationService();
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

        // 전달된 값 파싱
        String performanceId = request.getParameter("performanceId");
        String date = request.getParameter("date");
        String time = request.getParameter("time");
        String quantityStr = request.getParameter("quantity");
        String daysStr = request.getParameter("days");
        String totalPriceStr = request.getParameter("totalPrice");
        String cardNumber = request.getParameter("cardNumber");

        if (performanceId == null || quantityStr == null || daysStr == null || totalPriceStr == null ||
            performanceId.isEmpty() || quantityStr.isEmpty() || daysStr.isEmpty() || totalPriceStr.isEmpty()) {
            request.setAttribute("error", "필수 정보가 누락되었습니다.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            return;
        }

        int quantity = Integer.parseInt(quantityStr);
        int days = Integer.parseInt(daysStr);
        int totalPrice = Integer.parseInt(totalPriceStr);

        ReservationDTO dto = new ReservationDTO();
        dto.setMemberId(loginUser.getId());
        dto.setPerformanceId(performanceId);
        dto.setReservationDate(date);
        dto.setReservationTime(time);
        dto.setSeatId(null); // 자유석
        dto.setQuantity(quantity);
        dto.setDays(days);
        dto.setPaymentStatus("결제완료");

        boolean result = reservationService.reserve(dto);
        if (!result) {
            request.setAttribute("error", "자유석 예매에 실패했습니다.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            return;
        }

        // 마스킹된 카드번호
        String maskedCardNumber = maskCardNumber(cardNumber);

        PerformanceDTO performance = performanceDAO.getPerformanceById(performanceId);

        request.setAttribute("totalPrice", totalPrice);
        request.setAttribute("maskedCardNumber", maskedCardNumber);
        request.setAttribute("seatLabels", null); // 자유석
        request.setAttribute("performance", performance);

        request.getRequestDispatcher("/WEB-INF/views/reservation/complete.jsp").forward(request, response);
    }

    private String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) return "****-****-****-****";
        return "****-****-****-" + cardNumber.substring(cardNumber.length() - 4);
    }
}
