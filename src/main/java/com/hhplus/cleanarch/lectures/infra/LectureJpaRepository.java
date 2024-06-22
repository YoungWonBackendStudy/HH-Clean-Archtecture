package com.hhplus.cleanarch.lectures.infra;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureJpaRepository extends JpaRepository<LectureEntity, Long>{
}
