package com.example.backend.seat.Booking.Service.controller;

import com.example.backend.seat.Booking.Service.dto.BookingRequest;
import com.example.backend.seat.Booking.Service.dto.BookingResponse;
import com.example.backend.seat.Booking.Service.dto.SeatResponse;
import com.example.backend.seat.Booking.Service.dto.SeatWithPricingResponse;
import com.example.backend.seat.Booking.Service.exception.EmptyInputException;
import com.example.backend.seat.Booking.Service.exception.PricingNotFoundException;
import com.example.backend.seat.Booking.Service.exception.SeatNotFoundException;
import com.example.backend.seat.Booking.Service.model.Booking;
import com.example.backend.seat.Booking.Service.model.Seat;
import com.example.backend.seat.Booking.Service.model.SeatPricing;
import com.example.backend.seat.Booking.Service.repository.SeatPricingRepository;
import com.example.backend.seat.Booking.Service.repository.SeatRepository;
import com.example.backend.seat.Booking.Service.service.BookingService;
import com.example.backend.seat.Booking.Service.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @Autowired
    SeatRepository seatRepository;

    @Autowired
    SeatPricingRepository seatPricingRepository;

    @Autowired
    SeatService seatService;


    @PostMapping("/booking")
    public ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest bookingRequest) throws Exception {
        BookingResponse response= bookingService.createBooking(bookingRequest.getSeatIds(), bookingRequest.getUserName(), bookingRequest.getPhoneNumber());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getBookings(@RequestParam String userIdentifier) throws Exception {
        List<Booking> bookings = bookingService.getBookingsByUserIdentifier(userIdentifier);
        if (bookings.isEmpty()) {
            throw new SeatNotFoundException("No bookings found for user: " + userIdentifier);
        }
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

}
