package com.hotel.rooms.repository;

import com.hotel.rooms.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room,Integer> {

    @Query(value = "SELECT * FROM room WHERE room_number = ?1",nativeQuery = true)
    Room getRoomByRoomNumber(String roomNumber);

    @Query(value = "SELECT * FROM room WHERE room_id = ?1",nativeQuery = true)
    Room getRoomByRoomId(Integer roomId);

    @Query(value = "SELECT * FROM room WHERE room_type = ?1",nativeQuery = true)
    List<Room> getRoomsByRoomType(String roomType);

    @Query(value = "SELECT COUNT(room_id) AS total_ids FROM room",nativeQuery = true)
    Integer getNoOfRooms();

    @Query(value = "SELECT COUNT(DISTINCT room_type) FROM room",nativeQuery = true)
    Integer getNoOfRoomTypes();
}
