package com.hhplus.cleanarch.lecture.infra.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(
    name = "lecture_schedule_history"
    ,uniqueConstraints = {
    @UniqueConstraint(name = "lectureScheduleUserUnique", columnNames = {"lectureScheduleId", "userId"})
})
public class LectureSchHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    
    @Column(nullable = false)
    Long lectureScheduleId;

    @Column(nullable = false)
    Long userId;
    Long updateMillis;
}
