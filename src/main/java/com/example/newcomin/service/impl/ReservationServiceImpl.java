package com.example.newcomin.service.impl;

import com.example.newcomin.entity.*;
import com.example.newcomin.entity.User;
import com.example.newcomin.entity.Room;
import com.example.newcomin.repository.ReservationRepository;
import com.example.newcomin.repository.RoomRepository;
import com.example.newcomin.repository.UserRepository;
import com.example.newcomin.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;


@EnableScheduling
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;


    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository,
                                  UserRepository userRepository,
                                  RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.roomRepository =roomRepository;
    }

    @Override
    public Reservation createReservation(User user, List<Long> companions, Room room, ReservationStatus reservationStatus,
                                         LocalDateTime startTime, LocalDateTime endTime, LocalDate reservationDate) {
        if (user != null && companions != null && !companions.isEmpty() && room != null && startTime != null
                && endTime != null && reservationDate != null) {
            Reservation reservation = new Reservation();
            reservation.setUserId(user);
            reservation.setCompanions(companions); // companions 설정
            reservation.setRoomId(room);
            reservation.setReservationStatus(reservationStatus);
            reservation.setStartTime(startTime);
            reservation.setEndTime(endTime);
            reservation.setReservationDate(reservationDate);
            return reservationRepository.save(reservation);
        } else {
            throw new IllegalArgumentException("유효하지 않은 예약 정보입니다.");
        }
    }

    @Override
    public Reservation getReservationById(Long reservationId) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);
        return optionalReservation.get();
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public void deleteReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    @Override
    public List<Reservation> getUserReservations(User user) {
        return reservationRepository.findByUserId(user);
    }

    @Override
    public List<Reservation> getReservationsByRoom(Room room) {
        return reservationRepository.findByRoomId(room);
    }

    @Override
    @Scheduled(cron = "0 * * * * *")
    public void updateReservationStatus() {
        List<Reservation> reservations = getAllReservations();
        LocalDateTime now = LocalDateTime.now();

        for (Reservation reservation : reservations) {
            if (now.isAfter(reservation.getStartTime()) && now.isBefore(reservation.getEndTime())) {
                reservation.setReservationStatus(ReservationStatus.IN_USE);
            } else if (now.isBefore(reservation.getStartTime())) {
                reservation.setReservationStatus(ReservationStatus.RESERVED);
            } else if (now.isAfter(reservation.getEndTime())) {
                reservation.setReservationStatus(ReservationStatus.AVAILABLE);
            }
        }
        reservationRepository.saveAll(reservations); // 변경된 예약 상태를 저장
    }
}

