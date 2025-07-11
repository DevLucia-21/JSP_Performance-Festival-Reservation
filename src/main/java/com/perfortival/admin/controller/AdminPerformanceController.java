package com.perfortival.admin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.perfortival.performance.dao.PerformanceDAO;
import com.perfortival.performance.dto.PerformanceDTO;
import com.perfortival.performance.service.PerformanceService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/performances")
public class AdminPerformanceController extends HttpServlet {

    private PerformanceService performanceService = new PerformanceService();
    private PerformanceDAO performanceDAO = new PerformanceDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchKeyword = request.getParameter("searchKeyword");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        List<PerformanceDTO> searchResults = new ArrayList<>();

        try {
            if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
                searchResults = performanceService.fetchPerformances(searchKeyword, startDate, endDate);
                System.out.println("[INFO] 검색 결과 수: " + searchResults.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "공연 검색 중 오류가 발생했습니다.");
        }

        request.setAttribute("searchResults", searchResults);
        request.getRequestDispatcher("/WEB-INF/views/admin/adminPerformances.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] selectedIds = request.getParameterValues("selectedPerformances");
        String action = request.getParameter("action");
        
        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/admin/performances");
            return;
        }

        try {
            switch (action) {
                case "save":
                    if (selectedIds != null) handleSave(selectedIds);
                    response.sendRedirect(request.getContextPath() + "/admin/performances");
                    return;

                case "reservation":
                    if (selectedIds != null && selectedIds.length > 0) {
                        handleReservation(selectedIds, request, response);
                        return; 
                    } else {
                        System.out.println("[WARN] 예매 방식 설정할 공연이 선택되지 않았습니다.");
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/admin/performances");
    }

    private void handleSave(String[] selectedIds) {
        for (String id : selectedIds) {
            try {
                PerformanceDTO performance = performanceService.getPerformanceById(id);
                if (performance == null) continue;

                performance.setAdminSelected(true);
                if (performance.getPosterUrl() == null || performance.getPosterUrl().isEmpty()) {
                    performance.setPosterUrl("https://default-image-path.com/default.jpg");
                }

                performanceDAO.savePerformance(performance);
                System.out.println("[INFO] 공연 저장 완료: " + performance.getTitle());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void handleReservation(String[] selectedIds, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // /reservation?selectedPerformances=id1&id2 형식으로 리다이렉트
        StringBuilder redirectUrl = new StringBuilder(request.getContextPath() + "/reservation?");
        for (int i = 0; i < selectedIds.length; i++) {
            redirectUrl.append("selectedPerformances=").append(selectedIds[i]);
            if (i < selectedIds.length - 1) redirectUrl.append("&");
        }

        System.out.println("[INFO] 예매 방식 관리 리다이렉트: " + redirectUrl);
        response.sendRedirect(redirectUrl.toString());
    }
}
