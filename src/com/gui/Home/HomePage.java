/* Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.gui.Home;

import com.gui.util.Theme;
import com.gui.EmpManage.AttendanceManagement;
import com.gui.EmpManage.EmployeeManagement;
import com.gui.Leave.LeaveApprovalForm;
import com.gui.Leave.LeaveRequestForm;
import com.gui.Payroll.PayrollManagement;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.awt.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.ImageIcon;
import com.gui.EmpManage.editEmployee;
import com.systemMaintenance.SystemMaintenance;
import javax.swing.JFrame;
import com.gui.Payroll.PayrollDisplay;

/**
 *
 * @author ongoj
 */
public class HomePage extends javax.swing.JFrame {

    // To keep time logs for attendance
    private User currentUser; // Store the user info
    private String clockInDate = null;
    private String clockInTime = null;

    //jPanel2
    private javax.swing.JLabel jLabelGovHeader;
    private javax.swing.JLabel jLabelSSS;
    private javax.swing.JLabel jLabelPhilhealth;
    private javax.swing.JLabel jLabelTIN;
    private javax.swing.JLabel jLabelPagibig;

    private javax.swing.JLabel jLabelPayHeader;
    private javax.swing.JLabel jLabelBasicSalary;
    private javax.swing.JLabel jLabelGrossSemi;

    private javax.swing.JLabel jLabelAllowHeader;
    private javax.swing.JLabel jLabelHourlyRate;
    private javax.swing.JLabel jLabelRiceSubsidy;
    private javax.swing.JLabel jLabelPhoneAllowance;
    private javax.swing.JLabel jLabelClothingAllowance;

    private PayrollDisplay payrollDisplay;

    // Constructor: receive User info
    public HomePage(User user) {
        this.currentUser = user;
        initComponents();
        // ── role-based button controls ───────────────────────────────
        String role = currentUser.getuPosition();
        // Employee Management → IT, HR Manager, HR Team Leader, **HR Rank and File**
        boolean canManageEmployees
                = role.equals("IT Operations and Systems")
                || role.equals("HR Manager")
                || role.equals("HR Team Leader")
                || role.equals("HR Rank and File");
        jButton3.setVisible(canManageEmployees);

        // Payroll Management → CFO, Payroll Manager, Payroll Team Leader, **Payroll Rank and File**
        boolean canManagePayroll
                = role.equals("Chief Finance Officer")
                || role.equals("Payroll Manager")
                || role.equals("Payroll Team Leader")
                || role.equals("Payroll Rank and File");
        jButton2.setVisible(canManagePayroll);

        boolean canSystemMaintenance
                = role.equals("IT Operations and Systems");
        jButton9.setVisible(canSystemMaintenance);

        //jPanel2 method
        setupJPanel2(); // we will define this method

        // ── Leave navigation buttons (added programmatically) ────────
        addLeaveButtons();

        setResizable(false);// Removes maximize and resizing
        setLocationRelativeTo(null); // This centers the window on the screen
        pack();// Fit frame to preferred size
        applyTheme(); // Apply modern color theme

        // Set the company logo on the left
        SwingUtilities.invokeLater(() -> setLogoOnLabel(jLabel1, "/com/gui/images/LoginIcons/RevisedLogo.png"));
        SwingUtilities.invokeLater(() -> setProfileImage(jLabel5, currentUser.getuEmpId()));
        setUserInfo();
        startClock();

    }

