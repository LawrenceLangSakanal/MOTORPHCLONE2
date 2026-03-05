package com.motorph.service;

import com.motorph.model.AttendanceRecord;
import com.motorph.repo.AttendanceRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Business logic for clock-in/clock-out and attendance queries.
 */
public class AttendanceService {

    private final AttendanceRepository repo;

    public AttendanceService(AttendanceRepository repo) {
        this.repo = repo;
    }

    /**
     * Record a clock-in for the given employee on today's date.
     * Throws {@link IllegalStateException} if already clocked in today.
     */
    public AttendanceRecord clockIn(String empId) {
        LocalDate today = LocalDate.now();
        AttendanceRecord existing = repo.findByEmpIdAndDate(empId, today);
        if (existing != null && existing.getClockIn() != null) {
            throw new IllegalStateException("Already clocked in today at " + existing.getClockIn());
        }
        return repo.clockIn(empId, today, LocalTime.now());
    }

    /**
     * Record a clock-out for the given employee on today's date.
     * Throws {@link IllegalStateException} if not yet clocked in.
     */
    public AttendanceRecord clockOut(String empId) {
        LocalDate today = LocalDate.now();
        AttendanceRecord rec = repo.findByEmpIdAndDate(empId, today);
        if (rec == null || rec.getClockIn() == null) {
            throw new IllegalStateException("You must clock in first.");
        }
        return repo.clockOut(empId, today, LocalTime.now());
    }

    /** Check if the employee has already clocked in today. */
    public boolean hasClockInToday(String empId) {
        AttendanceRecord rec = repo.findByEmpIdAndDate(empId, LocalDate.now());
        return rec != null && rec.getClockIn() != null;
    }

    /** Retrieve all attendance records for an employee. */
    public List<AttendanceRecord> getRecords(String empId) {
        return repo.findByEmpId(empId);
    }

    /** Retrieve records for an employee within a date range. */
    public List<AttendanceRecord> getRecords(String empId, LocalDate from, LocalDate to) {
        return repo.findByEmpIdAndDateRange(empId, from, to);
    }

    /** Retrieve today's record for an employee, or null if none. */
    public AttendanceRecord getTodayRecord(String empId) {
        return repo.findByEmpIdAndDate(empId, LocalDate.now());
    }
}
