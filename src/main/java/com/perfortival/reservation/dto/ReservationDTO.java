package com.perfortival.reservation.dto;

import com.perfortival.performance.dto.SeatDTO;

public class ReservationDTO {
    private int id;
    private String performanceId;
    private String memberId;
    private String reservationDate;
    private String reservationTime;

    private Integer seatId;
    private Integer quantity;
    private Integer days;
    private String paymentStatus;
    private String performanceTitle;
    private SeatDTO seat;
    private String status;

    // Getter/Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPerformanceId() { return performanceId; }
    public void setPerformanceId(String performanceId) { this.performanceId = performanceId; }

    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }

    public String getReservationDate() { return reservationDate; }
    public void setReservationDate(String reservationDate) { this.reservationDate = reservationDate; }

    public String getReservationTime() { return reservationTime; }
    public void setReservationTime(String reservationTime) { this.reservationTime = reservationTime; }

    public Integer getSeatId() { return seatId; }
    public void setSeatId(Integer seatId) { this.seatId = seatId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Integer getDays() { return days; }
    public void setDays(Integer days) { this.days = days; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getPerformanceTitle() { return performanceTitle; }
    public void setPerformanceTitle(String performanceTitle) { this.performanceTitle = performanceTitle; }

    public SeatDTO getSeat() { return seat; }
    public void setSeat(SeatDTO seat) { this.seat = seat; }
    
    public String getStatus() { return status;}
    public void setStatus(String status) { this.status = status; }
    
    public void setDays(int days) { this.days = days; }

}
