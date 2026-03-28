package org.eternity.example;

import java.time.Duration;
import java.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ScheduleJson {
    private final Schedule schedule;
    private final ObjectMapper mapper;

    public ScheduleJson(Schedule schedule) {
        this.schedule = schedule;
        this.mapper = initializeMapper();
    }

    public ObjectMapper initializeMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configOverride(Duration.class)
            .setFormat(JsonFormat.Value.forPattern("MINUTES"));
        objectMapper.configOverride(LocalTime.class)
            .setFormat(JsonFormat.Value.forPattern("HH:mm"));
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        return objectMapper;
    }

    public String toJson() throws JsonProcessingException {
        return mapper.writeValueAsString(schedule);
    }
}
