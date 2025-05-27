package com.perfortival.reservation.controller;

import java.io.IOException;

import com.perfortival.member.dto.MemberDTO;
import com.perfortival.reservation.dto.ReservationDTO;
import com.perfortival.reservation.service.ReservationService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/reservation/complete")
public class ReservationCompleteController extends HttpServlet {

    private ReservationService reservationService = new ReservationService();

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

        String performanceId = request.getParameter("performanceId");
        String date = request.getParameter("date");
        String time = request.getParameter("time");
        int seatId = Integer.parseInt(request.getParameter("seatId"));

        ReservationDTO dto = new ReservationDTO();
        dto.setMemberId(loginUser.getId());
        dto.setPerformanceId(performanceId);
        dto.setReservationDate(date);
        dto.setReservationTime(time);
        dto.setSeatId(seatId); // 자유석이면 null로 대체

        boolean result = reservationService.reserve(dto);

        if (result) {
            request.getRequestDispatcher("/WEB-INF/views/reservation/complete.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "예매에 실패했습니다.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
