package com.motorph.model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a single clock-in / clock-out record for one employee on one day.
 */
public class AttendanceRecord {

    private final String empId;
    private final LocalDate date;
    private LocalTime clockIn;
    private LocalTime clockOut;

    public AttendanceRecord(String empId, LocalDate date, LocalTime clockIn, LocalTime clockOut) {
        this.empId    = empId;
        this.date     = date;
        this.clockIn  = clockIn;
        this.clockOut = clockOut;
    }

    public String    getEmpId()    { return empId; }
    public LocalDate getDate()     { return date; }
    public LocalTime getClockIn()  { return clockIn; }
    public LocalTime getClockOut() { return clockOut; }

    public void setClockIn(LocalTime clockIn)   { this.clockIn  = clockIn; }
    public void setClockOut(LocalTime clockOut) { this.clockOut = clockOut; }

    /**
     * Computed work duration in decimal hours, or 0 if either time is missing.
     */
    public double computeHours() {
        if (clockIn == null || clockOut == null) return 0.0;
        long minutes = java.time.Duration.between(clockIn, clockOut).toMinutes();
        if (minutes < 0) minutes += 24 * 60; // overnight
        return minutes / 60.0;
    }

    @Override
    public String toString() {
        return empId + " | " + date + " | in=" + clockIn + " out=" + clockOut;
    }
}
