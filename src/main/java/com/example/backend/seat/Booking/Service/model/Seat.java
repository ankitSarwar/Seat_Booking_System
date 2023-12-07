package com.example.backend.seat.Booking.Service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "[A-J]", message = "Seat class must be a single character from A to J")
    private String seatClass;

    private String seat_identifier;

    private boolean booked=false;


    private static int availableSeats = 100;


    public static int getAvailableSeats() {
        return availableSeats;
    }

    public static void setAvailableSeats(int newAvailableSeats) {
        availableSeats = newAvailableSeats;
    }

}