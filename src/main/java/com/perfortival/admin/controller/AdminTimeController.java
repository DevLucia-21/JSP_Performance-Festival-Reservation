package com.perfortival.admin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.perfortival.performance.dao.PerformanceDAO;
import com.perfortival.performance.dao.PerformanceTimeDAO;
import com.perfortival.performance.dto.PerformanceDTO;
import com.perfortival.performance.dto.PerformanceTimeDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/times")
public class AdminTimeController extends HttpServlet {

    private PerformanceDAO performanceDAO = new PerformanceDAO();
    private PerformanceTimeDAO timeDAO = new PerformanceTimeDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        List<PerformanceDTO> performanceList = performanceDAO.getAllPerformances();
        request.setAttribute("performanceList", performanceList);

        request.getRequestDispatcher("/WEB-INF/views/admin/timeForm.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String performanceId = request.getParameter("performanceId");
        String[] times = request.getParameterValues("time");

        if (performanceId == null || times == null) {
            response.sendRedirect(request.getContextPath() + "/admin/times?error=1");
            return;
        }

        List<PerformanceTimeDTO> timeList = new ArrayList<>();
        for (String time : times) {
            PerformanceTimeDTO dto = new PerformanceTimeDTO();
            dto.setPerformanceId(performanceId);
            dto.setTime(time);
            timeList.add(dto);
        }

        boolean success = timeDAO.insertTimes(timeList);
        if (success) {
            response.sendRedirect(request.getContextPath() + "/admin/times?success=1");
        } else {
            request.setAttribute("errorMessage", "시간 등록에 실패했습니다.");
            doGet(request, response);
        }
    }
}
