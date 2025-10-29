package ui.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Navigation Helper for improved application navigation flow
 * 
 * This class provides utilities to enhance keyboard navigation and
 * navigation flow across the ATP application.
 * 
 * @version 1.0
 * @date 2025-10-29
 */
public class NavigationHelper {
    
    /**
     * Sets up keyboard mnemonics for menu items to improve navigation
     * 
     * @param menu The menu to configure
     * @param mnemonic The keyboard mnemonic key
     */
    public static void setMenuMnemonic(JMenu menu, int mnemonic) {
        menu.setMnemonic(mnemonic);
    }
    
    /**
     * Sets up keyboard mnemonics for menu items
     * 
     * @param menuItem The menu item to configure
     * @param mnemonic The keyboard mnemonic key
     */
    public static void setMenuItemMnemonic(JMenuItem menuItem, int mnemonic) {
        menuItem.setMnemonic(mnemonic);
    }
    
    /**
     * Sets up keyboard accelerators for menu items
     * 
     * @param menuItem The menu item to configure
     * @param keyCode The key code (e.g., KeyEvent.VK_N)
     * @param modifiers The modifiers (e.g., KeyEvent.CTRL_DOWN_MASK)
     */
    public static void setMenuItemAccelerator(JMenuItem menuItem, int keyCode, int modifiers) {
        menuItem.setAccelerator(KeyStroke.getKeyStroke(keyCode, modifiers));
    }
    
    /**
     * Sets up a button with mnemonic for keyboard navigation
     * 
     * @param button The button to configure
     * @param mnemonic The keyboard mnemonic key
     */
    public static void setButtonMnemonic(JButton button, int mnemonic) {
        button.setMnemonic(mnemonic);
    }
    
    /**
     * Configures focus traversal order for a container
     * 
     * @param container The container to configure
     * @param components The components in desired focus order
     */
    public static void setFocusTraversalOrder(Container container, Component... components) {
        container.setFocusTraversalPolicy(new CustomFocusTraversalPolicy(components));
        container.setFocusCycleRoot(true);
    }
    
    /**
     * Adds visual focus indicators to a component
     * 
     * @param component The component to enhance
     */
    public static void addFocusIndicator(JComponent component) {
        component.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                component.setBorder(UITheme.BORDER_FOCUS);
            }
            
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                component.setBorder(UITheme.BORDER_DEFAULT);
            }
        });
    }
    
    /**
     * Creates a navigation breadcrumb panel
     * 
     * @param paths The breadcrumb path items
     * @return A panel containing the breadcrumb navigation
     */
    public static JPanel createBreadcrumb(String... paths) {
        JPanel breadcrumbPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        breadcrumbPanel.setBackground(UITheme.BACKGROUND_PRIMARY);
        
        for (int i = 0; i < paths.length; i++) {
            JLabel label = new JLabel(paths[i]);
            label.setFont(UITheme.FONT_NORMAL);
            label.setForeground(UITheme.TEXT_PRIMARY);
            breadcrumbPanel.add(label);
            
            if (i < paths.length - 1) {
                JLabel separator = new JLabel(" > ");
                separator.setForeground(UITheme.NEUTRAL_MEDIUM);
                breadcrumbPanel.add(separator);
            }
        }
        
        return breadcrumbPanel;
    }
    
    /**
     * Configures standard keyboard shortcuts for a window
     * 
     * @param frame The frame to configure
     */
    public static void setupStandardKeyBindings(JFrame frame) {
        // Close window with Escape key
        JRootPane rootPane = frame.getRootPane();
        rootPane.registerKeyboardAction(
            e -> frame.dispose(),
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_IN_FOCUSED_WINDOW
        );
    }
    
    /**
     * Configures standard keyboard shortcuts for a dialog
     * 
     * @param dialog The dialog to configure
     */
    public static void setupStandardKeyBindings(JDialog dialog) {
        // Close dialog with Escape key
        JRootPane rootPane = dialog.getRootPane();
        rootPane.registerKeyboardAction(
            e -> dialog.dispose(),
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_IN_FOCUSED_WINDOW
        );
    }
    
    /**
     * Sets a default button for Enter key activation
     * 
     * @param frame The frame
     * @param button The button to set as default
     */
    public static void setDefaultButton(JFrame frame, JButton button) {
        frame.getRootPane().setDefaultButton(button);
    }
    
    /**
     * Sets a default button for Enter key activation in a dialog
     * 
     * @param dialog The dialog
     * @param button The button to set as default
     */
    public static void setDefaultButton(JDialog dialog, JButton button) {
        dialog.getRootPane().setDefaultButton(button);
    }
    
    /**
     * Custom focus traversal policy for explicit focus order
     */
    private static class CustomFocusTraversalPolicy extends FocusTraversalPolicy {
        private final List<Component> components;
        
        public CustomFocusTraversalPolicy(Component... components) {
            this.components = new ArrayList<>();
            for (Component c : components) {
                if (c.isFocusable()) {
                    this.components.add(c);
                }
            }
        }
        
        @Override
        public Component getComponentAfter(Container container, Component component) {
            int index = components.indexOf(component);
            if (index >= 0 && index < components.size() - 1) {
                return components.get(index + 1);
            }
            return components.isEmpty() ? null : components.get(0);
        }
        
        @Override
        public Component getComponentBefore(Container container, Component component) {
            int index = components.indexOf(component);
            if (index > 0) {
                return components.get(index - 1);
            }
            return components.isEmpty() ? null : components.get(components.size() - 1);
        }
        
        @Override
        public Component getFirstComponent(Container container) {
            return components.isEmpty() ? null : components.get(0);
        }
        
        @Override
        public Component getLastComponent(Container container) {
            return components.isEmpty() ? null : components.get(components.size() - 1);
        }
        
        @Override
        public Component getDefaultComponent(Container container) {
            return getFirstComponent(container);
        }
    }
}
