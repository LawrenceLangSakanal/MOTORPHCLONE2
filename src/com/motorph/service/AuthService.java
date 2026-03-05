package com.motorph.service;

import com.motorph.model.Employee;
import com.motorph.repo.EmployeeRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Authenticates employees using the existing CSV credential store.
 * No Excel dependency is required; credentials come from
 * {@code src/com/csv/LoginCredentials.csv}.
 *
 * The employee master data is resolved from {@link EmployeeRepository}
 * (hardcoded in-memory) rather than from EmployeeData.csv.
 */
public class AuthService {

    private static final Path CRED_PATH = Paths.get("src", "com", "csv", "LoginCredentials.csv");
    private static final String CRED_RESOURCE = "/com/csv/LoginCredentials.csv";

    private final EmployeeRepository employeeRepo;

    public AuthService(EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    /**
     * Attempt login.  Returns the matching {@link Employee} on success, or
     * throws an {@link AuthException} describing the failure reason.
     *
     * @param empId    employee ID (username)
     * @param password plain-text password
     */
    public Employee login(String empId, String password) throws AuthException {
        Map<String, String[]> credentials = loadCredentials();

        if (!credentials.containsKey(empId)) {
            throw new AuthException("No account found for ID: " + empId);
        }

        String[] row = credentials.get(empId);
        String storedPass  = row.length > 1 ? row[1].trim() : "";
        String lockStatus  = row.length > 5 ? row[5].trim() : "No";

        if ("Yes".equalsIgnoreCase(lockStatus)) {
            throw new AuthException("Account is locked. Contact IT to reactivate.");
        }

        if (!storedPass.equals(password)) {
            throw new AuthException("Incorrect password.");
        }

        Optional<Employee> emp = employeeRepo.findById(empId);
        if (emp.isEmpty()) {
            throw new AuthException("Employee data not found. Contact admin.");
        }

        return emp.get();
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    /** Reads credentials from file-system path first, then classpath fallback. */
    private Map<String, String[]> loadCredentials() throws AuthException {
        Map<String, String[]> map = new HashMap<>();
        try {
            Reader reader;
            if (CRED_PATH.toFile().exists()) {
                reader = Files.newBufferedReader(CRED_PATH);
            } else {
                InputStream is = getClass().getResourceAsStream(CRED_RESOURCE);
                if (is == null) throw new AuthException("Credentials file not found.");
                reader = new InputStreamReader(is);
            }

            try (CSVReader csv = new CSVReader(reader)) {
                String[] row;
                boolean header = true;
                while ((row = csv.readNext()) != null) {
                    if (header) { header = false; continue; }
                    if (row.length > 0 && !row[0].isBlank()) {
                        map.put(row[0].trim(), row);
                    }
                }
            }
        } catch (IOException | CsvValidationException ex) {
            throw new AuthException("Cannot read credentials: " + ex.getMessage());
        }
        return map;
    }

    // ── Exception ─────────────────────────────────────────────────────────────

    public static class AuthException extends Exception {
        public AuthException(String message) { super(message); }
    }
}
