package com.example.backend.seat.Booking.Service.service;

import com.example.backend.seat.Booking.Service.model.SeatPricing;
import com.example.backend.seat.Booking.Service.repository.SeatPricingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SeatPricingService {

    @Autowired
    private SeatPricingRepository seatPricingRepository;

    public double calculateBookingAmount(String seatClass, int percentageBooked) {
        Optional<SeatPricing> pricingOptional = getPricingBySeatClass(seatClass);
        double price = 0;

        if (pricingOptional.isPresent()) {
            SeatPricing pricing = pricingOptional.get();


            if (percentageBooked < 40) {
                price= pricing.getMinPrice();
            } else if (percentageBooked <= 60) {
                price= pricing.getNormalPrice();
            } else {
                price =pricing.getMaxPrice();
            }
        }
        if(price ==0){
            return pricingOptional.get().getNormalPrice();
        }
        return price; // Handle the case where pricing is not available
    }

    public Optional<SeatPricing> getPricingBySeatClass(String seatClass) {
        return seatPricingRepository.findBySeatClass(seatClass);
    }

}
