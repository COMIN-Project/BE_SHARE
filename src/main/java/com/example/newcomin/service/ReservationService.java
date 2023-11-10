package com.example.newcomin.service;

import com.example.newcomin.entity.Reservation;
import com.example.newcomin.entity.ReservationStatus;
import com.example.newcomin.entity.Room;
import com.example.newcomin.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservationService {

    Reservation createReservation
            (User user, List<Long> companions, Room room, ReservationStatus reservationStatus,
             LocalDateTime startTime, LocalDateTime endTime, LocalDate reservationDate);

    Reservation getReservationById(Long reservationId);
    List<Reservation> getAllReservations();
    void deleteReservation(Long reservationId);
    List<Reservation> getUserReservations(User user);
    List<Reservation> getReservationsByRoom(Room room);
    void updateReservationStatus();
}
