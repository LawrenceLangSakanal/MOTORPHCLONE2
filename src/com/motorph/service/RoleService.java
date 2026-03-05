package com.motorph.service;

import com.motorph.model.Employee;
import com.motorph.model.Role;

/**
 * Maps an employee's position string to a {@link Role} enum value, and exposes
 * permission checks used by the UI layer.
 */
public class RoleService {

    /** Derive the logical Role from an employee's position. */
    public Role getRole(Employee employee) {
        if (employee == null) return Role.REGULAR;
        return getRole(employee.getPosition());
    }

    public Role getRole(String position) {
        if (position == null) return Role.REGULAR;
        switch (position) {
            case "Chief Executive Officer":
            case "Chief Operating Officer":
            case "Chief Finance Officer":
            case "Chief Marketing Officer":
            case "Accounting Head":
            case "Account Manager":
            case "Payroll Manager":
            case "HR Manager":
                return Role.MANAGER;

            case "IT Operations and Systems":
                return Role.ADMIN;

            case "HR Team Leader":
            case "HR Rank and File":
            case "Payroll Team Leader":
            case "Payroll Rank and File":
                return Role.HR;

            default:
                return Role.REGULAR;
        }
    }

    // ── Permission helpers ────────────────────────────────────────────────────

    public boolean canAccessEmployeeManagement(Employee e) {
        if (e == null) return false;
        Role r = getRole(e);
        return r == Role.ADMIN || r == Role.MANAGER
                || isHRPosition(e.getPosition());
    }

    public boolean canAccessPayrollManagement(Employee e) {
        if (e == null) return false;
        return isFinancePosition(e.getPosition());
    }

    public boolean canApproveLeave(Employee e) {
        if (e == null) return false;
        Role r = getRole(e);
        return r == Role.ADMIN || r == Role.MANAGER || r == Role.HR;
    }

    public boolean canAccessSystemMaintenance(Employee e) {
        if (e == null) return false;
        return "IT Operations and Systems".equals(e.getPosition());
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    private boolean isHRPosition(String position) {
        return position != null && (
                position.equals("HR Manager")
                || position.equals("HR Team Leader")
                || position.equals("HR Rank and File"));
    }

    private boolean isFinancePosition(String position) {
        return position != null && (
                position.equals("Chief Finance Officer")
                || position.equals("Payroll Manager")
                || position.equals("Payroll Team Leader")
                || position.equals("Payroll Rank and File"));
    }
}
