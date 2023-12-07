package com.example.backend.seat.Booking.Service.service;

import com.example.backend.seat.Booking.Service.dto.SeatResponse;
import com.example.backend.seat.Booking.Service.exception.SeatNotFoundException;
import com.example.backend.seat.Booking.Service.model.Seat;
import com.example.backend.seat.Booking.Service.repository.SeatPricingRepository;
import com.example.backend.seat.Booking.Service.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SeatPricingRepository seatPricingRepository;

    public boolean isSeatBooked(Long seatId) {
        Optional<Seat> seat = seatRepository.findById(seatId);
        return seat.map(Seat::isBooked).orElse(false);
    }

    public List<Seat> bookSeats(List<Long> seatIds) {
        List<Seat> bookedSeats = new ArrayList<>();

        for (Long seatId : seatIds) {
            Optional<Seat> seatOptional = seatRepository.findById(seatId);

            if (seatOptional.isPresent() && !seatOptional.get().isBooked()) {
                Seat seat = seatOptional.get();
                seat.setBooked(true);
                seatRepository.save(seat);
                bookedSeats.add(seat);
            }
        }

        return bookedSeats;
    }

    public Seat assignSeatClass(Long seatId) {
        Seat seat=new Seat();

        // Assign seat class based on ranges of seat IDs
        if (seatId >= 1 && seatId <= 10) {
            seat.setSeatClass("A");
        } else if (seatId >= 11 && seatId <= 20) {
            seat.setSeatClass("B");
        } else if (seatId >= 21 && seatId <= 30) {
            seat.setSeatClass("C");
        } else if (seatId >= 31 && seatId <= 40) {
            seat.setSeatClass("D");
        } else if (seatId >= 41 && seatId <= 50) {
            seat.setSeatClass("E");
        } else if (seatId >= 51 && seatId <= 60) {
            seat.setSeatClass("F");
        } else if (seatId >= 61 && seatId <= 70) {
            seat.setSeatClass("G");
        } else if (seatId >= 71 && seatId <= 80) {
            seat.setSeatClass("H");
        } else if (seatId >= 81 && seatId <= 90) {
            seat.setSeatClass("I");
        } else if (seatId >= 91 && seatId <= 100) {
            seat.setSeatClass("J");
        }

        return seat;
    }
}