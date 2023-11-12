package com.example.newcomin.repository;

import com.example.newcomin.entity.Reservation;
import com.example.newcomin.entity.Room;
import com.example.newcomin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserId(User user);
    List<Reservation> findByRoomId(Room room);
}