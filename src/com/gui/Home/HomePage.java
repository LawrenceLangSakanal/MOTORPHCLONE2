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

    // ── PayrollDisplay (lazy-loaded modal) ──────────────────────────────────
    private PayrollDisplay payrollDisplay;

    // ── Sidebar / Dashboard state ────────────────────────────────────────────
    private javax.swing.JPanel  contentArea;   // CardLayout host
    private java.awt.CardLayout cardLayout;
    private javax.swing.JButton activeNavBtn;  // tracks highlighted nav item
    private javax.swing.JLabel  clockStatusLbl;// "Status: IN" / "Status: OUT"

    // Constructor: receive User info
    public HomePage(User user) {
        this.currentUser = user;
        initComponents();
        buildDashboard();
        startClock();
    }

    // ════════════════════════════════════════════════════════════════════════
    //  DASHBOARD BUILDER  –  replaces content pane after initComponents()
    // ════════════════════════════════════════════════════════════════════════
    private void buildDashboard() {
        String role = (currentUser != null) ? currentUser.getuPosition() : "";

        // ── Frame setup ──────────────────────────────────────────────────────
        setTitle("MotorPH Payroll System");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1050, 680));
        setResizable(true);

        // ── Root layout ──────────────────────────────────────────────────────
        java.awt.Container cp = getContentPane();
        cp.removeAll();
        cp.setLayout(new java.awt.BorderLayout());
        cp.setBackground(Theme.PAGE_BG);

        // ════════════════════════════════
        //  A. SIDEBAR
        // ════════════════════════════════
        javax.swing.JPanel sidebar = new javax.swing.JPanel();
        sidebar.setBackground(Theme.SIDEBAR_BG);
        sidebar.setPreferredSize(new java.awt.Dimension(220, 0));
        sidebar.setLayout(new java.awt.BorderLayout());

        // ── A1. Sidebar top: Logo + company name ─────────────────────────────
        javax.swing.JPanel sideTop = new javax.swing.JPanel();
        sideTop.setBackground(Theme.SIDEBAR_BG);
        sideTop.setLayout(new java.awt.BorderLayout(0, 4));
        sideTop.setBorder(javax.swing.BorderFactory.createEmptyBorder(16, 16, 12, 16));

        javax.swing.JLabel logoName = new javax.swing.JLabel("MotorPH", javax.swing.SwingConstants.LEFT);
        logoName.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
        logoName.setForeground(Theme.TEXT_ON_DARK);

        javax.swing.JLabel logoSub = new javax.swing.JLabel("HR & Payroll System", javax.swing.SwingConstants.LEFT);
        logoSub.setFont(Theme.FONT_SMALL);
        logoSub.setForeground(new java.awt.Color(0x64748B));

        javax.swing.JSeparator logoSep = new javax.swing.JSeparator();
        logoSep.setForeground(new java.awt.Color(0x1E293B));
        logoSep.setBackground(new java.awt.Color(0x1E293B));

        sideTop.add(logoName, java.awt.BorderLayout.NORTH);
        sideTop.add(logoSub,  java.awt.BorderLayout.CENTER);
        sideTop.add(logoSep,  java.awt.BorderLayout.SOUTH);

        // ── A2. Nav items (scrollable) ───────────────────────────────────────
        javax.swing.JPanel navPanel = new javax.swing.JPanel();
        navPanel.setBackground(Theme.SIDEBAR_BG);
        navPanel.setLayout(new javax.swing.BoxLayout(navPanel, javax.swing.BoxLayout.Y_AXIS));
        navPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));

        // Helper to create a nav button
        // Args: label, emoji prefix, accent color (null = default), card name, always-visible
        // Role-based access map
        boolean canPayroll = role.equals("Chief Finance Officer")
                || role.equals("Payroll Manager") || role.equals("Payroll Team Leader")
                || role.equals("Payroll Rank and File")
                || role.equals("IT Operations and Systems")
                || role.equals("Chief Executive Officer")
                || role.equals("Chief Operating Officer");
        boolean canEmployees = role.equals("IT Operations and Systems")
                || role.equals("HR Manager") || role.equals("HR Team Leader")
                || role.equals("HR Rank and File");
        boolean canAttendance = true;
        boolean canSysMaint = role.equals("IT Operations and Systems");
        boolean canApprove = role.equals("HR Manager") || role.equals("HR Team Leader")
                || role.equals("HR Rank and File")
                || role.equals("Chief Executive Officer")
                || role.equals("Chief Operating Officer")
                || role.equals("Chief Finance Officer")
                || role.equals("Payroll Manager") || role.equals("Account Manager")
                || role.equals("Accounting Head")
                || role.equals("IT Operations and Systems");

        // ── Profile nav (all roles) ──
        javax.swing.JButton btnProfile = makeNavBtn("\uD83D\uDC64  Profile", null);
        navPanel.add(btnProfile);

        // ── Attendance nav ──
        javax.swing.JButton btnAttendance = makeNavBtn("\uD83D\uDDD3  Attendance", null);
        navPanel.add(btnAttendance);

        // ── Payroll nav (role-restricted) ──
        javax.swing.JButton btnPayroll = null;
        if (canPayroll) {
            btnPayroll = makeNavBtn("\uD83D\uDCB3  Payroll", new java.awt.Color(0x1D4ED8)); // blue accent
            navPanel.add(btnPayroll);
        }

        // ── Employees nav (HR roles) ──
        javax.swing.JButton btnEmployees = null;
        if (canEmployees) {
            btnEmployees = makeNavBtn("\uD83D\uDC65  Employees", new java.awt.Color(0x0F766E)); // teal accent
            navPanel.add(btnEmployees);
        }

        // ── Leave Request nav (all) ──
        javax.swing.JButton btnLeaveReq = makeNavBtn("\uD83D\uDCDD  Leave Request", new java.awt.Color(0x059669)); // green
        navPanel.add(btnLeaveReq);

        // ── Leave Status (all) ──
        javax.swing.JButton btnLeaveStatus = makeNavBtn("\uD83D\uDCCB  Leave Status", null);
        navPanel.add(btnLeaveStatus);

        // ── Leave Approval (managers) ──
        javax.swing.JButton btnLeaveApproval = null;
        if (canApprove) {
            btnLeaveApproval = makeNavBtn("\u2714  Leave Approval", new java.awt.Color(0xD97706)); // amber
            navPanel.add(btnLeaveApproval);
        }

        // ── System Maintenance (IT only) ──
        javax.swing.JButton btnSysMaint = null;
        if (canSysMaint) {
            btnSysMaint = makeNavBtn("\u2699  System Maint.", new java.awt.Color(0x7C3AED)); // purple
            navPanel.add(btnSysMaint);
        }

        // ── View Payroll Details ──
        javax.swing.JButton btnViewPayroll = makeNavBtn("\uD83D\uDCC4  Payroll Details", null);
        navPanel.add(btnViewPayroll);

        navPanel.add(javax.swing.Box.createVerticalGlue());

        // ── A3. Sidebar bottom: user info + logout ───────────────────────────
        javax.swing.JPanel sideBottom = new javax.swing.JPanel();
        sideBottom.setBackground(new java.awt.Color(0x0A0F1C));
        sideBottom.setLayout(new java.awt.BorderLayout(0, 6));
        sideBottom.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 16, 14, 16));

        String fullName = (currentUser != null)
                ? currentUser.getuFirstName() + " " + currentUser.getuLastName() : "Employee";
        javax.swing.JLabel userNameLbl = new javax.swing.JLabel(fullName);
        userNameLbl.setFont(Theme.FONT_BUTTON);
        userNameLbl.setForeground(Theme.TEXT_ON_DARK);

        // Role badge
        String badgeRole = role.isEmpty() ? "Employee" : role;
        // Shorten very long role names
        if (role.startsWith("IT Operations")) badgeRole = "IT";
        else if (role.contains("Chief Executive")) badgeRole = "CEO";
        else if (role.contains("Chief Operating")) badgeRole = "COO";
        else if (role.contains("Chief Finance")) badgeRole = "CFO";
        else if (role.contains("Chief Marketing")) badgeRole = "CMO";
        else if (role.length() > 12) badgeRole = "STAFF";

        javax.swing.JLabel roleBadge = new javax.swing.JLabel(badgeRole.toUpperCase());
        roleBadge.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 10));
        roleBadge.setForeground(new java.awt.Color(0x93C5FD));
        roleBadge.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 4, 0));

        javax.swing.JButton btnLogout = new javax.swing.JButton("\u2190  Logout");
        btnLogout.setBackground(new java.awt.Color(0x7F1D1D));
        btnLogout.setForeground(Theme.TEXT_ON_DARK);
        btnLogout.setFont(Theme.FONT_BUTTON);
        btnLogout.setFocusPainted(false);
        btnLogout.setBorderPainted(false);
        btnLogout.setOpaque(true);
        btnLogout.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        btnLogout.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 34));
        btnLogout.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnLogout.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 12, 8, 12));

        javax.swing.JPanel userInfo = new javax.swing.JPanel(new java.awt.BorderLayout(0, 2));
        userInfo.setBackground(new java.awt.Color(0x0A0F1C));
        userInfo.add(roleBadge,   java.awt.BorderLayout.NORTH);
        userInfo.add(userNameLbl, java.awt.BorderLayout.CENTER);

        sideBottom.add(new javax.swing.JSeparator(), java.awt.BorderLayout.NORTH);
        sideBottom.add(userInfo,    java.awt.BorderLayout.CENTER);
        sideBottom.add(btnLogout,   java.awt.BorderLayout.SOUTH);

        // Assemble sidebar
        sideBottom.setPreferredSize(new java.awt.Dimension(220, 110));
        javax.swing.JScrollPane navScroll = new javax.swing.JScrollPane(navPanel,
                javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        navScroll.setBorder(null);
        navScroll.setBackground(Theme.SIDEBAR_BG);
        navScroll.getViewport().setBackground(Theme.SIDEBAR_BG);

        sidebar.add(sideTop,    java.awt.BorderLayout.NORTH);
        sidebar.add(navScroll,  java.awt.BorderLayout.CENTER);
        sidebar.add(sideBottom, java.awt.BorderLayout.SOUTH);

        // ════════════════════════════════
        //  B. TOP BAR
        // ════════════════════════════════
        javax.swing.JPanel topBar = new javax.swing.JPanel(new java.awt.BorderLayout(0, 0));
        topBar.setBackground(Theme.CARD_BG);
        topBar.setPreferredSize(new java.awt.Dimension(0, 56));
        topBar.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDER_COLOR),
            javax.swing.BorderFactory.createEmptyBorder(0, 20, 0, 16)
        ));

        String firstName2 = (currentUser != null) ? currentUser.getuFirstName() + " " + currentUser.getuLastName() : "Employee";
        javax.swing.JLabel welcomeLbl = new javax.swing.JLabel("Welcome, " + firstName2);
        welcomeLbl.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));
        welcomeLbl.setForeground(Theme.TEXT_PRIMARY);

        // Right side: Status + Clock In + Clock Out
        javax.swing.JPanel topRight = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 10));
        topRight.setBackground(Theme.CARD_BG);

        // Date/Time label (live clock)
        jLabel3 = new javax.swing.JLabel();
        jLabel3.setFont(Theme.FONT_SMALL);
        jLabel3.setForeground(Theme.TEXT_MUTED);
        jLabel4 = new javax.swing.JLabel();
        jLabel4.setFont(Theme.FONT_SMALL);
        jLabel4.setForeground(Theme.TEXT_MUTED);

        clockStatusLbl = new javax.swing.JLabel("Status: OUT");
        clockStatusLbl.setFont(Theme.FONT_BUTTON);
        clockStatusLbl.setForeground(Theme.DANGER_RED);

        javax.swing.JButton topClockIn = new javax.swing.JButton("\u25B6  Clock In");
        topClockIn.setBackground(Theme.SUCCESS_GREEN);
        topClockIn.setForeground(Theme.TEXT_ON_DARK);
        topClockIn.setFont(Theme.FONT_BUTTON);
        topClockIn.setFocusPainted(false);
        topClockIn.setBorderPainted(false);
        topClockIn.setOpaque(true);
        topClockIn.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        topClockIn.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 14, 6, 14));

        javax.swing.JButton topClockOut = new javax.swing.JButton("\u25A0  Clock Out");
        topClockOut.setBackground(new java.awt.Color(0xCBD5E1));
        topClockOut.setForeground(new java.awt.Color(0x64748B));
        topClockOut.setFont(Theme.FONT_BUTTON);
        topClockOut.setFocusPainted(false);
        topClockOut.setBorderPainted(false);
        topClockOut.setOpaque(true);
        topClockOut.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        topClockOut.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 14, 6, 14));

        topRight.add(jLabel3);
        topRight.add(clockStatusLbl);
        topRight.add(topClockIn);
        topRight.add(topClockOut);

        topBar.add(welcomeLbl, java.awt.BorderLayout.WEST);
        topBar.add(topRight,   java.awt.BorderLayout.EAST);

        // ════════════════════════════════
        //  C. CARD LAYOUT CONTENT
        // ════════════════════════════════
        cardLayout = new java.awt.CardLayout();
        contentArea = new javax.swing.JPanel(cardLayout);
        contentArea.setBackground(Theme.PAGE_BG);

        // ── Card: Profile ──────────────────────────────────────────────────
        contentArea.add(buildProfileCard(), "profile");

        // ── Card: Attendance ──────────────────────────────────────────────
        contentArea.add(buildPlaceholderCard("Attendance", "\uD83D\uDDD3",
                "View and manage your daily time records.",
                new java.awt.Color(0x1D4ED8)), "attendance");

        // ── Card: Payroll ─────────────────────────────────────────────────
        if (canPayroll) contentArea.add(buildPlaceholderCard("Payroll Management", "\uD83D\uDCB3",
                "Process payroll, view payslips and deductions.",
                new java.awt.Color(0x1D4ED8)), "payroll");

        // ── Card: Employees ───────────────────────────────────────────────
        if (canEmployees) contentArea.add(buildPlaceholderCard("Employee Management", "\uD83D\uDC65",
                "Add, edit or view employee records.",
                new java.awt.Color(0x0F766E)), "employees");

        // ── Card: Leave Request ───────────────────────────────────────────
        contentArea.add(buildPlaceholderCard("Leave Request", "\uD83D\uDCDD",
                "File a new leave request.",
                new java.awt.Color(0x059669)), "leaveReq");

        // ── Card: Leave Status ────────────────────────────────────────────
        contentArea.add(buildPlaceholderCard("Leave Status", "\uD83D\uDCCB",
                "Check the status of your leave applications.",
                new java.awt.Color(0x1E293B)), "leaveStatus");

        // ── Card: Leave Approval ──────────────────────────────────────────
        if (canApprove) contentArea.add(buildPlaceholderCard("Leave Approval", "\u2714",
                "Review and approve pending leave requests.",
                new java.awt.Color(0xD97706)), "leaveApproval");

        // ── Card: System Maintenance ──────────────────────────────────────
        if (canSysMaint) contentArea.add(buildPlaceholderCard("System Maintenance", "\u2699",
                "Manage system backups and maintenance tasks.",
                new java.awt.Color(0x7C3AED)), "sysMaint");

        // ── Card: Payroll Details ─────────────────────────────────────────
        contentArea.add(buildPlaceholderCard("Payroll Details", "\uD83D\uDCC4",
                "View your detailed payroll computation.",
                new java.awt.Color(0x1D4ED8)), "payrollDetails");

        // ════════════════════════════════
        //  D. WIRE NAV ACTIONS
        // ════════════════════════════════
        setNavActive(btnProfile);
        btnProfile.addActionListener(e -> { setNavActive(btnProfile); cardLayout.show(contentArea, "profile"); });
        btnAttendance.addActionListener(e -> { setNavActive(btnAttendance); cardLayout.show(contentArea, "attendance"); openModal(() -> jButton7ActionPerformed(null)); });
        btnLeaveReq.addActionListener(e -> { setNavActive(btnLeaveReq); new com.gui.Leave.LeaveRequestForm(com.motorph.util.AppContext.getInstance().getCurrentEmployee()).setVisible(true); });
        btnLeaveStatus.addActionListener(e -> { setNavActive(btnLeaveStatus); cardLayout.show(contentArea, "leaveStatus"); });
        btnViewPayroll.addActionListener(e -> { setNavActive(btnViewPayroll); jButton10ActionPerformed(null); });

        if (btnPayroll != null) {
            final javax.swing.JButton bp = btnPayroll;
            bp.addActionListener(e -> { setNavActive(bp); jButton2ActionPerformed(null); });
        }
        if (btnEmployees != null) {
            final javax.swing.JButton be = btnEmployees;
            be.addActionListener(e -> { setNavActive(be); jButton3ActionPerformed(null); });
        }
        if (btnLeaveApproval != null) {
            final javax.swing.JButton bla = btnLeaveApproval;
            bla.addActionListener(e -> { setNavActive(bla); new com.gui.Leave.LeaveApprovalForm(com.motorph.util.AppContext.getInstance().getCurrentEmployee()).setVisible(true); });
        }
        if (btnSysMaint != null) {
            final javax.swing.JButton bsm = btnSysMaint;
            bsm.addActionListener(e -> { setNavActive(bsm); jButton9ActionPerformed(null); });
        }

        // Clock In / Clock Out actions (reuse existing logic)
        topClockIn.addActionListener(e -> {
            jButton4ActionPerformed(null);
            clockStatusLbl.setText("Status: IN");
            clockStatusLbl.setForeground(Theme.SUCCESS_GREEN);
            topClockIn.setBackground(new java.awt.Color(0xCBD5E1));
            topClockIn.setForeground(new java.awt.Color(0x64748B));
            topClockOut.setBackground(Theme.DANGER_RED);
            topClockOut.setForeground(Theme.TEXT_ON_DARK);
        });
        topClockOut.addActionListener(e -> {
            jButton5ActionPerformed(null);
            clockStatusLbl.setText("Status: OUT");
            clockStatusLbl.setForeground(Theme.DANGER_RED);
            topClockOut.setBackground(new java.awt.Color(0xCBD5E1));
            topClockOut.setForeground(new java.awt.Color(0x64748B));
            topClockIn.setBackground(Theme.SUCCESS_GREEN);
            topClockIn.setForeground(Theme.TEXT_ON_DARK);
        });
        btnLogout.addActionListener(e -> jButton6ActionPerformed(null));

        // ── Assemble root ──────────────────────────────────────────────────
        javax.swing.JPanel mainArea = new javax.swing.JPanel(new java.awt.BorderLayout());
        mainArea.setBackground(Theme.PAGE_BG);
        mainArea.add(topBar,     java.awt.BorderLayout.NORTH);
        mainArea.add(contentArea, java.awt.BorderLayout.CENTER);

        cp.add(sidebar,  java.awt.BorderLayout.WEST);
        cp.add(mainArea, java.awt.BorderLayout.CENTER);

        pack();
        setSize(1100, 720);
        setLocationRelativeTo(null);
    }

    /** Make a sidebar nav button. accent=null uses default sidebar style. */
    private javax.swing.JButton makeNavBtn(String text, java.awt.Color accent) {
        javax.swing.JButton btn = new javax.swing.JButton(text);
        btn.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 40));
        btn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn.setFont(Theme.FONT_BODY);
        btn.setBackground(Theme.SIDEBAR_BG);
        btn.setForeground(new java.awt.Color(0xCBD5E1));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        btn.setBorder(javax.swing.BorderFactory.createEmptyBorder(9, 20, 9, 16));
        final java.awt.Color base = Theme.SIDEBAR_BG;
        final java.awt.Color hover = new java.awt.Color(0x1E293B);
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { if (btn != activeNavBtn) btn.setBackground(hover); }
            public void mouseExited(java.awt.event.MouseEvent e)  { if (btn != activeNavBtn) btn.setBackground(base);  }
        });
        return btn;
    }

    /** Highlight the active nav button, reset others. */
    private void setNavActive(javax.swing.JButton btn) {
        if (activeNavBtn != null) {
            activeNavBtn.setBackground(Theme.SIDEBAR_BG);
            activeNavBtn.setForeground(new java.awt.Color(0xCBD5E1));
            activeNavBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder(9, 20, 9, 16));
        }
        activeNavBtn = btn;
        btn.setBackground(new java.awt.Color(0x1E40AF));   // active blue
        btn.setForeground(Theme.TEXT_ON_DARK);
        // Left accent bar
        btn.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createMatteBorder(0, 3, 0, 0, new java.awt.Color(0x60A5FA)),
            javax.swing.BorderFactory.createEmptyBorder(9, 17, 9, 16)
        ));
    }

    /** Open a modal action without navigating the card deck. */
    private void openModal(Runnable r) { r.run(); }

    // ════════════════════════════════════════════════════════════════════════
    //  PROFILE CARD
    // ════════════════════════════════════════════════════════════════════════
    private javax.swing.JPanel buildProfileCard() {
        javax.swing.JPanel root = new javax.swing.JPanel(new java.awt.BorderLayout(0, 0));
        root.setBackground(Theme.PAGE_BG);

        // Page title
        javax.swing.JLabel titleLbl = new javax.swing.JLabel("  My Profile");
        titleLbl.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 20));
        titleLbl.setForeground(Theme.TEXT_PRIMARY);
        titleLbl.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 18, 8, 18));
        root.add(titleLbl, java.awt.BorderLayout.NORTH);

        // Scrollable body
        javax.swing.JPanel body = new javax.swing.JPanel(new java.awt.BorderLayout(16, 16));
        body.setBackground(Theme.PAGE_BG);
        body.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 18, 18, 18));

        // ── Left: Photo + upload + name ──────────────────────────────────────
        javax.swing.JPanel photoPane = new javax.swing.JPanel();
        photoPane.setLayout(new javax.swing.BoxLayout(photoPane, javax.swing.BoxLayout.Y_AXIS));
        photoPane.setBackground(Theme.CARD_BG);
        photoPane.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(Theme.BORDER_COLOR, 1),
            javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        photoPane.setPreferredSize(new java.awt.Dimension(200, 0));

        // Reuse jLabel5 for the profile photo
        jLabel5 = new javax.swing.JLabel();
        jLabel5.setPreferredSize(new java.awt.Dimension(120, 120));
        jLabel5.setMinimumSize(new java.awt.Dimension(120, 120));
        jLabel5.setMaximumSize(new java.awt.Dimension(120, 120));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setBackground(new java.awt.Color(0xE2E8F0));
        jLabel5.setOpaque(true);
        jLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(Theme.BORDER_COLOR, 1));
        jLabel5.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);

        String empFullName = (currentUser != null)
                ? currentUser.getuFirstName() + " " + currentUser.getuLastName() : "";
        javax.swing.JLabel photoName = new javax.swing.JLabel("<html><center>" + empFullName + "</center></html>",
                javax.swing.SwingConstants.CENTER);
        photoName.setFont(Theme.FONT_HEADER);
        photoName.setForeground(Theme.TEXT_PRIMARY);
        photoName.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);

        String pos = (currentUser != null) ? currentUser.getuPosition() : "";
        javax.swing.JLabel photoPos = new javax.swing.JLabel("<html><center>" + pos + "</center></html>",
                javax.swing.SwingConstants.CENTER);
        photoPos.setFont(Theme.FONT_SMALL);
        photoPos.setForeground(Theme.TEXT_MUTED);
        photoPos.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);

        jButton8 = new javax.swing.JButton("Upload Photo");
        jButton8.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        jButton8.setMaximumSize(new java.awt.Dimension(150, 30));
        Theme.secondaryButton(jButton8);
        jButton8.addActionListener(e -> jButton8ActionPerformed(null));

        photoPane.add(javax.swing.Box.createVerticalStrut(4));
        photoPane.add(jLabel5);
        photoPane.add(javax.swing.Box.createVerticalStrut(12));
        photoPane.add(photoName);
        photoPane.add(javax.swing.Box.createVerticalStrut(4));
        photoPane.add(photoPos);
        photoPane.add(javax.swing.Box.createVerticalStrut(16));
        photoPane.add(jButton8);

        SwingUtilities.invokeLater(() -> setProfileImage(jLabel5, currentUser != null ? currentUser.getuEmpId() : ""));

        // ── Right: Detail sections ────────────────────────────────────────────
        javax.swing.JPanel rightPane = new javax.swing.JPanel();
        rightPane.setLayout(new javax.swing.BoxLayout(rightPane, javax.swing.BoxLayout.Y_AXIS));
        rightPane.setBackground(Theme.PAGE_BG);

        // Personal Info section
        rightPane.add(buildInfoSection("Personal Information", new String[][]{
            {"Employee ID:",  (currentUser != null) ? currentUser.getuEmpId() : ""},
            {"Full Name:",    (currentUser != null) ? currentUser.getuFirstName() + " " + currentUser.getuLastName() : ""},
            {"Birthday:",     (currentUser != null) ? currentUser.getuDob() : ""},
            {"Address:",      (currentUser != null) ? currentUser.getuAddress() : ""},
            {"Phone:",        (currentUser != null) ? currentUser.getuPhoneNumber() : ""}
        }));
        rightPane.add(javax.swing.Box.createVerticalStrut(12));

        // Employment Details
        rightPane.add(buildInfoSection("Employment Details", new String[][]{
            {"Status:",     (currentUser != null) ? currentUser.getuStatus() : ""},
            {"Position:",   (currentUser != null) ? currentUser.getuPosition() : ""},
            {"Supervisor:", (currentUser != null) ? currentUser.getuImmediateSupervisor() : ""}
        }));
        rightPane.add(javax.swing.Box.createVerticalStrut(12));

        // Government IDs
        rightPane.add(buildInfoSection("Government IDs", new String[][]{
            {"SSS #:",       (currentUser != null) ? currentUser.getuSSS() : ""},
            {"PhilHealth #:", (currentUser != null) ? currentUser.getuPhilHealth() : ""},
            {"TIN #:",       (currentUser != null) ? currentUser.getuTIN() : ""},
            {"Pag-IBIG #:",  (currentUser != null) ? currentUser.getuPagIbig() : ""}
        }));

        javax.swing.JScrollPane rightScroll = new javax.swing.JScrollPane(rightPane,
                javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        rightScroll.setBorder(null);
        rightScroll.getViewport().setBackground(Theme.PAGE_BG);

        body.add(photoPane,   java.awt.BorderLayout.WEST);
        body.add(rightScroll, java.awt.BorderLayout.CENTER);

        root.add(body, java.awt.BorderLayout.CENTER);
        return root;
    }

    /** Build a labelled info section card. rows = {{"Label", "value"}, ...} */
    private javax.swing.JPanel buildInfoSection(String title, String[][] rows) {
        javax.swing.JPanel card = new javax.swing.JPanel(new java.awt.BorderLayout(0, 4));
        card.setBackground(Theme.CARD_BG);
        card.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(Theme.BORDER_COLOR, 1),
            javax.swing.BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));
        card.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        card.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, card.getPreferredSize().height + 300));

        javax.swing.JLabel titleLbl = new javax.swing.JLabel(title);
        titleLbl.setFont(Theme.FONT_HEADER);
        titleLbl.setForeground(Theme.PRIMARY_BLUE);
        titleLbl.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDER_COLOR),
            javax.swing.BorderFactory.createEmptyBorder(0, 0, 8, 0)
        ));
        card.add(titleLbl, java.awt.BorderLayout.NORTH);

        javax.swing.JPanel grid = new javax.swing.JPanel(new java.awt.GridLayout(rows.length, 2, 8, 4));
        grid.setBackground(Theme.CARD_BG);
        grid.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 0, 0, 0));

        for (String[] row : rows) {
            javax.swing.JLabel keyLbl = new javax.swing.JLabel(row[0]);
            keyLbl.setFont(Theme.FONT_BODY);
            keyLbl.setForeground(Theme.TEXT_MUTED);

            javax.swing.JLabel valLbl = new javax.swing.JLabel(row[1]);
            valLbl.setFont(Theme.FONT_BODY);
            valLbl.setForeground(Theme.TEXT_PRIMARY);

            grid.add(keyLbl);
            grid.add(valLbl);
        }
        card.add(grid, java.awt.BorderLayout.CENTER);
        return card;
    }

    /** Build a placeholder card for nav items that open a modal. */
    private javax.swing.JPanel buildPlaceholderCard(String title, String icon, String desc, java.awt.Color accent) {
        javax.swing.JPanel p = new javax.swing.JPanel(new java.awt.GridBagLayout());
        p.setBackground(Theme.PAGE_BG);

        javax.swing.JPanel card = new javax.swing.JPanel();
        card.setLayout(new javax.swing.BoxLayout(card, javax.swing.BoxLayout.Y_AXIS));
        card.setBackground(Theme.CARD_BG);
        card.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createMatteBorder(4, 0, 0, 0, accent),
            javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(Theme.BORDER_COLOR, 1),
                javax.swing.BorderFactory.createEmptyBorder(30, 40, 30, 40)
            )
        ));

        javax.swing.JLabel iconLbl = new javax.swing.JLabel(icon);
        iconLbl.setFont(new java.awt.Font("Segoe UI Emoji", java.awt.Font.PLAIN, 40));
        iconLbl.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);

        javax.swing.JLabel titleLbl = new javax.swing.JLabel(title);
        titleLbl.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
        titleLbl.setForeground(accent);
        titleLbl.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);

        javax.swing.JLabel descLbl = new javax.swing.JLabel("<html><center>" + desc + "</center></html>");
        descLbl.setFont(Theme.FONT_BODY);
        descLbl.setForeground(Theme.TEXT_MUTED);
        descLbl.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);

        card.add(iconLbl);
        card.add(javax.swing.Box.createVerticalStrut(10));
        card.add(titleLbl);
        card.add(javax.swing.Box.createVerticalStrut(6));
        card.add(descLbl);

        p.add(card);
        return p;
    }

    // ════════════════════════════════════════════════════════════════════════
    //  KEPT (legacy stubs — called by generated action handlers)
    // ════════════════════════════════════════════════════════════════════════
    private void applyTheme() { /* replaced by buildDashboard() */ }
    private void addLeaveButtons() { /* replaced by buildDashboard() */ }
    private void setupJPanel2() { /* replaced by buildProfileCard() */ }
    private javax.swing.JLabel makeDetailRow(String f, Object v) { return new javax.swing.JLabel(); }
    private double parseDoubleOrDefault(Object o) {
        try { return Double.parseDouble(o.toString()); } catch (Exception e) { return 0; }
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
