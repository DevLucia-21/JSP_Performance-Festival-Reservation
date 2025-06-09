package com.perfortival.reservation.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.perfortival.member.dto.MemberDTO;
import com.perfortival.performance.dao.PerformanceDAO;
import com.perfortival.performance.dto.PerformanceDTO;
import com.perfortival.performance.dto.SeatDTO;
import com.perfortival.performance.service.SeatService;
import com.perfortival.reservation.service.ReservationService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/reservation/step2")
public class ReservationStep2Controller extends HttpServlet {

    private ReservationService reservationService = new ReservationService();
    private PerformanceDAO performanceDAO = new PerformanceDAO();
    private SeatService seatService = new SeatService();

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

        String memberId = loginUser.getId();
        String performanceId = request.getParameter("performanceId");
        String date = request.getParameter("date");
        String time = request.getParameter("time");

        String seatIdsParam = request.getParameter("seatIds");
        String[] seatIdArray = seatIdsParam != null ? seatIdsParam.split(",") : null;

        String quantityStr = request.getParameter("quantity");
        if (quantityStr != null && !quantityStr.isEmpty()) {
            int quantity = Integer.parseInt(quantityStr);
            if (seatIdArray == null || seatIdArray.length != quantity) {
                request.setAttribute("error", "선택한 좌석 수가 예매 수량과 일치하지 않습니다.");
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                return;
            }
        }

        List<SeatDTO> selectedSeats = new ArrayList<>();
        int totalPrice = 0;

        for (int i = 0; i < seatIdArray.length; i++) {
            int seatId = Integer.parseInt(seatIdArray[i]);

            boolean isDuplicate = reservationService.isDuplicateReservation(
                memberId, performanceId, date, time, seatId);

            if (isDuplicate) {
                request.setAttribute("error", "이미 예매한 좌석이 포함되어 있습니다.");
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                return;
            }

            SeatDTO seat = seatService.getSeatById(seatId);
            if (seat != null) {
                selectedSeats.add(seat);
                totalPrice += seat.getPrice();
            }
        }

        PerformanceDTO performance = performanceDAO.getPerformanceById(performanceId);

        request.setAttribute("performance", performance);
        request.setAttribute("date", date);
        request.setAttribute("time", time);
        request.setAttribute("selectedSeats", selectedSeats);
        request.setAttribute("totalPrice", totalPrice);

        request.getRequestDispatcher("/WEB-INF/views/reservation/payment.jsp").forward(request, response);
    }
}
