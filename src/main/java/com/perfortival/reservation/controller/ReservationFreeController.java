package com.perfortival.reservation.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import com.perfortival.performance.dao.PerformanceDAO;
import com.perfortival.performance.dto.PerformanceDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/reservation/free")
public class ReservationFreeController extends HttpServlet {

    private final PerformanceDAO performanceDAO = new PerformanceDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String performanceId = request.getParameter("performanceId");
        String date = request.getParameter("date");
        String time = request.getParameter("time");

        PerformanceDTO performance = performanceDAO.getPerformanceById(performanceId);

        // maxDays 계산 (공연 시작 ~ 종료 사이 날짜 수)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(performance.getStartDate(), formatter);
        LocalDate end = LocalDate.parse(performance.getEndDate(), formatter);
        long maxDays = ChronoUnit.DAYS.between(start, end) + 1;

        request.setAttribute("performance", performance);
        request.setAttribute("date", date);
        request.setAttribute("time", time);
        request.setAttribute("maxDays", maxDays);

        request.getRequestDispatcher("/WEB-INF/views/reservation/reservation_free.jsp").forward(request, response);
    }
}
