package com.example.newcomin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservations")
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @Enumerated(EnumType.STRING)
    @Column (nullable = true)
    private ReservationStatus reservationStatus;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    private LocalDate reservationDate;

    @ElementCollection
    @JoinTable(name = "reservation_companions",
            joinColumns = @JoinColumn(name = "reservationId"))
    @Column(name = "userId")
    private List<Long> companions;

    @ManyToOne
    @JoinColumn(name = "roomId")
    private Room roomId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User userId;
}