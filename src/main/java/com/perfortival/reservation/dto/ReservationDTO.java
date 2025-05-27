package com.perfortival.reservation.dto;

public class ReservationDTO {
    private int id;  // 예약 ID (PK, 자동 증가)
    private String performanceId;  // 공연 ID
    private String memberId;       // 예매자 (회원 ID)
    private String reservationDate;  // 예매 날짜
    private String reservationTime;  // 예매 시간

    // 좌석형 예매
    private Integer seatId;

    // 자유석 예매
    private Integer quantity;
    private Integer days;

    // 생성자
    public ReservationDTO() {}

    // Getter / Setter
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getPerformanceId() {
        return performanceId;
    }
    public void setPerformanceId(String performanceId) {
        this.performanceId = performanceId;
    }

    public String getMemberId() {
        return memberId;
    }
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getReservationDate() {
        return reservationDate;
    }
    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservationTime() {
        return reservationTime;
    }
    public void setReservationTime(String reservationTime) {
        this.reservationTime = reservationTime;
    }

    public Integer getSeatId() {
        return seatId;
    }
    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getDays() {
        return days;
    }
    
    public void setDays(Integer days) {
        this.days = days;
    }
    
    private String paymentStatus;

    public String getPaymentStatus() {
        return paymentStatus;
    }
    
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
