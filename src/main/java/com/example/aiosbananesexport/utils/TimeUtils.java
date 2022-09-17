package com.example.aiosbananesexport.utils;

import java.time.Clock;
import java.time.ZonedDateTime;

public class TimeUtils {
    private static Clock clock = Clock.systemDefaultZone();

    public static ZonedDateTime now() {
        return ZonedDateTime.now(clock);
    }

    public static void useFixedClockAt(ZonedDateTime zonedDateTime) {
        clock = Clock.fixed(zonedDateTime.toInstant(), zonedDateTime.getZone());
    }

    public static void useSystemDefaultZoneClock(){
        clock = Clock.systemDefaultZone();
    }

    private static Clock getClock() {
        return clock ;
    }
}
