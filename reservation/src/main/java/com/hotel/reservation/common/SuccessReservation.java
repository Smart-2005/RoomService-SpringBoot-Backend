package com.hotel.reservation.common;

import com.hotel.reservation.dto.ReservationDTO;
import lombok.Getter;

@Getter
public class SuccessReservation implements ReservationResponse {
        private final ReservationDTO reservationDTO;

    public SuccessReservation(ReservationDTO reservationDTO) {
        this.reservationDTO = reservationDTO;
    }
}
