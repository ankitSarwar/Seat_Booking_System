package com.example.backend.seat.Booking.Service.controller;

import com.example.backend.seat.Booking.Service.dto.SeatResponse;
import com.example.backend.seat.Booking.Service.dto.SeatWithPricingResponse;
import com.example.backend.seat.Booking.Service.exception.PricingNotFoundException;
import com.example.backend.seat.Booking.Service.exception.SeatNotFoundException;
import com.example.backend.seat.Booking.Service.model.Seat;
import com.example.backend.seat.Booking.Service.model.SeatPricing;
import com.example.backend.seat.Booking.Service.repository.SeatPricingRepository;
import com.example.backend.seat.Booking.Service.repository.SeatRepository;
import com.example.backend.seat.Booking.Service.service.BookingService;
import com.example.backend.seat.Booking.Service.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class SeatController {

    @Autowired
    BookingService bookingService;

    @Autowired
    SeatRepository seatRepository;

    @Autowired
    SeatPricingRepository seatPricingRepository;

    @Autowired
    SeatService seatService;

    @GetMapping("/seats")
    public ResponseEntity<List<SeatResponse>> getAllSeats() {
        List<SeatResponse> seats = bookingService.getAllSeatsWithStatus();

        return new ResponseEntity<>(seats, HttpStatus.OK);
    }


    @GetMapping("/seats/{id}")
    public ResponseEntity<SeatWithPricingResponse> getSeatWithPricing(@PathVariable Long id) {
        Optional<Seat> seatOptional = seatRepository.findById(id);

        if (seatOptional.isPresent()) {
            Seat seat = seatOptional.get();
            String seatClass = seat.getSeatClass();

            int totalSeatsInClass = (int) seatRepository.countBySeatClass(seatClass);
            int bookedSeats = (int) seatRepository.countBookedSeatsBySeatClass(seatClass);
            int percentageBooked = (bookedSeats * 100) / totalSeatsInClass;

            double currentPrice = bookingService.calculateCurrentPrice(seatClass, bookedSeats);

            SeatWithPricingResponse response = new SeatWithPricingResponse(seat.getId(), seat.getSeatClass(),
                    seat.isBooked(), currentPrice);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
