package org.eternity.example;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
/**
 * 기능1. 월간회의
 * - ex. 매주 두번째 주 월요일 오후 2시부터 1시간
 * 
 * 기능2. 데일리 스크럼
 * - ex. 매주 월요일, 화요일 오전 9시부터 15분간
 */
public class Schedule {
     private static final int DAYS_IN_WEEK = 7;

    private String title;
    private LocalTime from;
    private Duration duration;

    private Integer ordinal; // 해당 월의 n번째 요일 (매월 특정 주차의 특정 요일에 반복되는 일정 관리하기 하기 위한 값)
    private DayOfWeek dayOfWeek; // MONDAY ~ SUNDAY
 
    private Set<DayOfWeek> dayOfWeeks = new HashSet<>();

    public Schedule(String title, LocalTime from, Duration duration, Integer ordinal, DayOfWeek dayOfWeek) {
        this.title = title;
        this.from = from;
        this.duration = duration;
        this.ordinal = ordinal;
        this.dayOfWeek = dayOfWeek;
    }

    public Schedule(String title, LocalTime from, Duration duration, Set<DayOfWeek> dayOfWeeks) {
        this.title = title;
        this.from = from;
        this.duration = duration;
        this.dayOfWeeks = dayOfWeeks;
    }

    /**
     * 지정된 날짜가 해당 월의 n번째(ordinal) 특정 요일(dayOfWeek)에 해당하는지 확인합니다.
     * 예: 2번째 월요일
     */
    public boolean includes(LocalDate day) {
        if(ordinal != null) {
            if (!day.getDayOfWeek().equals(dayOfWeek)) {
                return false;
            }

            return (day.getDayOfMonth() / DAYS_IN_WEEK) + 1 == ordinal;
        }

        return dayOfWeeks.contains(day.getDayOfWeek()); // ordinal == null, 데일리 스크럼 일정 확인 로직 실행
    }
}
