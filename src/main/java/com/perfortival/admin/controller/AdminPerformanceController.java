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

    // API에서 받아온 공연 데이터를 임시로 저장할 리스트
    private List<PerformanceDTO> searchResults = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchKeyword = request.getParameter("searchKeyword");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        searchResults.clear();  // 이전 검색 결과 초기화

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

        if (selectedIds != null) {
            for (String id : selectedIds) {
                try {
                    System.out.println("[DEBUG] 저장하려는 ID: " + id);

                    // searchResults 리스트에서 선택한 공연 찾기
                    PerformanceDTO selectedPerformance = null;

                    for (PerformanceDTO performance : searchResults) {
                        if (performance.getId().equals(id)) {
                            selectedPerformance = performance;
                            break;
                        }
                    }

                    if (selectedPerformance != null) {
                        System.out.println("[INFO] 저장할 공연명: " + selectedPerformance.getTitle());
                        selectedPerformance.setAdminSelected(true);

                        // 포스터 URL이 null이 아니면 설정
                        String posterUrl = selectedPerformance.getPosterUrl();
                        if (posterUrl == null || posterUrl.isEmpty()) {
                            posterUrl = "https://default-image-path.com/default.jpg"; // 기본 이미지 경로
                        }
                        selectedPerformance.setPosterUrl(posterUrl);

                        // DB에 저장
                        performanceDAO.savePerformance(selectedPerformance);

                        System.out.println("[INFO] 공연 저장 성공: " + selectedPerformance.getTitle() + " / 포스터: " + posterUrl);
                    } else {
                        System.out.println("[WARN] 해당 ID의 공연이 존재하지 않음: " + id);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("errorMessage", "공연 등록 중 오류가 발생했습니다.");
                }
            }
        }

        response.sendRedirect(request.getContextPath() + "/admin/performances");
    }
}
