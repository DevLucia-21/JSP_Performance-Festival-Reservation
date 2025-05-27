package com.perfortival.member.controller;

import java.io.IOException;

import com.perfortival.reservation.service.ReservationService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/member/reservationHistory/cancel")
public class ReservationCancelController extends HttpServlet {

    private ReservationService reservationService = new ReservationService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String reservationIdParam = request.getParameter("reservationId");

        if (reservationIdParam != null) {
            try {
                int reservationId = Integer.parseInt(reservationIdParam);
                reservationService.cancelReservation(reservationId);
            } catch (NumberFormatException e) {
                e.printStackTrace(); // 로그 기록
            }
        }

        response.sendRedirect(request.getContextPath() + "/member/reservationHistory");
    }
}
