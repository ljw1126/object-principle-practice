package org.eternity.example;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class Schedule {
     private static final int DAYS_IN_WEEK = 7;

    private String title;
    private LocalTime from;
    private Duration duration;
    
    private Integer ordinal; // 해당 월의 n번째 요일 (매월 특정 주차의 특정 요일에 반복되는 일정 관리하기 하기 위한 값)
    private DayOfWeek dayOfWeek; // MONDAY ~ SUNDAY
 
    public Schedule(String title, LocalTime from, Duration duration, Integer ordinal, DayOfWeek dayOfWeek) {
        this.title = title;
        this.from = from;
        this.duration = duration;
        this.ordinal = ordinal;
        this.dayOfWeek = dayOfWeek;
    }

    // 일정 확인 방식이 변경될 때 수정
    public boolean includes(LocalDate day) {
        if (!day.getDayOfWeek().equals(dayOfWeek)) {
            return false;
        }

        return (day.getDayOfMonth() / DAYS_IN_WEEK) + 1 == ordinal;
    }
}
