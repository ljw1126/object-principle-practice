package org.eternity.example;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class ScheduleTest {
    
    @Test
    public void day_of_week_in_month_includes() {
        Schedule schedule = new Schedule(
      "미팅",
             LocalTime.of(13, 0), 
             Duration.ofHours(1), 
             new MonthlyPlan(2, DayOfWeek.MONDAY)
        );

        assertThat(schedule.includes(LocalDate.of(2024, 7, 8))).isTrue();
    }

    @Test
    public void day_of_weeks_includes() {
        Schedule schedule = new Schedule(
                "미팅", LocalTime.of(13, 0), Duration.ofHours(1),
                new WeeklyPlan(Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY)));

        assertThat(schedule.includes(LocalDate.of(2024, 7, 8))).isTrue();
    }
}
