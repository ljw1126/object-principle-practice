package org.eternity.example;

import java.time.LocalDate;
import java.util.Collection;

public interface TemporalFilter {
    Collection<LocalDate> apply(DateInterval interval);  
} 
