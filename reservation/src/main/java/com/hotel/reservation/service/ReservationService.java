package com.hotel.reservation.service;

import com.hotel.reservation.common.ReservationResponse;
import com.hotel.reservation.dto.ReservationDTO;
import com.hotel.reservation.dto.ReservedDatesDTO;
import com.hotel.reservation.model.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {
    ReservationResponse makeReservation(ReservationDTO reservationDTO);
    List<String> getAvailableRooms( List<String> roomNumbers,
                                    LocalDate enteredCheckIn,
                                    LocalDate enteredCheckOut,
                                    List<Reservation> existingReservations
    );
    String getRandomAvailableRoom(List<String> availableRooms);
    List<ReservationDTO> getAllReservations();
    List<ReservedDatesDTO> getReservedDates(List<String> responseRoomNumbers);
    ReservationDTO getMyReservation(Integer reservationId);
    ReservationDTO changeMyReservation(ReservationDTO reservationDTO);
    Integer deleteMyReservation(Integer reservationId);
}
