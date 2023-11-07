package com.example.newcomin.service.impl;

import com.example.newcomin.entity.Room;
import com.example.newcomin.entity.Admin;
import com.example.newcomin.entity.Facility;
import com.example.newcomin.repository.AdminRepository;
import com.example.newcomin.repository.RoomRepository;
import com.example.newcomin.repository.FacilityRepository;
import com.example.newcomin.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final FacilityRepository facilityRepository; // FacilityRepository 주입 추가
    private final AdminRepository adminRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository,
                           FacilityRepository facilityRepository,
                           AdminRepository adminRepository) {
        this.roomRepository = roomRepository;
        this.facilityRepository = facilityRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public Room createRoom(Facility facility, Admin admin, String roomName, String roomCapacity) {
        if (facility != null && admin != null && roomName != null && roomCapacity != null) {

            Room room = new Room();
            room.setFacilityId(facility);
            room.setAdminId(admin);
            room.setRoomName(roomName);
            room.setRoomCapacity(roomCapacity);

            return roomRepository.save(room);
        } else {
            throw new IllegalArgumentException("유효하지 않은 객실 정보입니다.");
        }
    }

    @Override
    public Room getRoomById(Long roomId){
        Optional<Room> optionalRoom = roomRepository.findById(roomId);
        return optionalRoom.get();
    }
    @Override
    public List<Room> getAllRooms(){
        return roomRepository.findAll();
    }

    @Override
    public void deleteRoom(Long roomId){
        roomRepository.deleteById(roomId);
    }
}
