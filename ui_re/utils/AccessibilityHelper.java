package ui.utils;

import javax.accessibility.Accessible;
import javax.accessibility.AccessibleContext;
import javax.swing.*;
import java.awt.*;

/**
 * Accessibility Helper for WCAG 2.1 compliance
 * 
 * This class provides utilities to improve accessibility for users with disabilities,
 * following Web Content Accessibility Guidelines (WCAG) 2.1 standards.
 * 
 * @version 1.0
 * @date 2025-10-29
 */
public class AccessibilityHelper {
    
    /**
     * Sets accessible name and description for a component
     * 
     * @param component The component to configure
     * @param name The accessible name
     * @param description The accessible description
     */
    public static void setAccessibleInfo(JComponent component, String name, String description) {
        AccessibleContext context = component.getAccessibleContext();
        if (context != null) {
            context.setAccessibleName(name);
            context.setAccessibleDescription(description);
        }
    }
    
    /**
     * Associates a label with a component for screen readers
     * 
     * @param label The label component
     * @param component The associated input component
     */
    public static void associateLabelWithComponent(JLabel label, JComponent component) {
        label.setLabelFor(component);
        
        // Set accessible name from label text if not already set
        AccessibleContext context = component.getAccessibleContext();
        if (context != null && (context.getAccessibleName() == null || context.getAccessibleName().isEmpty())) {
            context.setAccessibleName(label.getText());
        }
    }
    
    /**
     * Configures a button with accessible information
     * 
     * @param button The button to configure
     * @param name The accessible name
     * @param description The accessible description
     */
    public static void configureAccessibleButton(JButton button, String name, String description) {
        setAccessibleInfo(button, name, description);
        
        // Ensure button has tool tip for additional context
        if (button.getToolTipText() == null || button.getToolTipText().isEmpty()) {
            button.setToolTipText(description);
        }
    }
    
    /**
     * Configures a text field with accessible information
     * 
     * @param textField The text field to configure
     * @param name The accessible name
     * @param description The accessible description
     */
    public static void configureAccessibleTextField(JTextField textField, String name, String description) {
        setAccessibleInfo(textField, name, description);
        textField.setToolTipText(description);
    }
    
    /**
     * Configures high contrast mode for a component
     * 
     * @param component The component to configure
     */
    public static void enableHighContrast(JComponent component) {
        component.setForeground(UITheme.NEUTRAL_DARK);
        component.setBackground(Color.WHITE);
        
        if (component instanceof JButton) {
            JButton button = (JButton) component;
            button.setBorder(BorderFactory.createLineBorder(UITheme.NEUTRAL_DARK, 2));
        }
    }
    
    /**
     * Creates an accessible panel with title and description
     * 
     * @param title The panel title
     * @param description The panel description
     * @return The configured panel
     */
    public static JPanel createAccessiblePanel(String title, String description) {
        JPanel panel = new JPanel();
        setAccessibleInfo(panel, title, description);
        return panel;
    }
    
    /**
     * Sets up keyboard shortcuts with accessible descriptions
     * 
     * @param component The component
     * @param action The action to perform
     * @param keyStroke The key stroke
     * @param description The action description
     */
    public static void addAccessibleKeyboardShortcut(JComponent component, Action action, 
                                                      KeyStroke keyStroke, String description) {
        String actionKey = "action_" + System.currentTimeMillis();
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, actionKey);
        component.getActionMap().put(actionKey, action);
        
