package com.example.newcomin.service.impl;

import com.example.newcomin.entity.*;
import com.example.newcomin.entity.User;
import com.example.newcomin.entity.Room;
import com.example.newcomin.entity.Reservation;
import com.example.newcomin.entity.ReservationDTO;
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
import java.util.stream.Collectors;


@EnableScheduling
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    private ReservationDTO convertToDTO(Reservation reservation) {
        ReservationDTO dto = new ReservationDTO();

        // Reservation 객체의 필드 값을 ReservationDTO 객체로 복사
        dto.setReservationId(reservation.getReservationId());
        dto.setReservationStatus(reservation.getReservationStatus());
        dto.setReservationDate(reservation.getReservationDate());

        // startTime 추출 (localdatetime에서 string으로 변환하는 과정)
        String startTime = reservation.getStartTime().toString();
        startTime = startTime.substring(11, 16); // "YYYY-MM-DDTHH:MM:SS"에서 12-16자리만 추출
        dto.setStartTime(startTime);

        // endTime 추출 (localdatetime에서 string으로 변환하는 과정)
        String endTime = reservation.getEndTime().toString();
        endTime = endTime.substring(11, 16); // "YYYY-MM-DDTHH:MM:SS"에서 12-16자리만 추출
        dto.setEndTime(endTime);

        dto.setCompanions(reservation.getCompanions());
        dto.setRoomId(reservation.getRoomId());
        dto.setUserId(reservation.getUserId());

        return dto;
    }

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
        if (user != null && room != null && startTime != null
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
    public List<ReservationDTO> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ReservationDTO> getReservationById(Long reservationId) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        return reservation.map(this::convertToDTO);
    }

    @Override
    public void deleteReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    @Override
    public List<ReservationDTO> getUserReservations(User user) {
        List<Reservation> reservation = reservationRepository.findByUserId(user);
        return reservation.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationDTO> getReservationsByRoom(Room room) {
        List<Reservation> reservation =  reservationRepository.findByRoomId(room);
        return reservation.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

    }

    @Override
    @Scheduled(cron = "0 * * * * *")
    public void updateReservationStatus() {
        List<Reservation> reservations = reservationRepository.findAll();
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
