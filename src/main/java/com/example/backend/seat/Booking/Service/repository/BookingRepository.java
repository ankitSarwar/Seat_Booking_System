package com.example.backend.seat.Booking.Service.repository;

import com.example.backend.seat.Booking.Service.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {


    List<Booking> findByPhoneNumber(String userIdentifier);

    List<Booking> findByUserName(String userIdentifier);

}
