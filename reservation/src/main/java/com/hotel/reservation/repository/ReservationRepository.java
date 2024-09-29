package com.hotel.reservation.repository;

import com.hotel.reservation.model.Reservation;
import com.hotel.rooms.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Integer> {

    @Query(value = "SELECT * FROM reservation WHERE reservation_id = ?1 ",nativeQuery = true)
    Reservation getReservationById(Integer reservationId);

    @Query("SELECT r FROM Reservation r WHERE r.roomNumber IN :roomNumbers")
    List<Reservation> getReservationsByRoomNumbers(@Param("roomNumbers") List<String> roomNumbers);



}
