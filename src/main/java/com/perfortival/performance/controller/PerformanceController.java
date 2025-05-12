package com.perfortival.performance.controller;

import java.io.IOException;
import java.util.List;

import com.perfortival.performance.dao.PerformanceDAO;
import com.perfortival.performance.dto.PerformanceDTO;
import com.perfortival.performance.service.PerformanceService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/performances")
public class PerformanceController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private PerformanceDAO performanceDAO = new PerformanceDAO();
    private PerformanceService performanceService = new PerformanceService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String searchKeyword = request.getParameter("searchKeyword");

        startDate = (startDate == null || startDate.isEmpty()) ? "20250101" : startDate;
        endDate = (endDate == null || endDate.isEmpty()) ? "20251231" : endDate;
        searchKeyword = (searchKeyword == null) ? "" : searchKeyword;

        try {
            savePerformancesFromAPI(searchKeyword, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "API 데이터 저장 중 오류 발생");
        }

        response.sendRedirect(request.getContextPath() + "/performances/search");
    }

    private void savePerformancesFromAPI(String keyword, String startDate, String endDate) {
        List<PerformanceDTO> fetchedPerformances = performanceService.fetchPerformances(keyword, startDate, endDate);
        for (PerformanceDTO performance : fetchedPerformances) {
            performanceDAO.savePerformance(performance);
        }
    }
}
