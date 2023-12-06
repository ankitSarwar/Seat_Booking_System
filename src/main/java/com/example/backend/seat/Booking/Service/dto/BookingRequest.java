package com.example.backend.seat.Booking.Service.dto;

import lombok.Data;
import java.util.List;

@Data
public class BookingRequest {
    private List<Long> seatIds;
    private String userName;
    private String phoneNumber;

}