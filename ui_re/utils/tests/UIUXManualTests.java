package ui.utils.tests;

import ui.utils.UITheme;
import ui.utils.NavigationHelper;
import ui.utils.AccessibilityHelper;

import javax.swing.*;
import java.awt.*;

/**
 * Manual test suite for UI/UX improvements
 * 
 * This class provides manual testing utilities to verify that the UI/UX
 * improvements are working correctly.
 * 
 * Run this class to perform manual testing of:
 * - Color palette and contrast ratios
 * - Navigation helpers
 * - Accessibility features
 * 
 * @version 1.0
 * @date 2025-10-29
 */
public class UIUXManualTests {
    
    /**
     * Test color contrast ratios for WCAG compliance
     */
    public static void testColorContrast() {
        System.out.println("=== Color Contrast Tests ===\n");
        
        // Test primary color combinations
        testContrast("PRIMARY_COLOR on WHITE", 
                    UITheme.PRIMARY_COLOR, Color.WHITE);
        testContrast("WHITE on PRIMARY_COLOR", 
                    Color.WHITE, UITheme.PRIMARY_COLOR);
        
        // Test text colors
        testContrast("TEXT_PRIMARY on WHITE", 
                    UITheme.TEXT_PRIMARY, Color.WHITE);
        testContrast("TEXT_SECONDARY on WHITE", 
                    UITheme.TEXT_SECONDARY, Color.WHITE);
        
        // Test semantic colors
        testContrast("WHITE on ERROR_COLOR", 
                    Color.WHITE, UITheme.ERROR_COLOR);
        testContrast("WHITE on SUCCESS_COLOR", 
                    Color.WHITE, UITheme.SUCCESS_COLOR);
        testContrast("WHITE on WARNING_COLOR", 
                    Color.WHITE, UITheme.WARNING_COLOR);
        testContrast("WHITE on INFO_COLOR", 
                    Color.WHITE, UITheme.INFO_COLOR);
        
        System.out.println();
    }
    
    /**
     * Helper method to test and print contrast ratio
     */
    private static void testContrast(String description, Color foreground, Color background) {
        double ratio = UITheme.getContrastRatio(foreground, background);
        String level = ratio >= 7.0 ? "AAA" : (ratio >= 4.5 ? "AA" : "FAIL");
        String status = ratio >= 4.5 ? "PASS" : "FAIL";
        
        System.out.printf("%-40s Ratio: %.2f:1 [%s] %s%n", 
                         description, ratio, level, status);
    }
    
    /**
     * Test color utility methods
     */
    public static void testColorUtilities() {
        System.out.println("=== Color Utility Tests ===\n");
        
        // Test lighter color
        Color lighter = UITheme.getLighterColor(UITheme.PRIMARY_COLOR);
        System.out.printf("Original: %s%n", colorToString(UITheme.PRIMARY_COLOR));
        System.out.printf("Lighter:  %s%n", colorToString(lighter));
        System.out.println();
        
        // Test darker color
        Color darker = UITheme.getDarkerColor(UITheme.PRIMARY_COLOR);
        System.out.printf("Original: %s%n", colorToString(UITheme.PRIMARY_COLOR));
        System.out.printf("Darker:   %s%n", colorToString(darker));
        System.out.println();
        
        // Test sufficient contrast check
        boolean hasContrast = UITheme.hasSufficientContrastWithWhite(UITheme.PRIMARY_COLOR);
        System.out.printf("PRIMARY_COLOR has sufficient contrast with white: %s%n", 
                         hasContrast ? "YES" : "NO");
        System.out.println();
    }
    
    /**
     * Helper to convert color to string
     */
    private static String colorToString(Color color) {
        return String.format("RGB(%d, %d, %d) #%02X%02X%02X", 
                           color.getRed(), color.getGreen(), color.getBlue(),
                           color.getRed(), color.getGreen(), color.getBlue());
    }
    
    /**
     * Test accessibility validation
     */
    public static void testAccessibilityValidation() {
        System.out.println("=== Accessibility Validation Tests ===\n");
        
        // Test color contrast validation
        boolean valid1 = AccessibilityHelper.validateColorContrast(
            UITheme.TEXT_PRIMARY, UITheme.BACKGROUND_PRIMARY
        );
        System.out.printf("TEXT_PRIMARY on BACKGROUND_PRIMARY: %s%n", 
                         valid1 ? "VALID" : "INVALID");
        
        boolean valid2 = AccessibilityHelper.validateColorContrast(
            UITheme.TEXT_SECONDARY, UITheme.BACKGROUND_PRIMARY
        );
        System.out.printf("TEXT_SECONDARY on BACKGROUND_PRIMARY: %s%n", 
                         valid2 ? "VALID" : "INVALID");
        
        // Test accessible foreground color suggestion
        Color fgForPrimary = AccessibilityHelper.getAccessibleForegroundColor(
            UITheme.PRIMARY_COLOR
        );
        System.out.printf("Recommended foreground for PRIMARY_COLOR: %s%n", 
                         colorToString(fgForPrimary));
        
        Color fgForLight = AccessibilityHelper.getAccessibleForegroundColor(
            UITheme.NEUTRAL_LIGHT
        );
        System.out.printf("Recommended foreground for NEUTRAL_LIGHT: %s%n", 
                         colorToString(fgForLight));
        
        // Test high contrast mode detection
        boolean highContrast = AccessibilityHelper.isHighContrastMode();
        System.out.printf("%nHigh contrast mode: %s%n", 
                         highContrast ? "ENABLED" : "DISABLED");
        
        System.out.println();
    }
    
