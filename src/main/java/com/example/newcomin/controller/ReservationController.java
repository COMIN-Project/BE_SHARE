package com.example.newcomin.controller;

import com.example.newcomin.entity.Reservation;
import com.example.newcomin.entity.ReservationStatus;
import com.example.newcomin.entity.Room;
import com.example.newcomin.entity.User;
import com.example.newcomin.service.UserService;
import com.example.newcomin.service.ReservationService;
import com.example.newcomin.service.RoomService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@RestController
@AllArgsConstructor
@RequestMapping("api/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private RoomService roomService;
    private UserService userService;

    // 등록
    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        if (reservation != null && reservation.getUserId() != null
                && reservation.getCompanions() != null
                && reservation.getRoomId() != null
                && reservation.getStartTime() != null
                && reservation.getEndTime() != null
                && reservation.getReservationDate() != null) {

            Long userId = reservation.getUserId().getUserId();
            List<Long> companions = reservation.getCompanions();

            Long roomId = reservation.getRoomId().getRoomId();
            User user = userService.getUserById(userId);

            List<User> companionUsers = new ArrayList<>();

            for (Long companionId : companions) {
                User companionUser = userService.getUserById(companionId);
                if (companionUser != null) {
                    companionUsers.add(companionUser);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
            Room room = roomService.getRoomById(roomId);
            if (user != null && companionUsers != null && !companionUsers.isEmpty() && room != null) {
                // 예약 상태 판단
                LocalDateTime now = LocalDateTime.now();
                ReservationStatus reservationStatus;
                if (now.isAfter(reservation.getStartTime()) && now.isBefore(reservation.getEndTime())) {
                    reservationStatus = ReservationStatus.IN_USE;
                } else if (now.isBefore(reservation.getStartTime())) {
                    reservationStatus = ReservationStatus.RESERVED;
                } else {
                    reservationStatus = ReservationStatus.AVAILABLE;
                }
                Reservation savedReservation = reservationService.createReservation(
                        user, companions, room, reservationStatus,
                        reservation.getStartTime(), reservation.getEndTime(),
                        reservation.getReservationDate());
                if (savedReservation != null) {
                    return new ResponseEntity<>(savedReservation, HttpStatus.CREATED);
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



    // 예약 아이디로 조회
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Reservation reservation = reservationService.getReservationById(id);
        if (reservation != null) {
            return ResponseEntity.ok(reservation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 모든 예약 조회
    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    // 특정 사용자의 예약 조회
    @GetMapping("user/{userId}")
    public ResponseEntity<List<Reservation>> getUserReservations(@PathVariable Long userId) {
        User user = new User();
        user.setUserId(userId);
        List<Reservation> userReservations = reservationService.getUserReservations(user);
        return ResponseEntity.ok(userReservations);
    }

    // 특정 방의 예약 조회
    @GetMapping("room/{roomId}")
    public ResponseEntity<List<Reservation>> getReservationsByRoom(@PathVariable Long roomId) {
        Room room = new Room();
        room.setRoomId(roomId);
        List<Reservation> roomReservations = reservationService.getReservationsByRoom(room);
        return ResponseEntity.ok(roomReservations);
    }

    // 삭제
    @DeleteMapping("/{reservationId}")
    public ResponseEntity<String> deleteReservation(@PathVariable("reservationId") Long reservationId) {
        reservationService.deleteReservation(reservationId);
        return ResponseEntity.ok("예약 정보를 삭제했습니다.");
    }
}