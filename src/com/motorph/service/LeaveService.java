package com.motorph.service;

import com.motorph.model.LeaveRequest;
import com.motorph.model.LeaveStatus;
import com.motorph.repo.LeaveRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Business logic for submitting and reviewing leave requests.
 */
public class LeaveService {

    private final LeaveRepository repo;

    public LeaveService(LeaveRepository repo) {
        this.repo = repo;
    }

    /**
     * Submit a new leave request.
     *
     * @param empId    requesting employee
     * @param fromDate start of leave (inclusive)
     * @param toDate   end of leave (inclusive)
     * @param reason   reason for the leave
     * @return the newly created {@link LeaveRequest}
     */
    public LeaveRequest submit(String empId, LocalDate fromDate, LocalDate toDate, String reason) {
        if (fromDate == null || toDate == null) throw new IllegalArgumentException("Dates must not be null.");
        if (fromDate.isAfter(toDate)) throw new IllegalArgumentException("From-date must not be after to-date.");
        if (reason == null || reason.isBlank()) throw new IllegalArgumentException("Reason must not be blank.");

        LeaveRequest request = new LeaveRequest(empId, fromDate, toDate, reason.trim());
        repo.save(request);
        return request;
    }

    /** All leave requests in the system. */
    public List<LeaveRequest> listAll() {
        return repo.findAll();
    }

    /** All pending leave requests (for HR/Manager approval view). */
    public List<LeaveRequest> listPending() {
        return repo.findPending();
    }

    /** All leave requests for a specific employee. */
    public List<LeaveRequest> listByEmployee(String empId) {
        return repo.findByEmpId(empId);
    }

    /**
     * Approve a leave request.
     *
     * @param requestId  ID of the request to approve
     * @param reviewerId employee ID of the approver
     */
    public void approve(int requestId, String reviewerId) {
        LeaveRequest req = getOrThrow(requestId);
        req.setStatus(LeaveStatus.APPROVED);
        req.setReviewedBy(reviewerId);
        req.setReviewedAt(LocalDateTime.now());
        repo.update(req);
    }

    /**
     * Reject a leave request.
     *
     * @param requestId  ID of the request to reject
     * @param reviewerId employee ID of the reviewer
     */
    public void reject(int requestId, String reviewerId) {
        LeaveRequest req = getOrThrow(requestId);
        req.setStatus(LeaveStatus.REJECTED);
        req.setReviewedBy(reviewerId);
        req.setReviewedAt(LocalDateTime.now());
        repo.update(req);
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    private LeaveRequest getOrThrow(int requestId) {
        return repo.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Leave request #" + requestId + " not found."));
    }
}
