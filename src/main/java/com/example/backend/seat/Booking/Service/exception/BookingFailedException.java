package com.example.backend.seat.Booking.Service.exception;

public class BookingFailedException extends RuntimeException {

    public BookingFailedException(String message) {
        super(message);
    }
}