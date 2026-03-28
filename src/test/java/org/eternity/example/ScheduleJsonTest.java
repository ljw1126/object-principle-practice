package org.eternity.example;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import com.fasterxml.jackson.core.JsonProcessingException;

public class ScheduleJsonTest {
    @Test
    public void to_json() throws JsonProcessingException, JSONException {
        Schedule schedule = new Schedule(
            "미팅", 
                   LocalTime.of(13, 0), 
                   Duration.ofHours(1), 
                   2, 
                   DayOfWeek.MONDAY
        );
        ScheduleJson scheduleJson = new ScheduleJson(schedule);

        JSONAssert.assertEquals("""
                {"title":"미팅","from":"13:00","duration":60,"ordinal":2,"dayOfWeek":"MONDAY"}
                """,
                scheduleJson.toJson(),
                JSONCompareMode.LENIENT);
    }
}
