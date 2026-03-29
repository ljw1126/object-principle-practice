package org.eternity.example;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class MonthlyPlanTest {

    @ParameterizedTest(name = "{0}주차 {1}가 {2}일 때 includes는 true를 반환해야 한다")
    @CsvSource({
        "1, MONDAY, 2024-10-07", // 1주차 월요일이 7일인 경우
        "2, MONDAY, 2024-10-14", // 2주차 월요일이 14일인 경우
        "3, MONDAY, 2024-10-21", // 3주차 월요일이 21일인 경우
        "4, MONDAY, 2024-10-28", // 4주차 월요일이 28일인 경우
        "1, TUESDAY, 2024-10-01" // 1주차 화요일이 1일인 경우
    })
    public void boundary_days_parameterized_test(int ordinal, DayOfWeek dayOfWeek, String date) {
        MonthlyPlan plan = new MonthlyPlan(ordinal, dayOfWeek);
        assertThat(plan.includes(LocalDate.parse(date))).isTrue();
    }
}
