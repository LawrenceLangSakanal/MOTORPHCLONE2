package com.motorph.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Utility methods for date/time conversions used across Swing controls and
 * service-layer operations.
 */
public final class DateUtil {

    private static final DateTimeFormatter DTR_DATE_FMT  = DateTimeFormatter.ofPattern("M/d/yyyy");
    private static final DateTimeFormatter DTR_TIME_FMT  = DateTimeFormatter.ofPattern("h:mm a");
    private static final DateTimeFormatter DISPLAY_DATE  = DateTimeFormatter.ofPattern("MMMM d, yyyy");

    private DateUtil() {}

    /** Convert a {@link java.util.Date} (from JDateChooser) to {@link LocalDate}. */
    public static LocalDate toLocalDate(Date date) {
        if (date == null) return null;
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /** Convert a {@link LocalDate} to {@link java.util.Date} for Swing date pickers. */
    public static Date toDate(LocalDate localDate) {
        if (localDate == null) return null;
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /** Parse a DTR-format date string (e.g. "6/15/2025") to LocalDate. */
    public static LocalDate parseDtrDate(String s) {
        if (s == null || s.isBlank()) return null;
        try { return LocalDate.parse(s.trim(), DTR_DATE_FMT); } catch (Exception e) { return null; }
    }

    /** Parse a 12-hour time string (e.g. "8:00 AM") to LocalTime. */
    public static LocalTime parseDtrTime(String s) {
        if (s == null || s.isBlank()) return null;
        try { return LocalTime.parse(s.trim().toUpperCase(), DTR_TIME_FMT); } catch (Exception e) { return null; }
    }

    /** Format LocalDate as DTR date string. */
    public static String formatDtrDate(LocalDate d) {
        return d == null ? "" : d.format(DTR_DATE_FMT);
    }

    /** Format LocalTime as DTR time string. */
    public static String formatDtrTime(LocalTime t) {
        return t == null ? "" : t.format(DTR_TIME_FMT);
    }

    /** Format LocalDate as a human-readable string (e.g. "June 15, 2025"). */
    public static String formatDisplay(LocalDate d) {
        return d == null ? "" : d.format(DISPLAY_DATE);
    }
}
