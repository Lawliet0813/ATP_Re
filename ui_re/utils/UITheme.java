package ui.utils;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * Centralized UI Theme Configuration
 * 
 * This class provides a consistent color palette and styling for the ATP application.
 * It supports better visual consistency and accessibility across all UI components.
 * 
 * @version 1.0
 * @date 2025-10-29
 */
public class UITheme {
    
    // Primary Color Palette (WCAG AA compliant)
    public static final Color PRIMARY_COLOR = new Color(0, 102, 204);          // Blue - Main brand color
    public static final Color PRIMARY_DARK = new Color(0, 76, 153);            // Dark blue - Headers, emphasis
    public static final Color PRIMARY_LIGHT = new Color(204, 229, 255);        // Light blue - Backgrounds
    
    // Secondary Colors (WCAG AA compliant)
    public static final Color SECONDARY_COLOR = new Color(46, 125, 50);        // Darker green - Success states
    public static final Color SECONDARY_DARK = new Color(27, 94, 32);          // Very dark green
    public static final Color SECONDARY_LIGHT = new Color(220, 237, 200);      // Light green
    
    // Neutral Colors
    public static final Color NEUTRAL_DARK = new Color(33, 33, 33);            // Almost black - Text
    public static final Color NEUTRAL_MEDIUM = new Color(117, 117, 117);       // Gray - Secondary text
    public static final Color NEUTRAL_LIGHT = new Color(245, 245, 245);        // Light gray - Backgrounds
    public static final Color NEUTRAL_BORDER = new Color(224, 224, 224);       // Borders
    
    // Semantic Colors (WCAG AA compliant with white text)
    public static final Color SUCCESS_COLOR = new Color(46, 125, 50);          // Darker green for better contrast
    public static final Color WARNING_COLOR = new Color(191, 54, 12);          // Darker orange for better contrast
    public static final Color ERROR_COLOR = new Color(198, 40, 40);            // Darker red for better contrast
    public static final Color INFO_COLOR = new Color(13, 71, 161);             // Darker blue for better contrast
    
    // Background Colors
    public static final Color BACKGROUND_PRIMARY = Color.WHITE;
    public static final Color BACKGROUND_SECONDARY = NEUTRAL_LIGHT;
    public static final Color BACKGROUND_HIGHLIGHT = PRIMARY_LIGHT;
    
    // Text Colors
    public static final Color TEXT_PRIMARY = NEUTRAL_DARK;
    public static final Color TEXT_SECONDARY = NEUTRAL_MEDIUM;
    public static final Color TEXT_ON_PRIMARY = Color.WHITE;
    public static final Color TEXT_DISABLED = new Color(189, 189, 189);
    
    // Focus and Selection Colors
    public static final Color FOCUS_COLOR = PRIMARY_COLOR;
    public static final Color SELECTION_BACKGROUND = PRIMARY_LIGHT;
    public static final Color SELECTION_FOREGROUND = PRIMARY_DARK;
    
    // Fonts
    public static final Font FONT_HEADER = new Font("SansSerif", Font.BOLD, 16);
    public static final Font FONT_SUBHEADER = new Font("SansSerif", Font.BOLD, 14);
    public static final Font FONT_NORMAL = new Font("SansSerif", Font.PLAIN, 12);
    public static final Font FONT_SMALL = new Font("SansSerif", Font.PLAIN, 10);
    public static final Font FONT_BUTTON = new Font("SansSerif", Font.BOLD, 12);
    
    // Borders
    public static final Border BORDER_DEFAULT = BorderFactory.createLineBorder(NEUTRAL_BORDER, 1);
    public static final Border BORDER_FOCUS = BorderFactory.createLineBorder(FOCUS_COLOR, 2);
    public static final Border BORDER_PANEL = BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(NEUTRAL_BORDER, 1),
        BorderFactory.createEmptyBorder(10, 10, 10, 10)
    );
    
    // Spacing constants
    public static final int SPACING_SMALL = 5;
    public static final int SPACING_MEDIUM = 10;
    public static final int SPACING_LARGE = 20;
    
    // Component sizes
    public static final int BUTTON_HEIGHT = 32;
    public static final int INPUT_HEIGHT = 28;
    public static final int ICON_SIZE = 24;
    
    /**
     * Private constructor to prevent instantiation
     */
    private UITheme() {
        throw new AssertionError("UITheme is a utility class and should not be instantiated");
    }
    
    /**
     * Returns a lighter version of the given color (for hover effects)
     * 
     * @param color The base color
     * @return A lighter version of the color
     */
    public static Color getLighterColor(Color color) {
        int r = Math.min(255, color.getRed() + 30);
        int g = Math.min(255, color.getGreen() + 30);
        int b = Math.min(255, color.getBlue() + 30);
        return new Color(r, g, b);
    }
    
    /**
     * Returns a darker version of the given color (for pressed effects)
     * 
     * @param color The base color
     * @return A darker version of the color
     */
    public static Color getDarkerColor(Color color) {
        int r = Math.max(0, color.getRed() - 30);
        int g = Math.max(0, color.getGreen() - 30);
        int b = Math.max(0, color.getBlue() - 30);
        return new Color(r, g, b);
    }
    
    /**
     * Checks if a color has sufficient contrast ratio with white text (WCAG AA: 4.5:1)
     * 
     * @param backgroundColor The background color to check
     * @return true if the color has sufficient contrast with white text
     */
    public static boolean hasSufficientContrastWithWhite(Color backgroundColor) {
        double contrast = getContrastRatio(backgroundColor, Color.WHITE);
        return contrast >= 4.5;
    }
    
    /**
     * Calculates the contrast ratio between two colors (WCAG 2.0)
     * 
     * @param c1 First color
     * @param c2 Second color
     * @return The contrast ratio (1 to 21)
     */
    public static double getContrastRatio(Color c1, Color c2) {
        double l1 = getRelativeLuminance(c1);
        double l2 = getRelativeLuminance(c2);
        double lighter = Math.max(l1, l2);
        double darker = Math.min(l1, l2);
        return (lighter + 0.05) / (darker + 0.05);
    }
    
    /**
     * Calculates the relative luminance of a color (WCAG 2.0)
     * 
     * @param color The color
     * @return The relative luminance (0 to 1)
     */
    private static double getRelativeLuminance(Color color) {
        double r = color.getRed() / 255.0;
        double g = color.getGreen() / 255.0;
        double b = color.getBlue() / 255.0;
        
        r = (r <= 0.03928) ? r / 12.92 : Math.pow((r + 0.055) / 1.055, 2.4);
        g = (g <= 0.03928) ? g / 12.92 : Math.pow((g + 0.055) / 1.055, 2.4);
        b = (b <= 0.03928) ? b / 12.92 : Math.pow((b + 0.055) / 1.055, 2.4);
        
        return 0.2126 * r + 0.7152 * g + 0.0722 * b;
    }
}
