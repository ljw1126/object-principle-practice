package org.eternity.example;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
/**
 * 기능1. 월간회의
 * - ex. 매주 두번째 주 월요일 오후 2시부터 1시간
 * 
 * 기능2. 데일리 스크럼
 * - ex. 매주 월요일, 화요일 오전 9시부터 15분간
 */
public class Schedule {
    private String title;
    private LocalTime from;
    private Duration duration;
    private RecurringPlan plan;

    public Schedule(String title, LocalTime from, Duration duration, RecurringPlan plan) {
        this.title = title;
        this.from = from;
        this.duration = duration;
        this.plan = plan;
    }

    public boolean includes(LocalDate day) {
        return plan.includes(day);
    }

    public void reschedule(LocalDate day) {
        this.plan = plan.reschedule(day);
    }
}
