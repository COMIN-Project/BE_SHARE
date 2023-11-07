package com.example.newcomin.entity;

import jakarta.persistence.*;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rooms")
public class Room implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @Column(nullable = false, unique = true)
    private String roomName;

    @Column(nullable = false)
    private String roomCapacity;

    @ManyToOne
    @JoinColumn(name = "adminId")
    private Admin adminId;

    @ManyToOne
    @JoinColumn(name = "facilityId")
    private Facility facilityId;
}