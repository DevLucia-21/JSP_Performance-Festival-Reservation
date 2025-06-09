package com.perfortival.member.controller;

import java.io.IOException;
import java.util.List;

import com.perfortival.member.dto.MemberDTO;
import com.perfortival.reservation.dto.ReservationDTO;
import com.perfortival.reservation.service.ReservationService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/member/reservationHistory")
public class ReservationHistoryController extends HttpServlet {

    private ReservationService reservationService = new ReservationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/member/login?error=needLogin");
            return;
        }

        String memberId = loginUser.getId();
        if (memberId == null || memberId.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/member/login?error=invalidAccess");
            return;
        }

        List<ReservationDTO> reservationList = reservationService.getReservationsByMemberId(memberId);

        request.setAttribute("reservationHistory", reservationList);
        request.getRequestDispatcher("/WEB-INF/views/member/reservationHistory.jsp").forward(request, response);
    }
}