    // ── Theme Application ───────────────────────────────────────────────
    private void applyTheme() {
        // ── Content pane ──
        getContentPane().setBackground(Theme.PAGE_BG);

        // ── Sidebar (jPanel1) ──
        jPanel1.setBackground(Theme.SIDEBAR_BG);
        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 1));
        // Right-edge separator line
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(0x1E293B)));

        // Sidebar MENU label
        jLabel2.setText("MOTORPH");
        jLabel2.setForeground(Theme.TEXT_ON_DARK);
        jLabel2.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        jLabel2.setBackground(Theme.SIDEBAR_BG);
        jLabel2.setOpaque(true);
        jLabel2.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0x1E293B)),
            javax.swing.BorderFactory.createEmptyBorder(6, 0, 6, 0)
        ));

        // Date / Time labels
        jLabel3.setForeground(new java.awt.Color(0x94A3B8));
        jLabel3.setFont(Theme.FONT_SMALL);
        jLabel3.setBackground(Theme.SIDEBAR_BG);
        jLabel3.setOpaque(true);
        jLabel4.setForeground(new java.awt.Color(0x94A3B8));
        jLabel4.setFont(Theme.FONT_SMALL);
        jLabel4.setBackground(Theme.SIDEBAR_BG);
        jLabel4.setOpaque(true);

        // Logo label
        jLabel1.setBackground(Theme.SIDEBAR_BG);
        jLabel1.setOpaque(true);

        // ── Sidebar navigation buttons ──
        for (java.awt.Component c : jPanel1.getComponents()) {
            if (c instanceof javax.swing.JButton btn) {
                if (btn == jButton4) {
                    Theme.successButton(btn);
                    btn.setText("▶  Clock In");
                } else if (btn == jButton5) {
                    Theme.dangerButton(btn);
                    btn.setText("■  Clock Out");
                } else if (btn == jButton6) {
                    Theme.dangerButton(btn);
                    btn.setText("⇠  Logout");
                } else {
                    Theme.sidebarButton(btn);
                }
            }
        }
        // Rename the known field-level buttons with icon prefixes
        jButton2.setText("  \uD83D\uDCB3  Payroll");
        jButton3.setText("  \uD83D\uDC65  Employees");
        jButton7.setText("  \uD83D\uDDD3  Attendance");
        jButton9.setText("  \u2699  System Maintenance");
        jButton10.setText("  \uD83D\uDCC4  View Payroll Details");

        // Logout button - positioned separately
        Theme.dangerButton(jButton6);
        jButton6.setText("Logout");

        // ── Profile card (jPanel3) ──
        jPanel3.setBackground(Theme.CARD_BG);
        jPanel3.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createMatteBorder(3, 0, 0, 0, Theme.PRIMARY_BLUE),  // blue top accent
            javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(Theme.BORDER_COLOR, 1),
                javax.swing.BorderFactory.createEmptyBorder(10, 12, 10, 12)
            )
        ));
        // Profile info labels
        for (javax.swing.JLabel lbl : new javax.swing.JLabel[]{
                jLabel6, jLabel7, jLabel8, jLabel9,
                jLabel10, jLabel11, jLabel12, jLabel14}) {
            lbl.setFont(Theme.FONT_BODY);
            lbl.setForeground(Theme.TEXT_PRIMARY);
        }
        jLabel5.setBackground(Theme.CARD_BG);
        jLabel5.setOpaque(true);

        // Upload button
        Theme.secondaryButton(jButton8);
        jButton8.setText("Upload Photo");

        // ── Dashboard panel (jPanel2) — styled in setupJPanel2() ──
        jPanel2.setBackground(Theme.PAGE_BG);
    }

    // Default constructor for GUI builder compatibility (not used in production)
    public HomePage() {
        initComponents();
        startClock();
    }

    HomePage(String user) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // Assign user info to labels
    private void setUserInfo() {
        if (currentUser != null) {
            jLabel6.setText("<html><b>Employee ID:</b> " + currentUser.getuEmpId() + "</html>");
            jLabel7.setText("<html><b>Name:</b> " + currentUser.getuFirstName() + " " + currentUser.getuLastName() + "</html>");
            jLabel8.setText("<html><b>Date of Birth:</b> " + currentUser.getuDob() + "</html>");
            jLabel9.setText("<html><b>Position:</b> " + currentUser.getuPosition() + "</html>");
            jLabel10.setText("<html><b>Status:</b> " + currentUser.getuStatus() + "</html>");
            jLabel11.setText("<html><b>Phone Number:</b> " + currentUser.getuPhoneNumber() + "</html>");
            jLabel12.setText("<html><b>Immediate Supervisor:</b><br>" + currentUser.getuImmediateSupervisor() + "</html>");
            jLabel14.setText("<html><b>Address:</b> " + currentUser.getuAddress() + "</html>");
            // jPanel2 labels are built inline in setupJPanel2() — no update needed here
        }
    }

    // Start a timer to update date and time labels
    private String getCurrentManilaDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Manila"));
        return dateFormat.format(new Date());
    }

    private String getCurrentManilaTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        timeFormat.setTimeZone(TimeZone.getTimeZone("Asia/Manila"));
        return timeFormat.format(new Date());
    }

    private void startClock() {
        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Date now = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
                SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm:ss a");
                dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Manila"));
                timeFormat.setTimeZone(TimeZone.getTimeZone("Asia/Manila"));
                jLabel3.setText("Date: " + dateFormat.format(now));
                jLabel4.setText("Time: " + timeFormat.format(now));
            }
        });
        timer.start();
    }

    private void setProfileImage(JLabel label, String empId) {
        String[] exts = {".png", ".jpg", ".jpeg"};
        boolean found = false;
        for (String ext : exts) {
            String path = "/com/gui/images/EmployeeIDs/" + empId + ext;
            java.net.URL imgURL = getClass().getResource(path);
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                int width = label.getWidth();
                int height = label.getHeight();
                float aspectRatio = (float) icon.getIconWidth() / icon.getIconHeight();
                if (width / (float) height > aspectRatio) {
                    width = (int) (height * aspectRatio);
                } else {
                    height = (int) (width / aspectRatio);
                }
                Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(img));
                found = true;
                break;
            }
        }
        if (!found) {
            // Use NULL.png as the fallback image
            java.net.URL defaultImg = getClass().getResource("/com/gui/images/EmployeeIDs/NULL.png");
            if (defaultImg != null) {
                ImageIcon icon = new ImageIcon(defaultImg);
                int width = label.getWidth() > 0 ? label.getWidth() : label.getPreferredSize().width;
                int height = label.getHeight() > 0 ? label.getHeight() : label.getPreferredSize().height;
                Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(img));
                label.setText("");
            } else {
                // If even NULL.png is missing, fallback to text
                label.setText("No Photo");
                label.setIcon(null);
            }
        }
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
    }
    // Helper method for setLogoOnLabel

    private void setLogoOnLabel(JLabel label, String resourcePath) {
        java.net.URL logoURL = getClass().getResource(resourcePath);
        if (logoURL != null) {
            ImageIcon icon = new ImageIcon(logoURL);
            // Always use 200x200 for scaling
            int width = 200;
            int height = 200;
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(img));
            label.setText("");
            label.setPreferredSize(new java.awt.Dimension(width, height)); // Optional: Forces the label to reserve this space
        } else {
            label.setText("Logo not found");
            label.setIcon(null);
        }
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
    }

    private void setupJPanel2() {
        // ── Root: BorderLayout ──────────────────────────────────────────────
        jPanel2.setLayout(new java.awt.BorderLayout(0, 0));
        jPanel2.setBackground(Theme.PAGE_BG);
        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // ────────────────────────────────────────────────────────────────────
        // 1. WELCOME BANNER (NORTH)
        // ────────────────────────────────────────────────────────────────────
        javax.swing.JPanel bannerPanel = new javax.swing.JPanel(new java.awt.BorderLayout(8, 2));
        bannerPanel.setBackground(Theme.PRIMARY_BLUE);
        bannerPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(14, 18, 14, 18));

        String firstName = (currentUser != null) ? currentUser.getuFirstName() : "Employee";
        javax.swing.JLabel welcomeLabel = new javax.swing.JLabel("Welcome back, " + firstName + "!");
        welcomeLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 17));
        welcomeLabel.setForeground(Theme.TEXT_ON_DARK);

        String dept = (currentUser != null) ? currentUser.getuPosition() : "";
        javax.swing.JLabel posLabel = new javax.swing.JLabel(dept + "  |  MotorPH Philippines");
        posLabel.setFont(Theme.FONT_SMALL);
        posLabel.setForeground(new java.awt.Color(0xBFDBFE)); // light blue tint

        bannerPanel.add(welcomeLabel, java.awt.BorderLayout.CENTER);
        bannerPanel.add(posLabel,     java.awt.BorderLayout.SOUTH);
        jPanel2.add(bannerPanel, java.awt.BorderLayout.NORTH);

        // ────────────────────────────────────────────────────────────────────
        // 2. STAT METRIC CARDS ROW  (below banner)
        // ────────────────────────────────────────────────────────────────────
        java.text.NumberFormat peso = java.text.NumberFormat.getNumberInstance(new java.util.Locale("en", "PH"));
        peso.setMaximumFractionDigits(2);
        peso.setMinimumFractionDigits(2);

        String basicSalStr   = "₱ –";
        String semiMonthStr  = "₱ –";
        String hourlyStr     = "₱ –";
        String allowTotalStr = "₱ –";

        if (currentUser != null) {
            try {
                double basic     = Double.parseDouble(currentUser.getuBasicSalary().toString());
                double semi      = Double.parseDouble(currentUser.getuGrossSemiRate().toString());
                double hourly    = Double.parseDouble(currentUser.getuHourlyRate().toString());
                double rice      = Double.parseDouble(currentUser.getuRiceSubsidy().toString());
                double phone     = Double.parseDouble(currentUser.getuPhoneAllowance().toString());
                double clothing  = Double.parseDouble(currentUser.getuClothingAllowance().toString());
                double allowTotal = rice + phone + clothing;
                basicSalStr   = "₱" + peso.format(basic);
                semiMonthStr  = "₱" + peso.format(semi);
                hourlyStr     = "₱" + peso.format(hourly);
                allowTotalStr = "₱" + peso.format(allowTotal);
            } catch (NumberFormatException ignored) {}
        }

        javax.swing.JPanel statsRow = new javax.swing.JPanel(new java.awt.GridLayout(1, 4, 10, 0));
        statsRow.setBackground(Theme.PAGE_BG);
        statsRow.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 12, 8, 12));

        statsRow.add(Theme.statCard("Basic Salary",      basicSalStr,   Theme.STAT_INDIGO));
        statsRow.add(Theme.statCard("Semi-monthly Rate", semiMonthStr,  Theme.STAT_TEAL));
        statsRow.add(Theme.statCard("Hourly Rate",       hourlyStr,     Theme.STAT_EMERALD));
        statsRow.add(Theme.statCard("Total Allowances",  allowTotalStr, Theme.STAT_AMBER));

        // ────────────────────────────────────────────────────────────────────
        // 3. TWO-COLUMN DETAIL SECTIONS (CENTER)
        // ────────────────────────────────────────────────────────────────────
        javax.swing.JPanel detailsWrap = new javax.swing.JPanel(new java.awt.GridLayout(1, 2, 10, 0));
        detailsWrap.setBackground(Theme.PAGE_BG);
        detailsWrap.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 12, 12, 12));

        // ── LEFT: Government & Contributions ──
        javax.swing.JPanel govCard = new javax.swing.JPanel();
        govCard.setLayout(new javax.swing.BoxLayout(govCard, javax.swing.BoxLayout.Y_AXIS));
        govCard.setBackground(Theme.CARD_BG);
        govCard.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(Theme.BORDER_COLOR, 1),
            javax.swing.BorderFactory.createEmptyBorder(12, 14, 12, 14)
        ));

        jLabelGovHeader = Theme.sectionHeader("Government & Contributions", Theme.STAT_INDIGO);
        jLabelGovHeader.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        govCard.add(jLabelGovHeader);
        govCard.add(javax.swing.Box.createVerticalStrut(8));

        jLabelSSS         = makeDetailRow("SSS Number",       (currentUser != null) ? currentUser.getuSSS() : "");
        jLabelPhilhealth  = makeDetailRow("PhilHealth No.",   (currentUser != null) ? currentUser.getuPhilHealth() : "");
        jLabelTIN         = makeDetailRow("TIN Number",       (currentUser != null) ? currentUser.getuTIN() : "");
        jLabelPagibig     = makeDetailRow("Pag-IBIG Number",  (currentUser != null) ? currentUser.getuPagIbig() : "");

        for (javax.swing.JLabel lbl : new javax.swing.JLabel[]{jLabelSSS, jLabelPhilhealth, jLabelTIN, jLabelPagibig}) {
            lbl.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
            govCard.add(lbl);
            govCard.add(javax.swing.Box.createVerticalStrut(6));
        }

        // ── RIGHT: Pay Breakdown ──
        javax.swing.JPanel payCard = new javax.swing.JPanel();
        payCard.setLayout(new javax.swing.BoxLayout(payCard, javax.swing.BoxLayout.Y_AXIS));
        payCard.setBackground(Theme.CARD_BG);
        payCard.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(Theme.BORDER_COLOR, 1),
            javax.swing.BorderFactory.createEmptyBorder(12, 14, 12, 14)
        ));

        jLabelPayHeader = Theme.sectionHeader("Pay Details", Theme.STAT_TEAL);
        jLabelPayHeader.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        payCard.add(jLabelPayHeader);
        payCard.add(javax.swing.Box.createVerticalStrut(8));

        jLabelBasicSalary = makeDetailRow("Basic Salary",       basicSalStr);
        jLabelGrossSemi   = makeDetailRow("Gross Semi-monthly", semiMonthStr);

        javax.swing.JLabel sep = Theme.sectionHeader("Allowances", Theme.STAT_AMBER);
        sep.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

        jLabelAllowHeader     = sep;
        jLabelHourlyRate      = makeDetailRow("Hourly Rate",        hourlyStr);
        jLabelRiceSubsidy     = makeDetailRow("Rice Subsidy",       (currentUser != null) ? "₱" + peso.format(parseDoubleOrDefault(currentUser.getuRiceSubsidy())) : "₱ –");
        jLabelPhoneAllowance  = makeDetailRow("Phone Allowance",    (currentUser != null) ? "₱" + peso.format(parseDoubleOrDefault(currentUser.getuPhoneAllowance())) : "₱ –");
        jLabelClothingAllowance = makeDetailRow("Clothing Allow.",  (currentUser != null) ? "₱" + peso.format(parseDoubleOrDefault(currentUser.getuClothingAllowance())) : "₱ –");

        for (java.awt.Component c : new java.awt.Component[]{
                jLabelBasicSalary, jLabelGrossSemi,
                javax.swing.Box.createVerticalStrut(8), sep, javax.swing.Box.createVerticalStrut(4),
                jLabelHourlyRate, jLabelRiceSubsidy, jLabelPhoneAllowance, jLabelClothingAllowance}) {
            if (c instanceof javax.swing.JLabel lbl) lbl.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
            payCard.add(c);
            if (c instanceof javax.swing.JLabel) payCard.add(javax.swing.Box.createVerticalStrut(6));
        }

        detailsWrap.add(govCard);
        detailsWrap.add(payCard);

        // ── Assemble CENTER section (stats + details) ──
        javax.swing.JPanel centerSection = new javax.swing.JPanel(new java.awt.BorderLayout());
        centerSection.setBackground(Theme.PAGE_BG);
        centerSection.add(statsRow,    java.awt.BorderLayout.NORTH);
        centerSection.add(detailsWrap, java.awt.BorderLayout.CENTER);

        jPanel2.add(centerSection, java.awt.BorderLayout.CENTER);
    }

    /** Create a label showing "  ● Field:  Value" with muted field name and primary value. */
    private javax.swing.JLabel makeDetailRow(String field, Object value) {
        String v = (value != null) ? value.toString() : "–";
        javax.swing.JLabel lbl = new javax.swing.JLabel(
            "<html><span style='color:#64748B;font-size:11px'>" + field + "</span>"
            + "<br><span style='color:#1E293B;font-size:12px'><b>" + v + "</b></span></html>"
        );
        lbl.setFont(Theme.FONT_BODY);
        lbl.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 0, 2, 0));
        return lbl;
    }

    /** Safe double parser with 0.0 default. */
    private double parseDoubleOrDefault(Object obj) {
        if (obj == null) return 0.0;
        try { return Double.parseDouble(obj.toString()); } catch (NumberFormatException e) { return 0.0; }
    }

    /**
     * Adds Leave navigation buttons to jPanel1 (the sidebar) programmatically
     * after initComponents().  The "Request Leave" button is visible to all
     * employees; "Leave Approval" is restricted to HR / Manager / Admin.
     */
    private void addLeaveButtons() {
        // "Request Leave" — all employees
        javax.swing.JButton btnLeaveRequest = new javax.swing.JButton("Request Leave");
        btnLeaveRequest.addActionListener(e -> {
            new com.gui.Leave.LeaveRequestForm(
                    com.motorph.util.AppContext.getInstance().getCurrentEmployee()
            ).setVisible(true);
        });
        jPanel1.add(btnLeaveRequest);

        // "Leave Approval" — HR / Manager / Admin only
        String pos = currentUser.getuPosition();
        boolean canApprove = pos.equals("HR Manager")
                || pos.equals("HR Team Leader")
                || pos.equals("HR Rank and File")
                || pos.equals("Chief Executive Officer")
                || pos.equals("Chief Operating Officer")
                || pos.equals("Chief Finance Officer")
                || pos.equals("Payroll Manager")
                || pos.equals("Account Manager")
                || pos.equals("Accounting Head")
                || pos.equals("IT Operations and Systems");
        if (canApprove) {
            javax.swing.JButton btnLeaveApproval = new javax.swing.JButton("Leave Approval");
            btnLeaveApproval.addActionListener(e -> {
                new com.gui.Leave.LeaveApprovalForm(
                        com.motorph.util.AppContext.getInstance().getCurrentEmployee()
                ).setVisible(true);
            });
            jPanel1.add(btnLeaveApproval);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        javax.swing.JButton jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(900, 900));
        setModalExclusionType(null);
        setSize(new java.awt.Dimension(700, 500));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.lightGray, java.awt.Color.darkGray));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel1.setMaximumSize(new java.awt.Dimension(200, 200));
        jLabel1.setMinimumSize(new java.awt.Dimension(150, 150));
        jLabel1.setPreferredSize(null);
        jLabel1.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("MENU");

        jButton2.setText("Payroll Management");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setText("Update Profile");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("Employee Management");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel3.setText("jLabel3");

        jLabel4.setText("jLabel4");

        jButton4.setText("Clock In");
        jButton4.setMaximumSize(new java.awt.Dimension(110, 30));
        jButton4.setMinimumSize(new java.awt.Dimension(110, 30));
        jButton4.setPreferredSize(new java.awt.Dimension(110, 30));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Clock Out");
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setMaximumSize(new java.awt.Dimension(110, 30));
        jButton5.setMinimumSize(new java.awt.Dimension(110, 30));
        jButton5.setPreferredSize(new java.awt.Dimension(110, 30));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton7.setText("Attendance Management");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton9.setText("System Maintenance");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setText("View Payroll Details");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton1, jButton10, jButton2, jButton3, jButton7, jButton9, jLabel2});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(67, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jButton10, jButton2, jButton3, jButton7, jButton9, jLabel2});

        jButton6.setText("Log out");
        jButton6.setMaximumSize(new java.awt.Dimension(75, 25));
        jButton6.setMinimumSize(new java.awt.Dimension(75, 25));
        jButton6.setPreferredSize(new java.awt.Dimension(75, 25));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel12.setText("jLabel12");

        jLabel6.setText("jLabel6");

        jLabel8.setText("jLabel8");

        jLabel14.setText("jLabel14");

        jLabel7.setText("jLabel7");

        jLabel10.setText("jLabel10");

        jLabel11.setText("jLabel11");

        jLabel9.setText("jLabel9");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel5.setMaximumSize(new java.awt.Dimension(200, 200));
        jLabel5.setMinimumSize(new java.awt.Dimension(150, 150));
        jLabel5.setPreferredSize(null);

        jButton8.setText("Upload");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton8))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(6, 6, 6)
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(6, 6, 6)
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(311, 311, 311)
                        .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // Logout Button - returns to LoginForm
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to log out?",
                "Logout Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        if (confirm == JOptionPane.YES_OPTION) {
            // Clear the shared session
            com.motorph.util.AppContext.getInstance().clearSession();

            // Close ALL open windows first
            Window[] windows = Window.getWindows();
            for (Window window : windows) {
                if (window.isVisible()) {
                    window.dispose();
                }
            }

            // Then open LoginForm
            new com.gui.Home.LoginForm().setVisible(true);
        }
        // If NO, do nothing
    }//GEN-LAST:event_jButton6ActionPerformed
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // Clock In Button
        // Prevent duplicate clock-in
        if (clockInTime != null && clockInDate != null) {
            JOptionPane.showMessageDialog(this, "You already clocked in at: " + clockInTime + " today.");
            return;
        }

        clockInTime = getCurrentManilaTime();
        clockInDate = getCurrentManilaDate();
        JOptionPane.showMessageDialog(this, "Time In recorded: " + clockInTime);
    }//GEN-LAST:event_jButton4ActionPerformed
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // Clock In must be recorded first
        if (clockInTime == null || clockInDate == null) {
            JOptionPane.showMessageDialog(this, "You need to clock in first!");
            return;
        }

        String clockOutTime = getCurrentManilaTime();
        String empId = currentUser.getuEmpId();
        String userCsvFile = "src/com/csv/DTR/" + empId + ".csv";
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");

        try {
            Date clockInDateObj = timeFormat.parse(clockInTime);
            Date clockOutDateObj = timeFormat.parse(clockOutTime);

            long durationMs = clockOutDateObj.getTime() - clockInDateObj.getTime();
            if (durationMs < 0) {
                durationMs += 24 * 60 * 60 * 1000; // Handle overnight shift
            }

            long durationMinutes = durationMs / (60 * 1000);
            long hours = durationMinutes / 60;
            long minutes = durationMinutes % 60;

            // Warn if under 8 hours
            if (durationMinutes < 480) {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "You have worked less than 8 hours (" + hours + "h " + minutes + "m).\nAre you sure you want to clock out?",
                        "Confirm Early Clock Out",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            // Write to CSV
            File file = new File(userCsvFile);
            boolean isNewFile = !file.exists();

            try (FileWriter fw = new FileWriter(file, true); BufferedWriter bw = new BufferedWriter(fw); PrintWriter out = new PrintWriter(bw)) {

                if (isNewFile) {
                    out.println("EmpID,Log Date,Clock In,Clock Out,Duration");
                }

                String durationStr = hours + "h " + minutes + "m";

                out.println(empId + ","
                        + clockInDate + ","
                        + clockInTime + ","
                        + clockOutTime + ","
                        + durationStr);
            }

            // Reset session values
            clockInTime = null;
            clockInDate = null;

            JOptionPane.showMessageDialog(this, "Time Out recorded and attendance saved!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error during Clock Out:\n" + e.getMessage());
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        editEmployee panel = new editEmployee(currentUser, currentUser.getuEmpId());
        JFrame frame = new JFrame("Update Profile — ID " + currentUser.getuEmpId());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(panel);
        frame.pack();
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (currentUser.canAccessPayrollManagement()) {
            new PayrollManagement(currentUser).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Access restricted to Finance/Payroll roles only.",
                    "Permission Denied",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // Employee Management Button
        if (currentUser.canAccessEmployeeManagement()) {
            new EmployeeManagement(currentUser).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Access restricted to leadership, IT or HR roles only.",
                    "Permission Denied",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // Open the Attendance Management window for the logged-in user
        AttendanceManagement am = new AttendanceManagement(currentUser);
        am.pack();
        am.setLocationRelativeTo(this);
        am.setVisible(true);

    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("PNG / JPG Images", "png", "jpg", "jpeg"));
        if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File src = chooser.getSelectedFile();
        String empId = currentUser.getuEmpId();
        String ext = src.getName().substring(src.getName().lastIndexOf('.') + 1).toLowerCase();
        String expected = empId + "." + ext;
        if (!src.getName().equals(expected)) {
            JOptionPane.showMessageDialog(
                    this,
                    "Filename must be exactly “" + expected + "”",
                    "Invalid Filename",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        File destDir = new File("src/com/gui/images/EmployeeIDs");
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        File dest = new File(destDir, expected);

        try {
            Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            JOptionPane.showMessageDialog(this, "Upload successful");
            // no label‐preview code here
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error saving file: " + ex.getMessage(),
                    "I/O Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        if ("IT Operations and Systems".equals(currentUser.getuPosition())) {
            new SystemMaintenance().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Access restricted to IT roles only.");
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        if (payrollDisplay == null) {                       // first time
            payrollDisplay = new PayrollDisplay(currentUser, this);
        }
        payrollDisplay.setLocationRelativeTo(this);         // center on parent
        payrollDisplay.setVisible(true);                    // show (or bring to front)
        payrollDisplay.toFront();
    }//GEN-LAST:event_jButton10ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomePage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables

}
