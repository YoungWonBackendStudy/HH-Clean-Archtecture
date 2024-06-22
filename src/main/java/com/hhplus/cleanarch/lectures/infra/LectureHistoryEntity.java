package com.hhplus.cleanarch.lectures.infra;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LectureHistoryEntity {
    @Id
    long id;
    long userId;
    long applyTimeMillies;
}
