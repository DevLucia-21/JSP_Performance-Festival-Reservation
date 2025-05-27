package com.perfortival.performance.service;

import java.util.List;
import java.util.Set;

import com.perfortival.performance.dao.SeatDAO;
import com.perfortival.performance.dto.SeatDTO;
import com.perfortival.reservation.dao.ReservationDAO;

public class SeatService {

	private ReservationDAO reservationDAO = new ReservationDAO();
    private SeatDAO seatDAO = new SeatDAO();

    // 공연 ID로 좌석 정보 그대로 반환
    public List<SeatDTO> getExpandedSeatList(String performanceId) {
        return seatDAO.getSeatsByPerformance(performanceId);
    }
    
    public SeatDTO getSeatById(int seatId) {
        return seatDAO.getSeatById(seatId);
    }
    
    public List<SeatDTO> getSeatListWithReservation(String performanceId, String date, String time) {
        List<SeatDTO> seatList = seatDAO.getSeatsByPerformance(performanceId);
        Set<Integer> reservedSeatIds = reservationDAO.getReservedSeatIds(performanceId, date, time);

        for (SeatDTO seat : seatList) {
            seat.setReserved(reservedSeatIds.contains(seat.getSeatId()));
        }

        return seatList;
    }

}
