package com.hotel.reservation.common;

import lombok.Getter;

@Getter
public class ErrorReservation implements ReservationResponse {
    private final String errorMessage;

    public ErrorReservation(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
