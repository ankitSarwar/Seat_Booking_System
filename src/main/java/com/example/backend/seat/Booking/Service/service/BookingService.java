package com.example.backend.seat.Booking.Service.service;

import com.example.backend.seat.Booking.Service.dto.SeatResponse;
import com.example.backend.seat.Booking.Service.exception.BookingFailedException;
import com.example.backend.seat.Booking.Service.exception.EmptyInputException;
import com.example.backend.seat.Booking.Service.exception.SeatNotFoundException;
import com.example.backend.seat.Booking.Service.model.Booking;
import com.example.backend.seat.Booking.Service.model.Seat;
import com.example.backend.seat.Booking.Service.model.SeatPricing;
import com.example.backend.seat.Booking.Service.repository.BookingRepository;
import com.example.backend.seat.Booking.Service.repository.SeatPricingRepository;
import com.example.backend.seat.Booking.Service.repository.SeatRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingService {

//    private static int availableSeats = 500;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SeatPricingRepository seatPricingRepository;

    public List<SeatResponse> getAllSeatsWithStatus() {
        List<Seat> seats =  seatRepository.findAll();
        return seats.stream()
                .map(seat -> new SeatResponse(seat.getId(), seat.getSeatClass(), seat.isBooked()))
                .collect(Collectors.toList());
    }

//    public SeatResponse getSeatWithPricing(Long id) throws SeatNotFoundException, EmptyInputException {
////        Seat seat = seatRepository.findByAvailableSeats(id);
////        Optional<SeatPricing> pricing = SeatPricingRepository.findBySeatClass(seat.getSeatClass());
//
//
//        if (!pricing.isPresent()) {
//            throw new EmptyInputException("No pricing information found for seat class: " + seat.getSeatClass());
//        }
//
//        return new SeatResponse(seat.getId(), seat.getSeatClass(), seat.isBooked(), pricing.get());
//    }

//    public Booking createBooking(List<Long> seatIds, String userName, String phoneNumber) throws SeatNotFoundException, BookingFailedException {
//        if (seatIds == null) {
//            throw new IllegalArgumentException("seatIds cannot be null");
//        }
//
//        double totalAmount = 0.0;
//        Set<Seat> seatsToBook = new HashSet<>();
//
//        for (Long seatId : seatIds) {
//            Seat seat = seatRepository.findByAvailableSeats(seatId);
//
//            if (seat != null && seat.isBooked()) {
//                throw new BookingFailedException("Seat " + seat.getId() + " is already booked");
//            }
//
//            if (seat != null) {
//                double currentPrice = calculateCurrentPrice(seat.getSeatClass(), SeatRepository.countBySeatClass(seat.getSeatClass()));
//                totalAmount += currentPrice;
//                seatsToBook.add(seat);
//            }
//        }

        // Subtract booked seats from available seats for each seat
//        List<Seat> availableSeats = (List<Seat>) seatRepository.findByAvailableSeats();
//        for (Seat seat : availableSeats) {
//            int currentAvailableSeats = seat.getAvailableSeats();
//            seat.setAvailableSeats(currentAvailableSeats - 1);
//        }
//        seatRepository.saveAll(availableSeats);
//
//        Booking booking = createBookingEntity(totalAmount, userName, phoneNumber, seatsToBook);
//        bookingRepository.save(booking);
//
//        // Save the updated seat information
//        seatRepository.saveAll(seatsToBook);
//
//        return booking;
//    }

    public double calculateTotalAmount(Booking booking) {
        double totalAmount = 0.0;

        for (Seat seat : booking.getSeats()) {
            totalAmount += calculateCurrentPrice(seat.getSeatClass(), SeatRepository.countBySeatClass(seat.getSeatClass()));
        }

        return totalAmount;
    }

    private double calculateCurrentPrice(String seatClass, long bookedSeats) {
        // Implement dynamic pricing logic based on booked seats percentage
        // This could involve using the SeatPricing information

        long totalSeats = SeatRepository.countBySeatClass(seatClass);

        Optional<SeatPricing> seatPricing = SeatPricingRepository.findBySeatClass(seatClass);

        if (!seatPricing.isPresent()) {
            // Handle missing pricing information
            return 0.0;
        }

        SeatPricing pricing = seatPricing.get();
        double percentageBooked = (double) bookedSeats / totalSeats * 100;

        if (percentageBooked < 40) {
            return pricing.getMinPrice();
        } else if (percentageBooked >= 40 && percentageBooked < 60) {
            return pricing.getNormalPrice();
        } else {
            return pricing.getMaxPrice();
        }
    }

    public List<Booking> getBookingsByUserIdentifier(String userIdentifier) {
        if (userIdentifier.contains("@")) {
            return bookingRepository.findByUserName(userIdentifier);
        } else {
            return bookingRepository.findByPhoneNumber(userIdentifier);
        }
    }

    private Booking createBookingEntity(double totalAmount, String userName, String phoneNumber, Set<Seat> seats) {
        Booking booking = new Booking();
        booking.setUserName(userName);
        booking.setPhoneNumber(phoneNumber);
        booking.setSeats(seats);
        booking.setTotalAmount(totalAmount);
        return booking;
    }


    public SeatResponse getSeatDetailsWithPricing(Long seatId) throws SeatNotFoundException, EmptyInputException {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new SeatNotFoundException("Seat not found with id: " + seatId));

        String seatClass = seat.getSeatClass();
        Optional<SeatPricing> seatPricing = SeatPricingRepository.findBySeatClass(seatClass);

        if (!seatPricing.isPresent()) {
            throw new EmptyInputException("No pricing information found for seat class: " + seatClass);
        }

        double percentageBooked = calculatePercentageBooked(seatClass);

        double currentPrice;
        if (percentageBooked < 40) {
            currentPrice = seatPricing.get().getMinPrice();
        } else if (percentageBooked >= 40 && percentageBooked < 60) {
            currentPrice = seatPricing.get().getNormalPrice();
        } else {
            currentPrice = seatPricing.get().getMaxPrice();
        }

        return new SeatResponse(seat.getId(), seatClass, seat.isBooked(), currentPrice);
    }

    private double calculatePercentageBooked(String seatClass) {
        long totalSeats = SeatRepository.countBySeatClass(seatClass);
        long bookedSeats = seatRepository.countBookedSeatsBySeatClass(seatClass);
        return (double) bookedSeats / totalSeats * 100;
    }
}