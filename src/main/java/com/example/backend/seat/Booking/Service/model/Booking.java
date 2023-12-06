package com.example.backend.seat.Booking.Service.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "booking_seat", joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "seat_id"))
    private Set<Seat> seats;

//    @ManyToOne
//    @JoinColumn(name = "seat_pricing_id")
//    private SeatPricing seatPricing; // Added this field to establish the relationship

    @NotBlank
    private String userName;

    @Pattern(regexp = "\\d{2}-\\d{10}", message = "Phone number should be in the format XX-XXXXXXXXXX")
    @Column(name = "phone_number")
    private String phoneNumber;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double totalAmount;

}