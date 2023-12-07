package com.example.backend.seat.Booking.Service.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
@NoArgsConstructor
public class SeatResponse {
    private Long id;
    private String seatClass;
    private boolean booked;
    private double currentPrice;

    public SeatResponse(Long id, String seatClass, boolean booked) {
        this.id = id;
        this.seatClass = seatClass;
        this.booked = booked;
    }
    public SeatResponse(Long id, String seatClass, boolean booked, double currentPrice) {
        this.id = id;
        this.seatClass = seatClass;
        this.booked = booked;
        this.currentPrice = currentPrice;
    }


    public SeatResponse(Long id, String seatClass, boolean booked, String hallIsEmpty) {
    }

    public SeatResponse(Long id, String seatClass, String yourPricingInfoHere) {
    }


    public SeatResponse(Long id, String seatClass) {
    }
}