package com.perfortival.main.controller;

import java.io.IOException;
import java.util.List;

import com.perfortival.performance.dao.PerformanceDAO;
import com.perfortival.performance.dto.PerformanceDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/main")
public class MainController extends HttpServlet {

    private PerformanceDAO performanceDAO = new PerformanceDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PerformanceDAO performanceDAO = new PerformanceDAO();
        List<PerformanceDTO> performances = performanceDAO.getMainPagePerformances();
        request.setAttribute("performances", performances);
        
        // 절대 경로로 지정
        request.getRequestDispatcher("/WEB-INF/views/main.jsp").forward(request, response);
    }

}