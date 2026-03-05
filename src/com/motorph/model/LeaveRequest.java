package com.motorph.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a leave request submitted by an employee.
 */
public class LeaveRequest {

    private static int sequence = 1;

    private final int id;
    private final String empId;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String reason;
    private LeaveStatus status;
    private final LocalDateTime submittedAt;
    private String reviewedBy;
    private LocalDateTime reviewedAt;

    public LeaveRequest(String empId, LocalDate fromDate, LocalDate toDate, String reason) {
        this.id          = sequence++;
        this.empId       = empId;
        this.fromDate    = fromDate;
        this.toDate      = toDate;
        this.reason      = reason;
        this.status      = LeaveStatus.PENDING;
        this.submittedAt = LocalDateTime.now();
    }

    // ── Getters ──────────────────────────────────────────────────────────────

    public int           getId()          { return id; }
    public String        getEmpId()       { return empId; }
    public LocalDate     getFromDate()    { return fromDate; }
    public LocalDate     getToDate()      { return toDate; }
    public String        getReason()      { return reason; }
    public LeaveStatus   getStatus()      { return status; }
    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public String        getReviewedBy()  { return reviewedBy; }
    public LocalDateTime getReviewedAt()  { return reviewedAt; }

    // ── Setters ──────────────────────────────────────────────────────────────

    public void setStatus(LeaveStatus status)          { this.status     = status; }
    public void setReviewedBy(String reviewedBy)       { this.reviewedBy = reviewedBy; }
    public void setReviewedAt(LocalDateTime reviewedAt){ this.reviewedAt = reviewedAt; }
    public void setFromDate(LocalDate fromDate)        { this.fromDate   = fromDate; }
    public void setToDate(LocalDate toDate)            { this.toDate     = toDate; }
    public void setReason(String reason)               { this.reason     = reason; }

    @Override
    public String toString() {
        return "LeaveRequest#" + id + "[" + empId + " " + fromDate + "->" + toDate + " " + status + "]";
    }
}
