package com.example.newcomin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {

    private Long reservationId;
    private ReservationStatus reservationStatus;
    private LocalDate reservationDate;

    // 시작시간과 끝시간의 자료형을 string으로 바꾸기 위해 DTO 클래스 생성
    private String startTime;
    private String endTime;

    private List<Long> companions;
    private Room roomId;
    private User userId;
}
