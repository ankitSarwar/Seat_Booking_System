package com.example.backend.seat.Booking.Service.controller;

import com.example.backend.seat.Booking.Service.dto.BookingRequest;
import com.example.backend.seat.Booking.Service.dto.BookingResponse;
import com.example.backend.seat.Booking.Service.dto.SeatResponse;
import com.example.backend.seat.Booking.Service.exception.EmptyInputException;
import com.example.backend.seat.Booking.Service.exception.SeatNotFoundException;
import com.example.backend.seat.Booking.Service.model.Booking;
import com.example.backend.seat.Booking.Service.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookingController {

    @Autowired
    BookingService bookingService;


    @GetMapping("/seats")
    public ResponseEntity<List<SeatResponse>> getAllSeats() throws Exception {
        List<SeatResponse> seats = bookingService.getAllSeatsWithStatus();
        if (seats == null) {
            throw new EmptyInputException("No seats found");
        }
        return new ResponseEntity<>(seats, HttpStatus.OK);
    }

//    @GetMapping("/seats")
//    public ResponseEntity<List<SeatResponse>> getAllSeats() {
//        List<SeatResponse> seats = seatService.getAllSeatsWithStatus();
//        return new ResponseEntity<>(seats, HttpStatus.OK);
//    }

//    @GetMapping("/seats/{id}")
//    public ResponseEntity<SeatResponse> getSeatPricing(@PathVariable Long id) throws Exception {
//        SeatResponse seat = bookingService.getSeatWithPricing(id);
//        if (seat == null) {
//            throw new SeatNotFoundException("Seat not found with id: ");
//        }
//        return new ResponseEntity<>(seat, HttpStatus.OK);
//    }

//    @PostMapping("/booking")
//    public ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest bookingRequest) throws Exception {
//        Booking booking = bookingService.createBooking(bookingRequest.getSeatIds(), bookingRequest.getUserName(), bookingRequest.getPhoneNumber());
//        double totalAmount = bookingService.calculateTotalAmount(booking);
//        BookingResponse response = new BookingResponse(booking.getId(), totalAmount);
//        return ResponseEntity.ok(response);
//    }

//    @GetMapping("/bookings")
//    public ResponseEntity<List<Booking>> getBookings(@RequestParam String userIdentifier) throws Exception {
//        List<Booking> bookings = bookingService.getBookingsByUserIdentifier(userIdentifier);
//        if (bookings.isEmpty()) {
//            throw new SeatNotFoundException("No bookings found for user: " + userIdentifier);
//        }
//        return new ResponseEntity<>(bookings, HttpStatus.OK);
//    }

}
