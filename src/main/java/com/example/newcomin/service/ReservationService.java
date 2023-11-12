package com.example.newcomin.service;

import com.example.newcomin.entity.*;
import com.example.newcomin.entity.Reservation;
import com.example.newcomin.entity.ReservationDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationService {
    // 예약 생성
    Reservation createReservation
            (User user, List<Long> companions, Room room, ReservationStatus reservationStatus,
             LocalDateTime startTime, LocalDateTime endTime, LocalDate reservationDate);
    // 전체 예약 조회
    List<ReservationDTO> getAllReservations();
    // 예약 아이디로 조회
    Optional<ReservationDTO> getReservationById(Long reservationId);
    // 회원으로 조회
    List<ReservationDTO> getUserReservations(User user);
    // 강의실로 조회
    List<ReservationDTO> getReservationsByRoom(Room room);
    // 예약 삭제
    void deleteReservation(Long reservationId);
    // 예약 상태 업데이트
    void updateReservationStatus();
}