    /**
     * Test theme constants
     */
    public static void testThemeConstants() {
        System.out.println("=== Theme Constants Tests ===\n");
        
        System.out.println("Spacing:");
        System.out.printf("  SPACING_SMALL:  %dpx%n", UITheme.SPACING_SMALL);
        System.out.printf("  SPACING_MEDIUM: %dpx%n", UITheme.SPACING_MEDIUM);
        System.out.printf("  SPACING_LARGE:  %dpx%n", UITheme.SPACING_LARGE);
        System.out.println();
        
        System.out.println("Component Sizes:");
        System.out.printf("  BUTTON_HEIGHT: %dpx%n", UITheme.BUTTON_HEIGHT);
        System.out.printf("  INPUT_HEIGHT:  %dpx%n", UITheme.INPUT_HEIGHT);
        System.out.printf("  ICON_SIZE:     %dpx%n", UITheme.ICON_SIZE);
        System.out.println();
        
        System.out.println("Fonts:");
        System.out.printf("  FONT_HEADER:    %s%n", fontToString(UITheme.FONT_HEADER));
        System.out.printf("  FONT_SUBHEADER: %s%n", fontToString(UITheme.FONT_SUBHEADER));
        System.out.printf("  FONT_NORMAL:    %s%n", fontToString(UITheme.FONT_NORMAL));
        System.out.printf("  FONT_SMALL:     %s%n", fontToString(UITheme.FONT_SMALL));
        System.out.printf("  FONT_BUTTON:    %s%n", fontToString(UITheme.FONT_BUTTON));
        System.out.println();
    }
    
    /**
     * Helper to convert font to string
     */
    private static String fontToString(Font font) {
        String style = font.isBold() ? "Bold" : (font.isItalic() ? "Italic" : "Plain");
        return String.format("%s, %s, %dpt", font.getFamily(), style, font.getSize());
    }
    
    /**
     * Print color palette
     */
    public static void testColorPalette() {
        System.out.println("=== Color Palette ===\n");
        
        System.out.println("Primary Colors:");
        printColor("PRIMARY_COLOR", UITheme.PRIMARY_COLOR);
        printColor("PRIMARY_DARK", UITheme.PRIMARY_DARK);
        printColor("PRIMARY_LIGHT", UITheme.PRIMARY_LIGHT);
        System.out.println();
        
        System.out.println("Secondary Colors:");
        printColor("SECONDARY_COLOR", UITheme.SECONDARY_COLOR);
        printColor("SECONDARY_DARK", UITheme.SECONDARY_DARK);
        printColor("SECONDARY_LIGHT", UITheme.SECONDARY_LIGHT);
        System.out.println();
        
        System.out.println("Neutral Colors:");
        printColor("NEUTRAL_DARK", UITheme.NEUTRAL_DARK);
        printColor("NEUTRAL_MEDIUM", UITheme.NEUTRAL_MEDIUM);
        printColor("NEUTRAL_LIGHT", UITheme.NEUTRAL_LIGHT);
        printColor("NEUTRAL_BORDER", UITheme.NEUTRAL_BORDER);
        System.out.println();
        
        System.out.println("Semantic Colors:");
        printColor("SUCCESS_COLOR", UITheme.SUCCESS_COLOR);
        printColor("WARNING_COLOR", UITheme.WARNING_COLOR);
        printColor("ERROR_COLOR", UITheme.ERROR_COLOR);
        printColor("INFO_COLOR", UITheme.INFO_COLOR);
        System.out.println();
        
        System.out.println("Text Colors:");
        printColor("TEXT_PRIMARY", UITheme.TEXT_PRIMARY);
        printColor("TEXT_SECONDARY", UITheme.TEXT_SECONDARY);
        printColor("TEXT_ON_PRIMARY", UITheme.TEXT_ON_PRIMARY);
        printColor("TEXT_DISABLED", UITheme.TEXT_DISABLED);
        System.out.println();
    }
    
    /**
     * Helper to print color information
     */
    private static void printColor(String name, Color color) {
        System.out.printf("  %-20s %s%n", name, colorToString(color));
    }
    
    /**
     * Run all tests
     */
    public static void runAllTests() {
        System.out.println("================================================================");
        System.out.println("  ATP UI/UX Manual Test Suite");
        System.out.println("================================================================");
        System.out.println();
        
        testColorPalette();
        testColorContrast();
        testColorUtilities();
        testAccessibilityValidation();
        testThemeConstants();
        
        System.out.println("================================================================");
        System.out.println("  All Tests Complete");
        System.out.println("================================================================");
    }
    
    /**
     * Main method to run tests
     */
    public static void main(String[] args) {
        runAllTests();
    }
}
