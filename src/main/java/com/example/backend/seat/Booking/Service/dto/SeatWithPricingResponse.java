package com.example.backend.seat.Booking.Service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatWithPricingResponse {
    private Long id;
    private String seatClass;
    private boolean booked;
    private double currentPrice;


}
