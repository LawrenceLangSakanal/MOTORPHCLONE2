package com.motorph.model;

/**
 * Employee domain model mirroring the 19-column EmployeeData.csv structure.
 * The master list is seeded by {@link com.motorph.repo.EmployeeRepository}.
 */
public class Employee {

    private String empId;
    private String firstName;
    private String lastName;
    private String birthday;
    private String hourlyRate;
    private String riceSubsidy;
    private String phoneAllowance;
    private String clothingAllowance;
    private String status;
    private String position;
    private String basicSalary;
    private String phoneNumber;
    private String sssNumber;
    private String philHealthNumber;
    private String tinNumber;
    private String pagIbigNumber;
    private String immediateSupervisor;
    private String grossSemiRate;
    private String address;
    private String profilePhotoPath; // optional — updated by ProfileService

    public Employee(String empId, String firstName, String lastName, String birthday,
                    String hourlyRate, String riceSubsidy, String phoneAllowance,
                    String clothingAllowance, String status, String position,
                    String basicSalary, String phoneNumber, String sssNumber,
                    String philHealthNumber, String tinNumber, String pagIbigNumber,
                    String immediateSupervisor, String grossSemiRate, String address) {
        this.empId = empId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.hourlyRate = hourlyRate;
        this.riceSubsidy = riceSubsidy;
        this.phoneAllowance = phoneAllowance;
        this.clothingAllowance = clothingAllowance;
        this.status = status;
        this.position = position;
        this.basicSalary = basicSalary;
        this.phoneNumber = phoneNumber;
        this.sssNumber = sssNumber;
        this.philHealthNumber = philHealthNumber;
        this.tinNumber = tinNumber;
        this.pagIbigNumber = pagIbigNumber;
        this.immediateSupervisor = immediateSupervisor;
        this.grossSemiRate = grossSemiRate;
        this.address = address;
    }

    // ── Getters ──────────────────────────────────────────────────────────────

    public String getEmpId()               { return empId; }
    public String getFirstName()           { return firstName; }
    public String getLastName()            { return lastName; }
    public String getBirthday()            { return birthday; }
    public String getHourlyRate()          { return hourlyRate; }
    public String getRiceSubsidy()         { return riceSubsidy; }
    public String getPhoneAllowance()      { return phoneAllowance; }
    public String getClothingAllowance()   { return clothingAllowance; }
    public String getStatus()              { return status; }
    public String getPosition()            { return position; }
    public String getBasicSalary()         { return basicSalary; }
    public String getPhoneNumber()         { return phoneNumber; }
    public String getSssNumber()           { return sssNumber; }
    public String getPhilHealthNumber()    { return philHealthNumber; }
    public String getTinNumber()           { return tinNumber; }
    public String getPagIbigNumber()       { return pagIbigNumber; }
    public String getImmediateSupervisor() { return immediateSupervisor; }
    public String getGrossSemiRate()       { return grossSemiRate; }
    public String getAddress()             { return address; }
    public String getProfilePhotoPath()    { return profilePhotoPath; }

    // ── Setters (for mutable fields) ─────────────────────────────────────────

    public void setStatus(String status)                           { this.status = status; }
    public void setPosition(String position)                       { this.position = position; }
    public void setPhoneNumber(String phoneNumber)                 { this.phoneNumber = phoneNumber; }
    public void setAddress(String address)                         { this.address = address; }
    public void setProfilePhotoPath(String profilePhotoPath)       { this.profilePhotoPath = profilePhotoPath; }
    public void setBasicSalary(String basicSalary)                 { this.basicSalary = basicSalary; }
    public void setHourlyRate(String hourlyRate)                   { this.hourlyRate = hourlyRate; }
    public void setGrossSemiRate(String grossSemiRate)             { this.grossSemiRate = grossSemiRate; }
    public void setImmediateSupervisor(String immediateSupervisor) { this.immediateSupervisor = immediateSupervisor; }

    /** Convenience: full display name. */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return empId + " - " + getFullName() + " (" + position + ")";
    }
}
