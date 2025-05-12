package com.perfortival.performance.controller;

import java.io.IOException;

import com.perfortival.performance.dto.PerformanceDTO;
import com.perfortival.performance.service.PerformanceService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/performances/detail")
public class PerformanceDetailController extends HttpServlet {

    private PerformanceService performanceService = new PerformanceService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        if (id == null || id.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/performances/search");
            return;
        }

        PerformanceDTO performance = performanceService.getPerformanceById(id);

        if (performance == null) {
            request.setAttribute("errorMessage", "해당 공연 정보를 찾을 수 없습니다.");
        }

        request.setAttribute("performance", performance);
        request.getRequestDispatcher("/WEB-INF/views/performance/performanceDetail.jsp").forward(request, response);
    }
}
