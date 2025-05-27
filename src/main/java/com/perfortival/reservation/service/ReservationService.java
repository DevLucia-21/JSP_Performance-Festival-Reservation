package com.perfortival.reservation.service;

import com.perfortival.reservation.dao.ReservationDAO;
import com.perfortival.reservation.dto.ReservationDTO;

public class ReservationService {

    private ReservationDAO reservationDAO = new ReservationDAO();

    public boolean reserve(ReservationDTO dto) {
        return reservationDAO.insertReservation(dto);
    }
}
