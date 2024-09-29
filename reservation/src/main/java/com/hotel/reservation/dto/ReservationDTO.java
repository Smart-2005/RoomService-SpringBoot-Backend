package com.hotel.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hotel.rooms.model.RoomType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private Integer reservationId;
    private String roomNumber;
    private String fullname;
    private String email;
    private String contactNumber;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkIn;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkOut;
    private BigDecimal totalPrice;
}
