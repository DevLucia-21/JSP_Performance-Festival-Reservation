package com.perfortival.performance.controller;

import java.io.IOException;
import java.util.List;

import com.perfortival.performance.dao.PerformanceDAO;
import com.perfortival.performance.dto.PerformanceDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/performances/search")
public class PerformanceSearchController extends HttpServlet {

    private PerformanceDAO performanceDAO = new PerformanceDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchKeyword = request.getParameter("searchKeyword");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        List<PerformanceDTO> performances = searchPerformances(searchKeyword, startDate, endDate);

        request.setAttribute("searchResults", performances);
        request.getRequestDispatcher("/WEB-INF/views/performance/searchPerformances.jsp").forward(request, response);
    }

    private List<PerformanceDTO> searchPerformances(String keyword, String startDate, String endDate) {
        if (isAllEmpty(keyword, startDate, endDate)) {
            return performanceDAO.getAllPerformances();
        }
        return performanceDAO.getPerformances(startDate, endDate, keyword);
    }

    private boolean isAllEmpty(String... values) {
        for (String value : values) {
            if (value != null && !value.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
