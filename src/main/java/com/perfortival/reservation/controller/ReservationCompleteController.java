package com.perfortival.reservation.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.perfortival.member.dto.MemberDTO;
import com.perfortival.performance.dto.SeatDTO;
import com.perfortival.performance.service.SeatService;
import com.perfortival.reservation.dto.ReservationDTO;
import com.perfortival.reservation.service.ReservationService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/reservation/complete")
public class ReservationCompleteController extends HttpServlet {

    private final ReservationService reservationService = new ReservationService();
    private final SeatService seatService = new SeatService();

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
        String daysStr = request.getParameter("days");
        String[] seatIdArray = request.getParameterValues("seatId");
        String cardNumber = request.getParameter("cardNumber");

        int days = (daysStr != null && !daysStr.isEmpty()) ? Integer.parseInt(daysStr) : 1;
        int totalPrice = 0;
        List<String> seatLabels = new ArrayList<>();

        boolean allSuccess = true;

        if (seatIdArray != null && seatIdArray.length > 0) {
            for (String seatIdStr : seatIdArray) {
                try {
                    int seatId = Integer.parseInt(seatIdStr.trim());
                    SeatDTO seat = seatService.getSeatById(seatId);
                    if (seat == null) {
                        allSuccess = false;
                        break;
                    }

                    int price = seat.getPrice();
                    totalPrice += price;
                    String label = seat.getZone() + "-" + seat.getRow() + seat.getCol();
                    seatLabels.add(label);

                    ReservationDTO dto = new ReservationDTO();
                    dto.setMemberId(loginUser.getId());
                    dto.setPerformanceId(performanceId);
                    dto.setReservationDate(date);
                    dto.setReservationTime(time);
                    dto.setSeatId(seatId);
                    dto.setDays(days);
                    dto.setQuantity(1);
                    dto.setPaymentStatus("결제완료");

                    boolean result = reservationService.reserve(dto);
                    if (!result) {
                        allSuccess = false;
                        break;
                    }

                } catch (Exception e) {
                    allSuccess = false;
                    break;
                }
            }
        }

        if (allSuccess) {
            String maskedCardNumber = maskCardNumber(cardNumber);
            request.setAttribute("totalPrice", totalPrice);
            request.setAttribute("seatLabels", seatLabels);
            request.setAttribute("maskedCardNumber", maskedCardNumber);
            request.getRequestDispatcher("/WEB-INF/views/reservation/complete.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "예매에 실패했습니다.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    private String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) return "****-****-****-****";
        return "****-****-****-" + cardNumber.substring(cardNumber.length() - 4);
    }
}
