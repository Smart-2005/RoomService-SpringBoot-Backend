package com.hotel.reservation.model;

import com.hotel.rooms.model.RoomType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reservationId;
    private String roomNumber;
    private String fullname;
    private String email;
    private String contactNumber;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    private LocalDate checkIn;
    private LocalDate checkOut;
    private BigDecimal totalPrice;

}
