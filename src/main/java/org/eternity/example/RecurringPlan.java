package org.eternity.example;

import java.time.LocalDate;

public interface RecurringPlan {
    boolean includes(LocalDate day);
    RecurringPlan reschedule(LocalDate day);
}
