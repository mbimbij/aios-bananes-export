package com.example.aiosbananesexport.utils;

import java.time.Clock;
import java.time.ZonedDateTime;

public class TimestampGenerator {
    private static Clock clock = Clock.systemDefaultZone();

    public static ZonedDateTime now() {
        return ZonedDateTime.now(clock);
    }

    public static void useFixedClockAt(ZonedDateTime zonedDateTime) {
        clock = Clock.fixed(zonedDateTime.toInstant(), zonedDateTime.getZone());
    }
}
