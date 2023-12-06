package com.example.backend.seat.Booking.Service.dto;
import com.example.backend.seat.Booking.Service.model.SeatPricing;
import lombok.Data;

@Data
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

    public SeatResponse(Long id, String seatClass, boolean booked, double seatPricing) {
        this.id = id;
        this.seatClass = seatClass;
        this.booked = booked;
        this.currentPrice = seatPricing>=30 ? seatPricing : 100.0; // Set a default value
    }

    public SeatResponse(Long id, String seatClass, boolean booked, SeatPricing seatPricing) {
    }
}