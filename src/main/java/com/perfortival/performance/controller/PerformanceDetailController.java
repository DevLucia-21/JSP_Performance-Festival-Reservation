package com.perfortival.performance.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.perfortival.performance.dto.PerformanceDTO;
import com.perfortival.performance.dto.PerformanceTimeDTO;
import com.perfortival.performance.dto.SeatDTO;
import com.perfortival.performance.service.PerformanceService;
import com.perfortival.review.dao.ReviewDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/performances/detail")
public class PerformanceDetailController extends HttpServlet {

    private PerformanceService performanceService = new PerformanceService();
    private ReviewDAO reviewDAO = new ReviewDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String performanceId = request.getParameter("id"); // 공연 ID
        double avgRating = reviewDAO.getAverageRating(performanceId);
        request.setAttribute("avgRating", avgRating);

        if (id == null || id.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/performances/search");
            return;
        }

        PerformanceDTO performance = performanceService.getPerformanceByIdFromDB(id);

        if (performance == null) {
            request.setAttribute("errorMessage", "해당 공연 정보를 찾을 수 없습니다.");
        } else {
            List<PerformanceTimeDTO> timeList = performanceService.getTimesByPerformanceId(id);
            request.setAttribute("timeList", timeList);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            List<String> dateList = new ArrayList<>();
            LocalDate start = LocalDate.parse(performance.getStartDate(), formatter);
            LocalDate end = LocalDate.parse(performance.getEndDate(), formatter);
            while (!start.isAfter(end)) {
                dateList.add(start.toString());
                start = start.plusDays(1);
            }
            request.setAttribute("dateList", dateList);

            long dayCount = ChronoUnit.DAYS.between(
                LocalDate.parse(performance.getStartDate(), formatter),
                LocalDate.parse(performance.getEndDate(), formatter)
            ) + 1;
            request.setAttribute("maxDays", dayCount);

            if (!"자유석".equals(performance.getReservationType())) {
                List<SeatDTO> seatList = performanceService.getSeatsByPerformanceId(id);
                request.setAttribute("seatList", seatList);
            }

            if ("자유석".equals(performance.getReservationType())) {
                Integer price = performanceService.getFreeSeatPrice(id);
                if (price != null) {
                    performance.setBasePrice(price);
                }
            }
        }

        request.setAttribute("performance", performance);
        request.getRequestDispatcher("/WEB-INF/views/performance/performanceDetail.jsp").forward(request, response);
    }
}
