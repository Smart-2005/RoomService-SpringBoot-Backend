package com.hotel.rooms.service;

import com.hotel.rooms.RoomsApplication;
import com.hotel.rooms.dto.RoomDTO;
import com.hotel.rooms.model.RoomType;

import java.util.List;

public interface RoomService {

    RoomDTO saveRoom(RoomDTO roomDTO);
    List<RoomDTO> getAllRooms();
    RoomDTO getRoom(String roomNumber);
    RoomDTO updateRoom(RoomDTO roomDTO);
    Integer deleteRoom(Integer roomId);
    List<String> getRoomNumbersByRoomType(String roomType);
    Integer noOfRooms();
    Integer noOfRoomTypes();

}
