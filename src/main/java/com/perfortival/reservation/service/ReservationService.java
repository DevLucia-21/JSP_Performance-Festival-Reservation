package com.perfortival.reservation.service;

import java.util.List;

import com.perfortival.performance.dao.SeatDAO;
import com.perfortival.reservation.dao.ReservationDAO;
import com.perfortival.reservation.dto.ReservationDTO;

public class ReservationService {

    private ReservationDAO reservationDAO = new ReservationDAO();
    private SeatDAO seatDAO = new SeatDAO();

    public boolean reserve(ReservationDTO dto) {
        boolean result = reservationDAO.insertReservation(dto);
        if (result && dto.getSeatId() != null) {
            seatDAO.markSeatAsReserved(dto.getSeatId()); // 좌석 예매 완료 처리
        }
        return result;
    }

    // 예매 중복 확인
    public boolean isDuplicateReservation(String memberId, String performanceId, String date, String time, int seatId) {
        return reservationDAO.isDuplicateReservation(memberId, performanceId, date, time, seatId);
    }
    
    // 자유석 예매 수량 조회
    public int getReservedFreeQuantity(String performanceId, String date, String time) {
        return reservationDAO.getReservedFreeQuantity(performanceId, date, time);
    }
    
    public List<ReservationDTO> getReservationsByMemberId(String memberId) {
        return reservationDAO.getReservationsByMemberId(memberId);
    }
    
    public boolean cancelReservation(int reservationId) {
        return reservationDAO.markReservationAsCancelled(reservationId);
    }
    
    public List<ReservationDTO> getAllReservations() {
        return reservationDAO.getAllReservations();
    }
}
