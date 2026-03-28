package org.eternity.example;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Set;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import com.fasterxml.jackson.core.JsonProcessingException;

public class ScheduleJsonTest {
    @Test
    public void to_json_day_of_week_in_month() throws JsonProcessingException, JSONException {
        Schedule schedule = new Schedule(
            "미팅", 
                   LocalTime.of(13, 0), 
                   Duration.ofHours(1), 
                   new MonthlyPlan(2, DayOfWeek.MONDAY)
        );
        ScheduleJson scheduleJson = new ScheduleJson(schedule);

        JSONAssert.assertEquals("""
                {"title":"미팅","from":"13:00","duration":60, "plan":{"ordinal":2,"dayOfWeek":"MONDAY"}}
                """,
                scheduleJson.toJson(),
                JSONCompareMode.LENIENT);
    }

    @Test
    public void to_json_day_of_weeks() throws JsonProcessingException, JSONException {
        Schedule schedule = new Schedule(
            "미팅", 
                   LocalTime.of(13, 0), 
                   Duration.ofHours(1), 
                   new WeeklyPlan(Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY))
        );
        ScheduleJson scheduleJson = new ScheduleJson(schedule);

        JSONAssert.assertEquals("""
                {"title":"미팅","from":"13:00","duration":60, "plan":{"dayOfWeeks":["MONDAY","TUESDAY"]}}
                """,
                scheduleJson.toJson(),
                JSONCompareMode.LENIENT);
    }
}
