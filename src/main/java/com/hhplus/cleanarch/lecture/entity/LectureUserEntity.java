package com.hhplus.cleanarch.lecture.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(uniqueConstraints = {
    @UniqueConstraint(name = "lectureUserUnique", columnNames = {"lectureId", "userId"})
})
public class LectureUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column
    long lectureId;

    @Column
    long userId;
}
