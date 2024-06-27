package com.hhplus.cleanarch.lecture.domain;

import java.util.LinkedList;
import java.util.List;

import com.hhplus.cleanarch.exception.InvalidApplyException;

import lombok.Getter;

@Getter
public class LectureScheduleWithHistory extends LectureSchedule {
    List<LectureScheduleHistory> applyHistory;

    protected LectureScheduleWithHistory(long id, long lectureId, long dateMillis, int maxUser, int appliedUserCnt, List<LectureScheduleHistory> applyHistory) {
        super(id, lectureId, dateMillis, maxUser, maxUser);
        this.applyHistory = applyHistory;
    }

    public LectureScheduleHistory apply(long userId) throws InvalidApplyException {
        if(applyHistory == null) 
            applyHistory = new LinkedList<>();

        if(maxUser <= applyHistory.size())
            throw new InvalidApplyException("정원을 초과했습니다.");

        if(this.hasUserApplied(userId))
            throw new InvalidApplyException("이미 지원한 사용자입니다.");
        
        super.appliedUserCnt += 1;
        return new LectureScheduleHistory(this.id, userId, System.currentTimeMillis());
    }

    public boolean hasUserApplied(long userId) {
        for (LectureScheduleHistory lectureHis : applyHistory) {
            if(lectureHis.getUserId() == userId)
                return true;
        }

        return false;
    }
}
