package com.example.backend.seat.Booking.Service.repository;

import com.example.backend.seat.Booking.Service.model.Seat;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository  extends JpaRepository<Seat, Long> {


    Optional<Seat> findById(Long seatId);


     long countBySeatClass(String seatClass);

//     int countBySeatClass(String seatClass);

    long countBookedSeatsBySeatClass(String seatClass);

    List<Seat> findAllByOrderBySeatClass();
//    findAllByOrderBySeatClassAsc();


}
