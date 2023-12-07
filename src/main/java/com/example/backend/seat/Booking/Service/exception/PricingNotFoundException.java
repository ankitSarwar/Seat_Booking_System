package com.example.backend.seat.Booking.Service.exception;

public class PricingNotFoundException extends RuntimeException {
    public PricingNotFoundException(String message) {
        super(message);
    }
}
