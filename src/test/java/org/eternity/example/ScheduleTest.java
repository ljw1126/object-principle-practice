package org.eternity.example;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

public class ScheduleTest {
    @Test
    public void includes() {
        Schedule schedule = new Schedule(
      "미팅",
             LocalTime.of(13, 0), 
             Duration.ofHours(1), 
             2, 
             DayOfWeek.MONDAY
        );
        assertThat(schedule.includes(LocalDate.of(2024, 7, 8))).isTrue();
    }
}
