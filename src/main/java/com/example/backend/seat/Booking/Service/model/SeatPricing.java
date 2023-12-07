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

    private double minPrice;

    private double normalPrice;

    private double maxPrice;


}
//{
//        "id": 1,
//        "seatClass": "A",
//        "minPrice": 0,
//        "maxPrice": 547.20,
//        "normalPrice": 397.61
//        },
//        {
//        "id": 2,
//        "seatClass": "B",
//        "minPrice": 183.44,
//        "maxPrice": 766.96,
//        "normalPrice": 441.65
//        },