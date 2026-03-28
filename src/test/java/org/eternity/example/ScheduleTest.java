package org.eternity.example;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import com.fasterxml.jackson.core.JsonProcessingException;

public class ScheduleTest {
    @Test
    public void includes() {
        Schedule schedule = new Schedule(
      "월간회의",
             LocalTime.of(14, 0), 
             Duration.ofHours(1), 
             2, 
             DayOfWeek.MONDAY
        );
        assertThat(schedule.includes(LocalDate.of(2025, 1, 13))).isTrue();
    }

    @Test
    public void to_json() throws JsonProcessingException, JSONException {
        Schedule schedule = new Schedule(
            "월간회의", 
                   LocalTime.of(14, 0), 
                   Duration.ofHours(1), 
                   2, 
                   DayOfWeek.MONDAY
        );
        JSONAssert.assertEquals("""
                {"title":"월간회의","from":"14:00","duration":60,"ordinal":2,"dayOfWeek":"MONDAY"}
                """,
                schedule.toJson(),
                JSONCompareMode.LENIENT);
    }
}
