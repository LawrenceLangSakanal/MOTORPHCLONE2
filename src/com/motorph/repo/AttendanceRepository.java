package com.motorph.repo;

import com.motorph.model.AttendanceRecord;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * In-memory attendance repository.  Existing CSV DTR records can be read via
 * {@link #loadFromCsv(String, String)} and new records added at runtime are
 * kept in memory.  Supports date-range filtering.
 */
public class AttendanceRepository {

    private static AttendanceRepository instance;

    private final List<AttendanceRecord> records = new ArrayList<>();

    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("h:mm a");
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("M/d/yyyy");

    private AttendanceRepository() {}

    public static synchronized AttendanceRepository getInstance() {
        if (instance == null) instance = new AttendanceRepository();
        return instance;
    }

    // ── Write ─────────────────────────────────────────────────────────────────

    /** Add or update a record (matched by empId + date). */
    public void save(AttendanceRecord record) {
        for (int i = 0; i < records.size(); i++) {
            AttendanceRecord r = records.get(i);
            if (r.getEmpId().equals(record.getEmpId()) && r.getDate().equals(record.getDate())) {
                records.set(i, record);
                return;
            }
        }
        records.add(record);
    }

    /**
     * Record clock-in for an employee on today's date.
     * @return the newly created (or updated) record
     */
    public AttendanceRecord clockIn(String empId, LocalDate date, LocalTime clockIn) {
        AttendanceRecord rec = findByEmpIdAndDate(empId, date);
        if (rec == null) {
            rec = new AttendanceRecord(empId, date, clockIn, null);
            records.add(rec);
        } else {
            rec.setClockIn(clockIn);
        }
        return rec;
    }

    /**
     * Record clock-out for an employee on a given date.
     * @return the updated record, or null if no clock-in exists
     */
    public AttendanceRecord clockOut(String empId, LocalDate date, LocalTime clockOut) {
        AttendanceRecord rec = findByEmpIdAndDate(empId, date);
        if (rec != null) {
            rec.setClockOut(clockOut);
        }
        return rec;
    }

    // ── Read ──────────────────────────────────────────────────────────────────

    /** All records for one employee. */
    public List<AttendanceRecord> findByEmpId(String empId) {
        return records.stream()
                .filter(r -> r.getEmpId().equals(empId))
                .collect(Collectors.toList());
    }

    /** Records for one employee within an inclusive date range. */
    public List<AttendanceRecord> findByEmpIdAndDateRange(String empId, LocalDate from, LocalDate to) {
        return records.stream()
                .filter(r -> r.getEmpId().equals(empId)
                        && !r.getDate().isBefore(from)
                        && !r.getDate().isAfter(to))
                .collect(Collectors.toList());
    }

    /** Find a single record by employee + date, or null. */
    public AttendanceRecord findByEmpIdAndDate(String empId, LocalDate date) {
        return records.stream()
                .filter(r -> r.getEmpId().equals(empId) && r.getDate().equals(date))
                .findFirst()
                .orElse(null);
    }

    /**
     * Load existing CSV DTR records for an employee into this repository.
     * Expected CSV columns: EmpID, LogDate (M/d/yyyy), ClockIn (h:mm a), ClockOut (h:mm a), Duration
     *
     * @param empId employee identifier (used to locate the file)
     * @param csvContent raw text content of the DTR file (read by caller)
     */
    public void loadFromCsvContent(String empId, String csvContent) {
        if (csvContent == null || csvContent.isBlank()) return;
        String[] lines = csvContent.split("\\r?\\n");
        for (String line : lines) {
            if (line.isBlank() || line.startsWith("EmpID")) continue;
            String[] parts = line.split(",");
            if (parts.length < 4) continue;
            try {
                LocalDate date    = LocalDate.parse(parts[1].trim(), DATE_FMT);
                LocalTime clockIn = parts[2].trim().isEmpty() ? null
                        : LocalTime.parse(parts[2].trim().toUpperCase(), TIME_FMT);
                LocalTime clockOut = parts[3].trim().isEmpty() ? null
                        : LocalTime.parse(parts[3].trim().toUpperCase(), TIME_FMT);
                AttendanceRecord rec = new AttendanceRecord(empId, date, clockIn, clockOut);
                save(rec);
            } catch (Exception ignored) {
                // skip malformed rows
            }
        }
    }

    /** Returns all records (across all employees). */
    public List<AttendanceRecord> findAll() {
        return new ArrayList<>(records);
    }
}
