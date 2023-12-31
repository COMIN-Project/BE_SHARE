package com.example.newcomin.service;

import com.example.newcomin.entity.Admin;
import com.example.newcomin.entity.Facility;
import com.example.newcomin.entity.Room;
import java.util.List;


public interface RoomService {
    Room createRoom(Facility facility, Admin admin, String roomName, Integer roomCapacity);

    Room getRoomById(Long roomId);

    List<Room> getAllRooms();

    Room updateRoom(Room room);

    void deleteRoom(Long roomId);
}
