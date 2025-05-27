package com.perfortival.reservation.controller;

import java.io.IOException;

import com.perfortival.member.dto.MemberDTO;
import com.perfortival.performance.dao.PerformanceDAO;
import com.perfortival.performance.dto.PerformanceDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/reservation/payment")
public class ReservationFreePaymentController extends HttpServlet {

    private PerformanceDAO performanceDAO = new PerformanceDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/member/login.jsp");
            return;
        }

        String performanceId = request.getParameter("performanceId");
        String date = request.getParameter("date");
        String time = request.getParameter("time");

        int quantity = Integer.parseInt(request.getParameter("quantity"));
        int days = Integer.parseInt(request.getParameter("days"));
        int basePrice = Integer.parseInt(request.getParameter("seatPrice")); // or basePrice

        int totalPrice = 0;

        if (days == 1) {
            totalPrice = basePrice * quantity;
        } else if (days == 2) {
            totalPrice = (int)(basePrice * 2 * 0.9 * quantity);
        } else if (days == 3) {
            totalPrice = (int)(basePrice * 3 * 0.85 * quantity);
        }

        PerformanceDTO performance = performanceDAO.getPerformanceById(performanceId);

        request.setAttribute("performance", performance);
        request.setAttribute("date", date);
        request.setAttribute("time", time);
        request.setAttribute("quantity", quantity);
        request.setAttribute("days", days);
        request.setAttribute("price", totalPrice);
        request.setAttribute("seatLabel", null); // 자유석

        request.getRequestDispatcher("/WEB-INF/views/reservation/payment.jsp").forward(request, response);
    }
}
