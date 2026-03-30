package org.eternity.example;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class WeeklyPlanTest {

    @Test
    public void includes() {
        WeeklyPlan plan = new WeeklyPlan(Set.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY));

        assertThat(plan.includes(LocalDate.of(2024, 7, 8))).isTrue();  // Monday
        assertThat(plan.includes(LocalDate.of(2024, 7, 10))).isTrue(); // Wednesday
        assertThat(plan.includes(LocalDate.of(2024, 7, 9))).isFalse(); // Tuesday
    }

    @Test
    public void reschedule() {
        WeeklyPlan plan = new WeeklyPlan(Set.of(DayOfWeek.MONDAY));
        
        // 화요일로 reschedule
        WeeklyPlan newPlan = (WeeklyPlan) plan.reschedule(LocalDate.of(2024, 7, 9));

        // 기존 플랜은 변함없어야 함 (불변성)
        assertThat(plan.includes(LocalDate.of(2024, 7, 9))).isFalse();
        
        // 새 플랜은 월요일과 화요일을 모두 포함해야 함 (WeeklyPlan의 비즈니스 로직)
        assertThat(newPlan.includes(LocalDate.of(2024, 7, 8))).isTrue();
        assertThat(newPlan.includes(LocalDate.of(2024, 7, 9))).isTrue();
    }
}
