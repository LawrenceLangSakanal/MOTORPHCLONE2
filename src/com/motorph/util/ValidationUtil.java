package com.motorph.util;

/**
 * Basic input validation helpers used across GUI forms.
 */
public final class ValidationUtil {

    private ValidationUtil() {}

    /** Returns true if the string is non-null and not blank. */
    public static boolean isNotBlank(String value) {
        return value != null && !value.trim().isEmpty();
    }

    /** Returns true if the string is a valid employee ID (digits only). */
    public static boolean isValidEmpId(String value) {
        return isNotBlank(value) && value.trim().matches("\\d+");
    }

    /** Returns true if the string matches basic date format M/d/yyyy or d-MMM-yy. */
    public static boolean isValidDate(String value) {
        if (!isNotBlank(value)) return false;
        return value.trim().matches("\\d{1,2}/\\d{1,2}/\\d{4}")
                || value.trim().matches("\\d{1,2}-[A-Za-z]{3}-\\d{2,4}");
    }

    /** Returns true if the string is a valid positive number (with optional comma separators). */
    public static boolean isPositiveNumber(String value) {
        if (!isNotBlank(value)) return false;
        try {
            double v = Double.parseDouble(value.trim().replace(",", ""));
            return v > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks that a from-date string is not after a to-date string.
     * Uses simple lexicographic comparison when both are ISO (yyyy-MM-dd).
     */
    public static boolean isDateRangeValid(java.time.LocalDate from, java.time.LocalDate to) {
        if (from == null || to == null) return false;
        return !from.isAfter(to);
    }
}
