package com.example.backend.seat.Booking.Service.service;

import com.example.backend.seat.Booking.Service.dto.BookingResponse;
import com.example.backend.seat.Booking.Service.dto.EmailDetail;
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

    @Autowired
    SeatService seatService;


    //    public List<SeatResponse> getAllSeatsWithStatus() {
//        List<Seat> seats = seatRepository.findAllByOrderBySeatClass();
//        if (seats.isEmpty()) {
//            return Collections.singletonList(new SeatResponse(null, null, false, "Hall is Empty"));
//        }
//
//        return seats.stream()
//                .map(seat -> new SeatResponse(seat.getId(), seat.getSeatClass(), seat.isBooked(), "yourPricingInfoHere"))
//                .collect(Collectors.toList());
//    }


    public List<SeatResponse> getAllSeatsWithStatus() {
        List<Seat> seats = seatRepository.findAllByOrderBySeatClass();
        if (seats.isEmpty()) {
            return Collections.singletonList(new SeatResponse(null, null, false, "Hall is Empty"));
        }

        return seats.stream()
                .map(seat -> new SeatResponse(seat.getId(), seat.getSeatClass(), seat.isBooked(), "yourPricingInfoHere"))
                .collect(Collectors.toList());
    }


    public double calculatePercentageBooked(String seatClass) {
        long totalSeats = seatRepository.countBySeatClass(seatClass);
        long bookedSeats = seatRepository.countBookedSeatsBySeatClass(seatClass);
        return (double) bookedSeats / totalSeats * 100;
    }

    public BookingResponse createBooking(List<Long> seatIds, String userName, String phoneNumber)
            throws SeatNotFoundException, BookingFailedException {
        if (seatIds == null || seatIds.isEmpty()) {
            throw new IllegalArgumentException("seatIds cannot be null or empty! Please select at least one seat.");
        }

        double totalAmount = 0.0;

        BookingResponse response = new BookingResponse();

        Booking booking = new Booking();
        booking.setUserName(userName);
        booking.setPhoneNumber(phoneNumber);


        for (Long seatId : seatIds) {
            Optional<Seat> optionalSeat = seatRepository.findById(seatId);

            if (optionalSeat.isPresent()) {
                Seat seatPresent = optionalSeat.get();
                if (seatPresent.isBooked()) {
                    throw new BookingFailedException("Seat " + seatPresent.getId() + " is already booked.");
                }
            }

            Seat seat=new Seat();

            Seat assignedSeat = seatService.assignSeatClass(seatId);

            double currentPrice = calculateCurrentPrice(assignedSeat.getSeatClass(),
                    seatRepository.countBySeatClass(assignedSeat.getSeatClass()));
            totalAmount += currentPrice;

            seat.setId(seatId);
            seat.setBooked(true);
            seat.setSeatClass(assignedSeat.getSeatClass());
            seat.setAvailableSeats(seat.getAvailableSeats() - 1);
//            seat.setCurrentPricing(currentPrice);

            seatRepository.save(seat);
        }
        bookingRepository.save(booking);



        response.setBookingId(booking.getId());
        response.setTotalAmount(totalAmount);
        return response;
    }



    public double calculateTotalAmount(Booking booking) {
        double totalAmount = 0.0;

        for (Seat seat : booking.getSeats()) {
            totalAmount += calculateCurrentPrice(seat.getSeatClass(), seatRepository.countBySeatClass(seat.getSeatClass()));
        }

        return totalAmount;
    }


    public double calculateCurrentPrice(String seatClass, long bookedSeats) {
        // Implement dynamic pricing logic based on booked seats percentage
        // This could involve using the SeatPricing information

        long totalSeats = seatRepository.countBySeatClass(seatClass);

        Optional<SeatPricing> seatPricing = seatPricingRepository.findBySeatClass(seatClass);

        if (!seatPricing.isPresent()) {
            // Handle missing pricing information
            return 397; // default to normal price if any seat is not booked
        }

        SeatPricing pricing = seatPricing.get();
        double percentageBooked = (double) bookedSeats / totalSeats * 100;

        if (percentageBooked < 40) {
            return  pricing.getMinPrice() ; // use min_price or default to normal price
        } else if (percentageBooked >= 40 && percentageBooked < 60) {
            return  pricing.getNormalPrice() ; // use normal_price or default to normal price
        } else {
            return pricing.getMaxPrice() != 0.0 ? pricing.getMaxPrice() :
                    (pricing.getNormalPrice() != 0.0 ? pricing.getNormalPrice() : 397);
            // use max_price, normal_price or default to normal price
        }


    }



    public List<Booking> getBookingsByUserIdentifier(String userIdentifier) {
        if (userIdentifier == null || userIdentifier.isEmpty()) {
            throw new IllegalArgumentException("User identifier cannot be empty.");
        } else if (isNumeric(userIdentifier)) {
            return bookingRepository.findByPhoneNumber(userIdentifier);
        } else {
            return bookingRepository.findByUserName(userIdentifier);
        }
    }
    // Helper method to check if a string is numeric
    private boolean isNumeric(String str) {
        return str.matches("\\d+");
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
        Optional<SeatPricing> seatPricing = seatPricingRepository.findBySeatClass(seatClass);

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

//        return new SeatResponse(seat.getId(), seatClass, seat.isBooked(), currentPrice);
        return new SeatResponse(seat.getId(), seatClass, String.valueOf(currentPrice));
    }

}