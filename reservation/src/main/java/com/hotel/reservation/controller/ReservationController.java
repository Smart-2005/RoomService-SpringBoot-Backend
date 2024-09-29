package com.hotel.reservation.controller;

import com.hotel.reservation.dto.ReservationDTO;
import com.hotel.reservation.dto.ReservedDatesDTO;
import com.hotel.reservation.service.ReservationService;
import com.hotel.reservation.util.StandardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("reservations/makereservation")
    public ResponseEntity<StandardResponse> makeReservation(
            @RequestBody ReservationDTO reservationDTO
    ){
        var madeReservationDetails = reservationService.makeReservation(reservationDTO);
        return new ResponseEntity<>(
                new StandardResponse(200,"Your reservation success! Please save your reservationId for the further process ", madeReservationDetails),
                HttpStatus.OK
        );
    }

    @GetMapping("reservations/myreservation/{reservationId}")
    public ResponseEntity<StandardResponse> getMyReservation(
        @PathVariable Integer reservationId
    ){
        var myReservationDetails = reservationService.getMyReservation(reservationId);
        return new ResponseEntity<>(
                new StandardResponse(200,"Here is your reservation details",myReservationDetails),
                HttpStatus.OK
        );
    }

    @GetMapping("reservations/allreservations")
    public ResponseEntity<StandardResponse> getAllReservation(){
        var allReservationDetails = reservationService.getAllReservations();
        return new ResponseEntity<>(
                new StandardResponse(200,"Here is all reservation details",allReservationDetails),
                HttpStatus.OK
        );
    }

    @GetMapping("reservations/reserveddates")
    public ResponseEntity<List<ReservedDatesDTO>> getReservedDates(
            @RequestParam List<String> roomNumbers){
        List<ReservedDatesDTO> reservedDates = reservationService.getReservedDates(roomNumbers);
        return ResponseEntity.ok(reservedDates);
    }

    @PutMapping("reservations/changemyreservation")
    public ResponseEntity<StandardResponse> changeMyReservation(
            @RequestBody ReservationDTO reservationDTO
    ){
        var changedReservationDetails = reservationService.changeMyReservation(reservationDTO);
        return new ResponseEntity<>(
                new StandardResponse(200,"Your reservation details updated!",changedReservationDetails),
                HttpStatus.OK
        );
    }

    @DeleteMapping("reservations/deletemyreservation/{reservationId}")
    public ResponseEntity<StandardResponse> deleteMyReservation(
            @PathVariable Integer reservationId
    ){
        var deletedReservationId = reservationService.deleteMyReservation(reservationId);
        return new ResponseEntity<>(
                new StandardResponse(200,"Your reservation details deleted!","Your deleted reservationId : "+deletedReservationId),
                HttpStatus.OK
        );
    }
}
