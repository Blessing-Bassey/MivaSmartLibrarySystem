package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UITheme {
    public static final Color BG = new Color(245, 243, 239);
    public static final Color CARD = Color.WHITE;
    public static final Color MUTED = new Color(239, 236, 231);

    public static final Color GREEN_DARK = new Color(19, 54, 45);
    public static final Color GREEN = new Color(31, 88, 69);
    public static final Color GREEN_SOFT = new Color(230, 242, 235);

    public static final Color GOLD_SOFT = new Color(248, 242, 230);
    public static final Color RED_SOFT = new Color(250, 236, 236);
    public static final Color BLUE_SOFT = new Color(235, 242, 247);

    public static final Color TEXT_DARK = new Color(23, 35, 32);
    public static final Color TEXT_MUTED = new Color(102, 121, 113);
    public static final Color BORDER = new Color(222, 219, 214);

    public static Font titleFont(int size) {
        return new Font("Serif", Font.BOLD, size);
    }

    public static Font bodyFont(int size) {
        return new Font("SansSerif", Font.PLAIN, size);
    }

    public static Font bodyBoldFont(int size) {
        return new Font("SansSerif", Font.BOLD, size);
    }

    public static JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(GREEN);
        button.setForeground(Color.WHITE);
        button.setFont(bodyBoldFont(15));
        button.setBorder(new EmptyBorder(12, 18, 12, 18));
        return button;
    }

    public static JButton createSecondaryButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(MUTED);
        button.setForeground(TEXT_DARK);
        button.setFont(bodyBoldFont(15));
        button.setBorder(new EmptyBorder(12, 18, 12, 18));
        return button;
    }

    public static JLabel makeSectionTitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(titleFont(22));
        label.setForeground(TEXT_DARK);
        return label;
    }
}