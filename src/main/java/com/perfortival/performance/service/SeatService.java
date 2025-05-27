package com.perfortival.performance.service;

import java.util.List;

import com.perfortival.performance.dao.SeatDAO;
import com.perfortival.performance.dto.SeatDTO;

public class SeatService {

    private SeatDAO seatDAO = new SeatDAO();

    // 공연 ID로 좌석 정보 그대로 반환
    public List<SeatDTO> getExpandedSeatList(String performanceId) {
        return seatDAO.getSeatsByPerformance(performanceId);
    }
}
