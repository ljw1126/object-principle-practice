package org.eternity.example;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class MonthlyPlan implements RecurringPlan {
    private static final int DAYS_IN_WEEK = 7;

    private Integer ordinal; // 해당 월의 n번째 요일 (매월 특정 주차의 특정 요일에 반복되는 일정 관리하기 하기 위한 값)
    private DayOfWeek dayOfWeek; // MONDAY ~ SUNDAY
 
    public MonthlyPlan(Integer ordinal, DayOfWeek dayOfWeek) {
        this.ordinal = ordinal;
        this.dayOfWeek = dayOfWeek;
    }


    /**
     * 지정된 날짜가 해당 월의 n번째(ordinal) 특정 요일(dayOfWeek)에 해당하는지 확인합니다.
     * 예: 2번째 월요일
     */
    @Override
    public boolean includes(LocalDate day) {
          if (!day.getDayOfWeek().equals(dayOfWeek)) {
            return false;
        }

        /**
         * n번째 주차 계산 시, 7의 배수(7, 14, 21, 28일)가 다음 주로 넘어가는 오류를 방지하기 위해 
         * (day - 1)을 수행하여 0-based 인덱스로 변환 후 계산합니다.
         * 예: 7일의 경우 (7-1)/7 + 1 = 1주차 (정상), 7/7 + 1 = 2주차 (오류)
         */
        return ((day.getDayOfMonth() - 1) / DAYS_IN_WEEK) + 1 == ordinal;
    }
}