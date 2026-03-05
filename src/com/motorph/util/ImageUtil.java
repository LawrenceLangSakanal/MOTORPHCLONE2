package com.motorph.util;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Utility methods for loading and scaling images in Swing labels.
 */
public final class ImageUtil {

    private ImageUtil() {}

    /**
     * Scale an image from the classpath resource and apply it to a JLabel,
     * preserving aspect ratio to fit within the label's current bounds.
     *
     * @param label        target label
     * @param resourcePath classpath resource path (e.g. "/com/gui/images/...")
     */
    public static void setScaledImage(JLabel label, String resourcePath) {
        java.net.URL url = ImageUtil.class.getResource(resourcePath);
        if (url == null) {
            label.setText("No Image");
            label.setIcon(null);
            return;
        }
        setScaledImage(label, new ImageIcon(url));
    }

    /**
     * Scale an {@link ImageIcon} to fit within the label's preferred bounds and
     * apply it.
     */
    public static void setScaledImage(JLabel label, ImageIcon icon) {
        int w = label.getWidth()  > 0 ? label.getWidth()  : label.getPreferredSize().width;
        int h = label.getHeight() > 0 ? label.getHeight() : label.getPreferredSize().height;
        if (w <= 0) w = 150;
        if (h <= 0) h = 150;

        float aspect = (float) icon.getIconWidth() / icon.getIconHeight();
        if (w / (float) h > aspect) {
            w = (int) (h * aspect);
        } else {
            h = (int) (w / aspect);
        }

        Image scaled = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(scaled));
        label.setText("");
        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
    }

    /**
     * Scale an image from a file-system path (for uploaded photos).
     */
    public static void setScaledImageFromFile(JLabel label, String filePath) {
        if (filePath == null || filePath.isBlank()) {
            label.setText("No Photo");
            label.setIcon(null);
            return;
        }
        java.io.File f = new java.io.File(filePath);
        if (!f.exists()) {
            label.setText("No Photo");
            label.setIcon(null);
            return;
        }
        setScaledImage(label, new ImageIcon(filePath));
    }
}
