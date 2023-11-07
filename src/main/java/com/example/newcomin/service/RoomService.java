package com.example.newcomin.service;

import com.example.newcomin.entity.Admin;
import com.example.newcomin.entity.Facility;
import com.example.newcomin.entity.Room;
import java.util.List;


public interface RoomService {
    Room createRoom(Facility facility, Admin admin, String roomName, String roomCapacity);

    Room getRoomById(Long roomId);
    List<Room> getAllRooms();

    void deleteRoom(Long roomId);
}
