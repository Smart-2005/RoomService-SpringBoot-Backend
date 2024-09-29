package com.hotel.rooms.controller;

import com.hotel.rooms.dto.RoomDTO;
import com.hotel.rooms.model.RoomType;
import com.hotel.rooms.service.RoomService;
import com.hotel.rooms.util.StandardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping("rooms/saveroom")
    public ResponseEntity<StandardResponse> saveRoom(
            @RequestBody RoomDTO roomDTO
    ){
        var savedRoomDetails = roomService.saveRoom(roomDTO);
            return new ResponseEntity<>(
                    new StandardResponse(201,"Room details saved!", savedRoomDetails),
                    HttpStatus.CREATED
            );
    }

    @GetMapping("rooms/allrooms")
    public ResponseEntity<StandardResponse> getAllRooms(){
        var allRoomsDetails = roomService.getAllRooms();
        return new ResponseEntity<>(
                new StandardResponse(302,"All rooms details here!", allRoomsDetails),
                HttpStatus.OK
        );
    }

    @GetMapping("rooms/{roomNumber}")
    public RoomDTO getRoom(
            @PathVariable String roomNumber
    ){
        return roomService.getRoom(roomNumber);
    }

    @GetMapping("rooms")
    public ResponseEntity<List<String>> getRoomNumbersByRoomType(
            @RequestParam String roomType
    ) {
        List<String> roomNumbers = roomService.getRoomNumbersByRoomType(roomType);
        return ResponseEntity.ok(roomNumbers);
    }

    @PutMapping("rooms/updateroom")
    public ResponseEntity<StandardResponse> updateRoom(
            @RequestBody RoomDTO roomDTO
    ){
        var updatedRoomDetails = roomService.updateRoom(roomDTO);
        return new ResponseEntity<>(
                new StandardResponse(200,"Room details updated!", updatedRoomDetails),
                HttpStatus.OK
        );
    }

    @DeleteMapping("rooms/deleteroom/{roomId}")
    public ResponseEntity<StandardResponse> deleteRoom(
            @PathVariable Integer roomId
    ){
        var deletedRoomId = roomService.deleteRoom(roomId);
        return new ResponseEntity<>(
                new StandardResponse(200,"Room details deleted!", "Your deleted roomId : "+deletedRoomId),
                HttpStatus.OK
        );
    }

    @GetMapping("rooms/count")
    public Integer noOfRooms(){
        return roomService.noOfRooms();
    }

    @GetMapping("rooms/roomtypes/count")
    public Integer noOfRoomTypes(){
        return roomService.noOfRoomTypes();
    }
}
