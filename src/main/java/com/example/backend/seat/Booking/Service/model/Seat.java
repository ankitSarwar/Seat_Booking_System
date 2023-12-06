package com.example.backend.seat.Booking.Service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Seat class cannot be blank")
    private String seatClass;

    private boolean booked;

    @ManyToOne
    @JoinColumn(name = "seat_pricing_id")
    private SeatPricing seatPricing;

    private static int availableSeats = 500;

//    private int availableSeats;

    public static int getAvailableSeats() {
        return availableSeats;
    }

    public static void setAvailableSeats(int newAvailableSeats) {
        availableSeats = newAvailableSeats;
    }

}
