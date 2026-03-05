package com.motorph.service;

import com.motorph.model.Employee;
import com.motorph.repo.EmployeeRepository;
import com.payroll.MotorPHPayrollG3;
import java.util.Optional;

/**
 * Bridge between the GUI layer and the existing {@code com.payroll.*} payroll
 * computation engine.  Delegates to {@link MotorPHPayrollG3} for calculations.
 */
public class PayrollService {

    private final EmployeeRepository employeeRepo;

    public PayrollService(EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    /**
     * Look up an employee from the in-memory repository.
     */
    public Optional<Employee> getEmployee(String empId) {
        return employeeRepo.findById(empId);
    }

    /**
     * Run payroll computation for an employee for a given date range.
     * Delegates to {@link MotorPHPayrollG3#runPayrollSearch}.
     *
     * @param empId     employee identifier
     * @param startDate start of the pay period (inclusive)
     * @param endDate   end of the pay period (inclusive)
     * @return result array from the engine, or null on error
     */
    public Object[] runPayroll(String empId, java.time.LocalDate startDate, java.time.LocalDate endDate) {
        try {
            return MotorPHPayrollG3.runPayrollSearch(startDate, endDate, empId);
        } catch (Exception ex) {
            return null;
        }
    }
}
