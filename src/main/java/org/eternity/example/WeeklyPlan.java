package org.eternity.example;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * 매주 특정 요일들에 반복되는 일정을 관리하는 클래스입니다.
 * 예: "매주 월, 수, 금", "매주 주말(토, 일)" 등
 */
public class WeeklyPlan implements RecurringPlan {

    private Set<DayOfWeek> dayOfWeeks;

    public WeeklyPlan(Set<DayOfWeek> dayOfWeeks) {
        this.dayOfWeeks = new HashSet<>(dayOfWeeks);
    }

    @Override
    public boolean includes(LocalDate day) {
         return dayOfWeeks.contains(day.getDayOfWeek()); // ordinal == null, 데일리 스크럼 일정 확인 로직 실행
    }

    @Override
    public RecurringPlan reschedule(LocalDate day) {
        var copy = new HashSet<>(dayOfWeeks);
        copy.add(day.getDayOfWeek());
        return new WeeklyPlan(copy);
    }
    
}
