package com.perfortival.admin.controller;

import java.io.IOException;
import java.util.List;

import com.perfortival.reservation.dto.ReservationDTO;
import com.perfortival.reservation.service.ReservationService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/reservationLogs")
public class AdminReservationLogController extends HttpServlet {

    private ReservationService reservationService = new ReservationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<ReservationDTO> logs = reservationService.getAllReservations();

        request.setAttribute("logs", logs);
        request.getRequestDispatcher("/WEB-INF/views/admin/reservationLogs.jsp").forward(request, response);
    }
}
