package com.example.newcomin.controller;

import com.example.newcomin.service.FacilityService;
import com.example.newcomin.service.AdminService;
import com.example.newcomin.service.RoomService;
import com.example.newcomin.entity.Facility;
import com.example.newcomin.entity.Admin;
import com.example.newcomin.entity.Room;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/rooms")
public class RoomController {
    private RoomService roomService;
    private FacilityService facilityService;
    private AdminService adminService;

    // 등록
    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        if (room != null && room.getFacilityId() != null
                && room.getRoomName() != null && room.getRoomCapacity() != null) {

            Long facilityId = room.getFacilityId().getFacilityId(); // Facility ID 가져오기
            Facility facility = facilityService.getFacilityById(facilityId); // Facility ID를 매개변수로 사용하여 호출
            Long adminId = room.getAdminId().getAdminId();
            Admin admin = adminService.getAdminById(adminId);

            if (facility != null) {
                Room savedRoom = roomService.createRoom(facility, admin, room.getRoomName(), room.getRoomCapacity());
                if (savedRoom != null) {
                    return new ResponseEntity<>(savedRoom, HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }




    // 조회
    @GetMapping("/{roomId}")
    public ResponseEntity<Room> getRoomById(@PathVariable("roomId") Long roomId){
        Room room = roomService.getRoomById(roomId);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms(){
        List<Room> rooms = roomService.getAllRooms();
        return new ResponseEntity<>(rooms,HttpStatus.OK);
    }

    // 삭제
    @DeleteMapping("/{roomId}")
    public  ResponseEntity<String> deleteRoom(@PathVariable("roomId") Long roomId){
        roomService.deleteRoom(roomId);
        return new ResponseEntity<>("객실 정보를 삭제했습니다.", HttpStatus.OK);
    }

}
