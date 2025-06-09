package com.perfortival.reservation.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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

@WebServlet("/reservation/step1")
public class ReservationStep1Controller extends HttpServlet {

    private PerformanceDAO performanceDAO = new PerformanceDAO();
    private SeatService seatService = new SeatService();
    private ReservationService reservationService = new ReservationService(); // 추가

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        String performanceId = request.getParameter("performanceId");
        String date = request.getParameter("date");
        String time = request.getParameter("time");

        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/member/login"); 
            return;
        }

        PerformanceDTO performance = performanceDAO.getPerformanceById(performanceId);
        if (performance == null) {
            request.setAttribute("error", "공연 정보를 찾을 수 없습니다.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            return;
        }

        // 예매 수량 제한 확인 (1인당 최대 2개)
        int userReservationCount = reservationService.getUserReservationCount(
        	    loginUser.getId(), performanceId, date, time
    	);
    	if (userReservationCount >= 2) {
    	    response.setContentType("text/html; charset=UTF-8");
    	    PrintWriter out = response.getWriter();
    	    out.println("<script>");
    	    out.println("alert('이미 이 공연에 대해 최대 수량(2개)을 예매하셨습니다.');");
    	    out.println("location.href='" + request.getContextPath() + "/performances/detail?id=" + performanceId + "';");
    	    out.println("</script>");
    	    out.close();
    	    return;
    	}

        String reservationType = performance.getReservationType();
        System.out.println("[DEBUG] reservationType = " + reservationType);

        request.setAttribute("performance", performance);
        request.setAttribute("date", date);
        request.setAttribute("time", time);

        String quantityStr = request.getParameter("quantity");
        int quantity = 1;
        try {
            quantity = Integer.parseInt(quantityStr);
            if (quantity < 1 || quantity > 2) {
                quantity = 1;
            }
        } catch (NumberFormatException e) {
            quantity = 1;
        }
        request.setAttribute("quantity", quantity);

        if ("자유석".equals(reservationType)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate start = LocalDate.parse(performance.getStartDate(), formatter);
            LocalDate end = LocalDate.parse(performance.getEndDate(), formatter);
            long dayCount = ChronoUnit.DAYS.between(start, end) + 1;
            request.setAttribute("maxDays", dayCount);

            Integer price = performanceDAO.getFreeSeatPrice(performanceId);
            performance.setBasePrice(price != null ? price : 0);
            request.setAttribute("performance", performance);

            request.getRequestDispatcher("/WEB-INF/views/reservation/free_ticket.jsp").forward(request, response);
            return;
        }

        List<SeatDTO> seatList = seatService.getSeatListWithReservation(performanceId, date, time);
        request.setAttribute("seatList", seatList);

        switch (reservationType) {
            case "좌석A":
                request.getRequestDispatcher("/WEB-INF/views/reservation/seat_type_a.jsp").forward(request, response);
                break;
            case "좌석B":
                request.getRequestDispatcher("/WEB-INF/views/reservation/seat_type_b.jsp").forward(request, response);
                break;
            case "혼합":
                request.getRequestDispatcher("/WEB-INF/views/reservation/seat_type_mixed.jsp").forward(request, response);
                break;
            default:
                request.setAttribute("error", "지원하지 않는 예매 방식입니다.");
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                break;
        }
    }
}
