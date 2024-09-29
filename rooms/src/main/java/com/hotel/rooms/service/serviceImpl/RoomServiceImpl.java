package com.hotel.rooms.service.serviceImpl;

import com.hotel.rooms.dto.RoomDTO;
import com.hotel.rooms.model.Room;
import com.hotel.rooms.model.RoomType;
import com.hotel.rooms.repository.RoomRepository;
import com.hotel.rooms.service.RoomService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;

    @Override
    public RoomDTO saveRoom(RoomDTO roomDTO) {
        if (roomDTO.getRoomType().equals(RoomType.SINGLE)){
            roomDTO.setRoomCapacity(1);
            roomDTO.setRoomPrice(BigDecimal.valueOf(2500));
        } else if (roomDTO.getRoomType().equals(RoomType.COUPLE)) {
            roomDTO.setRoomCapacity(2);
            roomDTO.setRoomPrice(BigDecimal.valueOf(3500));
        } else if (roomDTO.getRoomType().equals(RoomType.FAMILY)) {
            roomDTO.setRoomCapacity(4);
            roomDTO.setRoomPrice(BigDecimal.valueOf(6500));
        } else if (roomDTO.getRoomType().equals(RoomType.SUITE)) {
            roomDTO.setRoomCapacity(8);
            roomDTO.setRoomPrice(BigDecimal.valueOf(9500));
        }
        var savedRoom = roomRepository.save(modelMapper.map(roomDTO,Room.class));
        roomDTO.setRoomId(savedRoom.getRoomId());
        return roomDTO;

    }

    @Override
    public List<RoomDTO> getAllRooms() {
        List<Room> allRooms = roomRepository.findAll();
        return modelMapper.map(allRooms, new TypeToken<List<RoomDTO>>(){}.getType());
    }

    @Override
    public RoomDTO getRoom(String roomNumber) {
        Room room = roomRepository.getRoomByRoomNumber(roomNumber);
        return modelMapper.map(room,new TypeToken<RoomDTO>(){}.getType());
    }

    @Override
    public RoomDTO updateRoom(RoomDTO roomDTO) {
        var roomId = roomDTO.getRoomId();
        var isRoomExists = roomRepository.getRoomByRoomId(roomId);
        if (isRoomExists != null){
            if (roomDTO.getRoomType().equals(RoomType.SINGLE)){
                roomDTO.setRoomCapacity(1);
                roomDTO.setRoomPrice(BigDecimal.valueOf(2500));
            } else if (roomDTO.getRoomType().equals(RoomType.COUPLE)) {
                roomDTO.setRoomCapacity(2);
                roomDTO.setRoomPrice(BigDecimal.valueOf(3500));
            } else if (roomDTO.getRoomType().equals(RoomType.FAMILY)) {
                roomDTO.setRoomCapacity(4);
                roomDTO.setRoomPrice(BigDecimal.valueOf(6500));
            } else if (roomDTO.getRoomType().equals(RoomType.SUITE)) {
                roomDTO.setRoomCapacity(8);
                roomDTO.setRoomPrice(BigDecimal.valueOf(9500));
            }
            roomRepository.save(modelMapper.map(roomDTO,Room.class));
            return roomDTO;
        }else {
            return null;
        }

    }

    @Override
    public Integer deleteRoom(Integer roomId) {
        roomRepository.deleteById(roomId);
        return roomId;
    }

    @Override
    public List<String> getRoomNumbersByRoomType(String roomType) {

        List<Room> roomList = roomRepository.getRoomsByRoomType(roomType);

        if (roomList == null) {
            throw new RuntimeException("No rooms found for type: " + roomType);
        }
        return roomList.stream().map(Room::getRoomNumber).collect(Collectors.toList());
    }

    @Override
    public Integer noOfRooms(){
        return roomRepository.getNoOfRooms();
    }

    @Override
    public Integer noOfRoomTypes(){
        return roomRepository.getNoOfRoomTypes();
    }

}
