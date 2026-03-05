package com.gui.util;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Central theme constants and helper methods for MotorPH Payroll System.
 * Apply styling AFTER initComponents() — never touch the GEN-BEGIN regions.
 */
public class Theme {

    // ── Color Palette ────────────────────────────────────────────────────────
    /** Dark navy sidebar / login background */
    public static final Color SIDEBAR_BG   = new Color(0x1E293B);
    /** Primary blue accent */
    public static final Color PRIMARY_BLUE = new Color(0x1B5E7B);
    /** Lighter action blue (hover / button) */
    public static final Color ACTION_BLUE  = new Color(0x2563EB);
    /** Light page background */
    public static final Color PAGE_BG      = new Color(0xF1F5F9);
    /** White card surface */
    public static final Color CARD_BG      = Color.WHITE;
    /** Muted border / divider */
    public static final Color BORDER_COLOR = new Color(0xCBD5E1);
    /** Primary text on light background */
    public static final Color TEXT_PRIMARY  = new Color(0x1E293B);
    /** Secondary / muted text */
    public static final Color TEXT_MUTED    = new Color(0x64748B);
    /** White text for dark surfaces */
    public static final Color TEXT_ON_DARK  = Color.WHITE;
    /** Clock-In green */
    public static final Color SUCCESS_GREEN = new Color(0x16A34A);
    /** Clock-Out / error red */
    public static final Color DANGER_RED    = new Color(0xDC2626);
    /** Approve green */
    public static final Color APPROVE_GREEN = new Color(0x15803D);
    /** Reject red */
    public static final Color REJECT_RED    = new Color(0xB91C1C);
    /** Sidebar nav button hover */
    public static final Color SIDEBAR_HOVER = new Color(0x334155);
    /** Sidebar active item */
    public static final Color SIDEBAR_ACTIVE= new Color(0x1B5E7B);

    // ── Fonts ────────────────────────────────────────────────────────────────
    public static final Font FONT_TITLE  = new Font("Segoe UI", Font.BOLD, 20);
    public static final Font FONT_HEADER = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_BODY   = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_SMALL  = new Font("Segoe UI", Font.PLAIN, 11);
    public static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD,  12);
    public static final Font FONT_MONO   = new Font("Consolas",  Font.PLAIN, 12);

    // ── Borders ──────────────────────────────────────────────────────────────
    public static Border cardBorder() {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
            BorderFactory.createEmptyBorder(10, 14, 10, 14)
        );
    }

    public static Border inputBorder() {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        );
    }

    public static Border inputBorderFocused() {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_BLUE, 2, true),
            BorderFactory.createEmptyBorder(3, 7, 3, 7)
        );
    }

    // ── Component Helpers ────────────────────────────────────────────────────

    /** Style a primary (blue) action button. */
    public static void primaryButton(JButton btn) {
        btn.setBackground(PRIMARY_BLUE);
        btn.setForeground(TEXT_ON_DARK);
        btn.setFont(FONT_BUTTON);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
    }

    /** Style a sidebar navigation button. */
    public static void sidebarButton(JButton btn) {
        btn.setBackground(SIDEBAR_BG);
        btn.setForeground(TEXT_ON_DARK);
        btn.setFont(FONT_BODY);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(SIDEBAR_HOVER);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(SIDEBAR_BG);
            }
        });
    }

    /** Style a danger (red) button. */
    public static void dangerButton(JButton btn) {
        btn.setBackground(DANGER_RED);
        btn.setForeground(TEXT_ON_DARK);
        btn.setFont(FONT_BUTTON);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
    }

    /** Style a success (green) button. */
    public static void successButton(JButton btn) {
        btn.setBackground(SUCCESS_GREEN);
        btn.setForeground(TEXT_ON_DARK);
        btn.setFont(FONT_BUTTON);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
    }

    /** Style a secondary (outline-style) button. */
    public static void secondaryButton(JButton btn) {
        btn.setBackground(CARD_BG);
        btn.setForeground(PRIMARY_BLUE);
        btn.setFont(FONT_BUTTON);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_BLUE, 1, true),
            BorderFactory.createEmptyBorder(6, 14, 6, 14)
        ));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    /** Style a text field with the modern input border. */
    public static void styleTextField(JTextField field) {
        field.setFont(FONT_BODY);
        field.setBackground(CARD_BG);
        field.setForeground(TEXT_PRIMARY);
        field.setBorder(inputBorder());
    }

    /** Style a password field with the modern input border. */
    public static void stylePasswordField(JPasswordField field) {
        field.setFont(FONT_BODY);
        field.setBackground(CARD_BG);
        field.setForeground(TEXT_PRIMARY);
        field.setBorder(inputBorder());
    }

    /** Style a text area. */
    public static void styleTextArea(JTextArea area) {
        area.setFont(FONT_BODY);
        area.setBackground(CARD_BG);
        area.setForeground(TEXT_PRIMARY);
        area.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
    }

    /** Style a JScrollPane (table container). */
    public static void styleScrollPane(JScrollPane sp) {
        sp.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        sp.getViewport().setBackground(CARD_BG);
    }

    /** Style a JTable with alternating rows and modern fonts. */
    public static void styleTable(javax.swing.JTable table) {
        table.setFont(FONT_BODY);
        table.setForeground(TEXT_PRIMARY);
        table.setBackground(CARD_BG);
        table.setRowHeight(28);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(0xDBEAFE));
        table.setSelectionForeground(TEXT_PRIMARY);
        table.setGridColor(BORDER_COLOR);
        // Header
        javax.swing.table.JTableHeader header = table.getTableHeader();
        header.setFont(FONT_HEADER);
        header.setBackground(SIDEBAR_BG);
        header.setForeground(TEXT_ON_DARK);
        header.setBorder(BorderFactory.createEmptyBorder());
        // Alternating row colors via renderer
        table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    javax.swing.JTable t, Object value, boolean isSelected,
                    boolean hasFocus, int row, int col) {
                super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, col);
                setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                if (!isSelected) {
                    setBackground(row % 2 == 0 ? CARD_BG : new Color(0xF8FAFC));
                    setForeground(TEXT_PRIMARY);
                }
                return this;
            }
        });
    }

    /** Apply a card-style to any panel. */
    public static void styleCard(JPanel panel) {
        panel.setBackground(CARD_BG);
        panel.setBorder(cardBorder());
    }
}
