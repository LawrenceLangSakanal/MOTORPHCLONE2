package com.payroll;

/**
 * RegularEmployee – full employee data model used by EmployeeLoader.
 * Mirrors all fields from EmployeeData.csv + LoginCredentials.csv.
 */
public class RegularEmployee {

    private final int    empId;
    private final String password;
    private final String lastName;
    private final String firstName;
    private final String birthday;
    private final String address;
    private final String phoneNumber;
    private final String sss;
    private final String philhealth;
    private final String tin;
    private final String pagibig;
    private final String position;
    private final String supervisor;
    private final double basicSalary;
    private final double riceSubsidy;
    private final double phoneAllowance;
    private final double clothingAllowance;
    private final double grossSemiRate;
    private final double hourlyRate;

    // Computed deductions (set after construction)
    private double sssDeduction;
    private double philhealthDeduction;
    private double pagibigDeduction;
    private double taxDeduction;

    public RegularEmployee(
            int empId, String password,
            String lastName, String firstName,
            String birthday, String address, String phoneNumber,
            String sss, String philhealth, String tin, String pagibig,
            String position, String supervisor,
            double basicSalary, double riceSubsidy,
            double phoneAllowance, double clothingAllowance,
            double grossSemiRate, double hourlyRate) {

        this.empId           = empId;
        this.password        = password;
        this.lastName        = lastName;
        this.firstName       = firstName;
        this.birthday        = birthday;
        this.address         = address;
        this.phoneNumber     = phoneNumber;
        this.sss             = sss;
        this.philhealth      = philhealth;
        this.tin             = tin;
        this.pagibig         = pagibig;
        this.position        = position;
        this.supervisor      = supervisor;
        this.basicSalary     = basicSalary;
        this.riceSubsidy     = riceSubsidy;
        this.phoneAllowance  = phoneAllowance;
        this.clothingAllowance = clothingAllowance;
        this.grossSemiRate   = grossSemiRate;
        this.hourlyRate      = hourlyRate;
    }

    // ── Setters for deductions ────────────────────────────────────────────
    public void setSssDeduction(double v)        { this.sssDeduction = v; }
    public void setPhilhealthDeduction(double v) { this.philhealthDeduction = v; }
    public void setPagibigDeduction(double v)    { this.pagibigDeduction = v; }
    public void setTaxDeduction(double v)        { this.taxDeduction = v; }

    // ── Getters ───────────────────────────────────────────────────────────
    public int    getEmpId()              { return empId; }
    public String getPassword()           { return password; }
    public String getLastName()           { return lastName; }
    public String getFirstName()          { return firstName; }
    public String getFullName()           { return firstName + " " + lastName; }
    public String getBirthday()           { return birthday; }
    public String getAddress()            { return address; }
    public String getPhoneNumber()        { return phoneNumber; }
    public String getSss()                { return sss; }
    public String getPhilhealth()         { return philhealth; }
    public String getTin()                { return tin; }
    public String getPagibig()            { return pagibig; }
    public String getPosition()           { return position; }
    public String getSupervisor()         { return supervisor; }
    public double getBasicSalary()        { return basicSalary; }
    public double getRiceSubsidy()        { return riceSubsidy; }
    public double getPhoneAllowance()     { return phoneAllowance; }
    public double getClothingAllowance()  { return clothingAllowance; }
    public double getGrossSemiRate()      { return grossSemiRate; }
    public double getHourlyRate()         { return hourlyRate; }
    public double getSssDeduction()       { return sssDeduction; }
    public double getPhilhealthDeduction(){ return philhealthDeduction; }
    public double getPagibigDeduction()   { return pagibigDeduction; }
    public double getTaxDeduction()       { return taxDeduction; }

    public double getTotalDeductions() {
        return sssDeduction + philhealthDeduction + pagibigDeduction + taxDeduction;
    }

    public double getNetPay() {
        return basicSalary - getTotalDeductions();
    }

    @Override
    public String toString() {
        return "[" + empId + "] " + getFullName() + " — " + position;
    }
}
