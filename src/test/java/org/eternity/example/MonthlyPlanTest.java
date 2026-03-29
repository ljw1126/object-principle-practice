package org.eternity.example;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class MonthlyPlanTest {

    @Test
    public void boundary_days_test() {
        // 1주차 월요일이 7일인 경우 (예: 2024년 10월 7일)
        MonthlyPlan firstMonday = new MonthlyPlan(1, DayOfWeek.MONDAY);
        assertThat(firstMonday.includes(LocalDate.of(2024, 10, 7))).isTrue();

        // 2주차 월요일이 14일인 경우 (예: 2024년 10월 14일)
        MonthlyPlan secondMonday = new MonthlyPlan(2, DayOfWeek.MONDAY);
        assertThat(secondMonday.includes(LocalDate.of(2024, 10, 14))).isTrue();
    }
}
