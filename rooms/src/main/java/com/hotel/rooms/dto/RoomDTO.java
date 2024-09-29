package com.hotel.rooms.dto;

import com.hotel.rooms.model.RoomType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
    private Integer roomId;
    private String roomNumber;
    private String roomDescription;
    @Enumerated(EnumType.STRING)
    private RoomType roomType;
    private Integer roomCapacity;
    private BigDecimal roomPrice;
}
