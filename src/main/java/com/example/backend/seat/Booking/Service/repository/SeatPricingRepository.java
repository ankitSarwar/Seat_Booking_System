package com.example.backend.seat.Booking.Service.repository;

import com.example.backend.seat.Booking.Service.model.Seat;
import com.example.backend.seat.Booking.Service.model.SeatPricing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatPricingRepository extends JpaRepository<SeatPricing, Long> {
        static Optional<SeatPricing> findBySeatClass(String seatClass) {
        return null;
    }

}
