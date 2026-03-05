package com.motorph.repo;

import com.motorph.model.LeaveRequest;
import com.motorph.model.LeaveStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * In-memory repository for leave requests.
 */
public class LeaveRepository {

    private static LeaveRepository instance;

    private final List<LeaveRequest> requests = new ArrayList<>();

    private LeaveRepository() {}

    public static synchronized LeaveRepository getInstance() {
        if (instance == null) instance = new LeaveRepository();
        return instance;
    }

    // ── Write ─────────────────────────────────────────────────────────────────

    public void save(LeaveRequest request) {
        requests.add(request);
    }

    public boolean update(LeaveRequest updated) {
        for (int i = 0; i < requests.size(); i++) {
            if (requests.get(i).getId() == updated.getId()) {
                requests.set(i, updated);
                return true;
            }
        }
        return false;
    }

    // ── Read ──────────────────────────────────────────────────────────────────

    public List<LeaveRequest> findAll() {
        return new ArrayList<>(requests);
    }

    public Optional<LeaveRequest> findById(int id) {
        return requests.stream().filter(r -> r.getId() == id).findFirst();
    }

    public List<LeaveRequest> findByEmpId(String empId) {
        return requests.stream()
                .filter(r -> r.getEmpId().equals(empId))
                .collect(Collectors.toList());
    }

    public List<LeaveRequest> findByStatus(LeaveStatus status) {
        return requests.stream()
                .filter(r -> r.getStatus() == status)
                .collect(Collectors.toList());
    }

    public List<LeaveRequest> findPending() {
        return findByStatus(LeaveStatus.PENDING);
    }
}
