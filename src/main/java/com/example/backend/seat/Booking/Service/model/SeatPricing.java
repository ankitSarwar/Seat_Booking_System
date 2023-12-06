package com.example.backend.seat.Booking.Service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SeatPricing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Seat class cannot be blank")
    private String seatClass;

    @PositiveOrZero(message = "Min price must be a positive number or zero")
    private double minPrice;

    @PositiveOrZero(message = "Max price must be a positive number or zero")
    private double maxPrice;

    @PositiveOrZero(message = "Normal price must be a positive number or zero")
    private double normalPrice;


}
