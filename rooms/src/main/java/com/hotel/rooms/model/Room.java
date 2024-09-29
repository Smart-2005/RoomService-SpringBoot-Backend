package com.hotel.rooms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roomId;
    private String roomNumber;
    private String roomDescription;
    @Enumerated(EnumType.STRING)
    private RoomType roomType;
    private Integer roomCapacity;
    private BigDecimal roomPrice;

}
