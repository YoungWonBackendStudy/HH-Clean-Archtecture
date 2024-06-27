package com.hhplus.cleanarch.lecture.infra.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "lecture_schedule")
public class LectureScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    Long lectureId;
    Long dateMillis;
    Integer maxUserCnt;
    Integer appliedUserCnt;
}
