package com.example.backend.seat.Booking.Service.exception;

public class SeatNotFoundException extends RuntimeException {

    public SeatNotFoundException(String message) {
        super(String.valueOf(message));
    }
}