package com.perfortival.reservation.controller;

import java.io.IOException;

import com.perfortival.member.dto.MemberDTO;
import com.perfortival.performance.dao.PerformanceDAO;
import com.perfortival.performance.dto.PerformanceDTO;
import com.perfortival.performance.dto.SeatDTO;
import com.perfortival.performance.service.SeatService;
import com.perfortival.reservation.service.ReservationService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/reservation/step2")
public class ReservationStep2Controller extends HttpServlet {

    private ReservationService reservationService = new ReservationService();
    private PerformanceDAO performanceDAO = new PerformanceDAO();
    private SeatService seatService = new SeatService(); // seatService 빠져 있었음

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

        String memberId = loginUser.getId();
        String performanceId = request.getParameter("performanceId");
        String date = request.getParameter("date");
        String time = request.getParameter("time");
        String seatIdStr = request.getParameter("seatId");

        if (seatIdStr == null || seatIdStr.isEmpty()) {
            request.setAttribute("error", "좌석 정보가 없습니다.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            return;
        }

        int seatId = Integer.parseInt(seatIdStr);

        boolean isDuplicate = reservationService.isDuplicateReservation(memberId, performanceId, date, time, seatId);

        if (isDuplicate) {
            request.setAttribute("error", "이미 예매한 좌석입니다.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            return;
        }

        // seatId로 SeatDTO 조회
        SeatDTO seat = seatService.getSeatById(seatId);
        String seatLabel = seat.getZone() + "-" + seat.getRow() + seat.getCol();
        int price = seat.getPrice();

        PerformanceDTO performance = performanceDAO.getPerformanceById(performanceId);

        // 결제 페이지로 전달
        request.setAttribute("performance", performance);
        request.setAttribute("date", date);
        request.setAttribute("time", time);
        request.setAttribute("seatId", seatId);
        request.setAttribute("seatLabel", seatLabel);
        request.setAttribute("price", price);

        request.getRequestDispatcher("/WEB-INF/views/reservation/payment.jsp").forward(request, response);
    }
}