        // Add to accessible context
        AccessibleContext context = component.getAccessibleContext();
        if (context != null) {
            String currentDesc = context.getAccessibleDescription();
            if (currentDesc == null || currentDesc.isEmpty()) {
                context.setAccessibleDescription(description);
            } else {
                context.setAccessibleDescription(currentDesc + "; " + description);
            }
        }
    }
    
    /**
     * Validates color contrast ratios for accessibility
     * 
     * @param foreground The foreground color
     * @param background The background color
     * @return true if the contrast ratio meets WCAG AA standards (4.5:1)
     */
    public static boolean validateColorContrast(Color foreground, Color background) {
        double contrast = UITheme.getContrastRatio(foreground, background);
        return contrast >= 4.5;
    }
    
    /**
     * Suggests an accessible foreground color for the given background
     * 
     * @param background The background color
     * @return An accessible foreground color
     */
    public static Color getAccessibleForegroundColor(Color background) {
        if (UITheme.hasSufficientContrastWithWhite(background)) {
            return Color.WHITE;
        } else {
            return UITheme.NEUTRAL_DARK;
        }
    }
    
    /**
     * Configures a table for better accessibility
     * 
     * @param table The table to configure
     * @param tableDescription The description of the table
     */
    public static void configureAccessibleTable(JTable table, String tableDescription) {
        setAccessibleInfo(table, "Data table", tableDescription);
        
        // Enable keyboard navigation
        table.setFocusable(true);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);
        
        // Set accessible headers
        table.getTableHeader().setReorderingAllowed(false);
        
        // Configure row height for better readability
        table.setRowHeight(Math.max(table.getRowHeight(), UITheme.INPUT_HEIGHT));
    }
    
    /**
     * Creates an accessible combo box with proper labeling
     * 
     * @param comboBox The combo box to configure
     * @param name The accessible name
     * @param description The accessible description
     */
    public static void configureAccessibleComboBox(JComboBox<?> comboBox, String name, String description) {
        setAccessibleInfo(comboBox, name, description);
        comboBox.setToolTipText(description);
    }
    
    /**
     * Adds accessible error indication to a component
     * 
     * @param component The component with an error
     * @param errorMessage The error message
     */
    public static void indicateError(JComponent component, String errorMessage) {
        component.setBorder(BorderFactory.createLineBorder(UITheme.ERROR_COLOR, 2));
        component.setToolTipText(errorMessage);
        
        AccessibleContext context = component.getAccessibleContext();
        if (context != null) {
            String name = context.getAccessibleName();
            context.setAccessibleDescription("Error: " + errorMessage + 
                (name != null ? " in " + name : ""));
        }
    }
    
    /**
     * Clears error indication from a component
     * 
     * @param component The component to clear
     */
    public static void clearError(JComponent component) {
        component.setBorder(UITheme.BORDER_DEFAULT);
        
        AccessibleContext context = component.getAccessibleContext();
        if (context != null) {
            String desc = context.getAccessibleDescription();
            if (desc != null && desc.startsWith("Error: ")) {
                context.setAccessibleDescription("");
            }
        }
    }
    
    /**
     * Configures a dialog for accessibility
     * 
     * @param dialog The dialog to configure
     * @param title The dialog title
     * @param description The dialog description
     */
    public static void configureAccessibleDialog(JDialog dialog, String title, String description) {
        dialog.setTitle(title);
        AccessibleContext context = dialog.getAccessibleContext();
        if (context != null) {
            context.setAccessibleName(title);
            context.setAccessibleDescription(description);
        }
    }
    
    /**
     * Announces a message to screen readers
     * 
     * @param component The component to announce from
     * @param message The message to announce
     */
    public static void announceToScreenReader(JComponent component, String message) {
        AccessibleContext context = component.getAccessibleContext();
        if (context != null) {
            // Store current description
            String currentDesc = context.getAccessibleDescription();
            
            // Set announcement
            context.setAccessibleDescription(message);
            
            // Restore after a delay (this is a workaround for screen reader announcement)
            Timer timer = new Timer(1000, e -> {
                if (currentDesc != null) {
                    context.setAccessibleDescription(currentDesc);
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }
    
    /**
     * Checks if system high contrast mode is enabled
     * 
     * @return true if high contrast mode is enabled
     */
    public static boolean isHighContrastMode() {
        try {
            // Check system property (Windows)
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Boolean highContrast = (Boolean) toolkit.getDesktopProperty("win.highContrast.on");
            return highContrast != null && highContrast;
        } catch (Exception e) {
            return false;
        }
    }
}
