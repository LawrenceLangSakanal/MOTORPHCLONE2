package com.motorph.util;

import com.motorph.model.Employee;
import com.motorph.repo.EmployeeRepository;
import com.motorph.repo.AttendanceRepository;
import com.motorph.repo.LeaveRepository;
import com.motorph.service.AuthService;
import com.motorph.service.AttendanceService;
import com.motorph.service.LeaveService;
import com.motorph.service.PayrollService;
import com.motorph.service.ProfileService;
import com.motorph.service.RoleService;

/**
 * Application-wide singleton that holds all repositories and services, plus the
 * currently logged-in employee.  GUI classes resolve their dependencies here
 * instead of constructing services individually.
 */
public class AppContext {

    private static AppContext instance;

    // ── Repositories ─────────────────────────────────────────────────────────
    private final EmployeeRepository  employeeRepo   = EmployeeRepository.getInstance();
    private final AttendanceRepository attendanceRepo = AttendanceRepository.getInstance();
    private final LeaveRepository      leaveRepo      = LeaveRepository.getInstance();

    // ── Services ─────────────────────────────────────────────────────────────
    private final RoleService       roleService       = new RoleService();
    private final AuthService       authService       = new AuthService(employeeRepo);
    private final AttendanceService attendanceService = new AttendanceService(attendanceRepo);
    private final LeaveService      leaveService      = new LeaveService(leaveRepo);
    private final ProfileService    profileService    = new ProfileService(employeeRepo);
    private final PayrollService    payrollService    = new PayrollService(employeeRepo);

    // ── Session ───────────────────────────────────────────────────────────────
    private Employee currentEmployee;

    private AppContext() {}

    public static synchronized AppContext getInstance() {
        if (instance == null) instance = new AppContext();
        return instance;
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public EmployeeRepository  getEmployeeRepo()    { return employeeRepo; }
    public AttendanceRepository getAttendanceRepo() { return attendanceRepo; }
    public LeaveRepository     getLeaveRepo()       { return leaveRepo; }

    public RoleService       getRoleService()       { return roleService; }
    public AuthService       getAuthService()       { return authService; }
    public AttendanceService getAttendanceService() { return attendanceService; }
    public LeaveService      getLeaveService()      { return leaveService; }
    public ProfileService    getProfileService()    { return profileService; }
    public PayrollService    getPayrollService()    { return payrollService; }

    public Employee getCurrentEmployee()                   { return currentEmployee; }
    public void setCurrentEmployee(Employee currentEmployee) { this.currentEmployee = currentEmployee; }

    /** Clears the current session (called on logout). */
    public void clearSession() {
        currentEmployee = null;
    }
}
