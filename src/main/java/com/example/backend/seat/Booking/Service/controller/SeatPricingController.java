package com.example.backend.seat.Booking.Service.controller;

import com.example.backend.seat.Booking.Service.model.SeatPricing;
import com.example.backend.seat.Booking.Service.repository.SeatPricingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seat-pricings")
public class SeatPricingController {

    @Autowired
    private SeatPricingRepository seatPricingRepository;


    @PostMapping("/bulk-create")
    public ResponseEntity<List<SeatPricing>> createSeatPricings(@RequestBody List<SeatPricing> seatPricings) {
        List<SeatPricing> savedPricings = seatPricingRepository.saveAll(seatPricings);
        return new ResponseEntity<>(savedPricings, HttpStatus.CREATED);
    }
}

