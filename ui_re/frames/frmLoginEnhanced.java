package ui.frames;

import ui.utils.UITheme;
import ui.utils.NavigationHelper;
import ui.utils.AccessibilityHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Enhanced Login Form with improved UI/UX
 * 
 * DEMONSTRATION CLASS - This is an example implementation showing how to use
 * the UI/UX utilities. It is NOT intended for production use.
 * 
 * This class demonstrates:
 * - Consistent color palette from UITheme
 * - Keyboard navigation with mnemonics
 * - Accessibility support for screen readers
 * - Clear focus indicators
 * - WCAG 2.1 AA compliant design
 * 
 * For production use, integrate these patterns into the existing frmLogin.java
 * instead of using this demonstration class.
 * 
 * @version 1.0
 * @date 2025-10-29
 */
public class frmLoginEnhanced extends JFrame {
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton cancelButton;
    private JLabel statusLabel;
    
    public frmLoginEnhanced() {
        super("ATP Management Workstation - Login");
        initComponents();
        setupLayout();
        setupNavigation();
        setupAccessibility();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    /**
     * Initialize all UI components
     */
    private void initComponents() {
        // Username field
        usernameField = new JTextField(20);
        usernameField.setFont(UITheme.FONT_NORMAL);
        usernameField.setPreferredSize(new Dimension(250, UITheme.INPUT_HEIGHT));
        
        // Password field
        passwordField = new JPasswordField(20);
        passwordField.setFont(UITheme.FONT_NORMAL);
        passwordField.setPreferredSize(new Dimension(250, UITheme.INPUT_HEIGHT));
        
        // Login button
        loginButton = new JButton("Login");
        loginButton.setFont(UITheme.FONT_BUTTON);
        loginButton.setBackground(UITheme.PRIMARY_COLOR);
        loginButton.setForeground(UITheme.TEXT_ON_PRIMARY);
        loginButton.setPreferredSize(new Dimension(100, UITheme.BUTTON_HEIGHT));
        loginButton.setFocusPainted(true);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });
        
        // Cancel button
        cancelButton = new JButton("Cancel");
        cancelButton.setFont(UITheme.FONT_BUTTON);
        cancelButton.setBackground(UITheme.NEUTRAL_LIGHT);
        cancelButton.setForeground(UITheme.TEXT_PRIMARY);
        cancelButton.setPreferredSize(new Dimension(100, UITheme.BUTTON_HEIGHT));
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performCancel();
            }
        });
        
        // Status label
        statusLabel = new JLabel(" ");
        statusLabel.setFont(UITheme.FONT_SMALL);
        statusLabel.setForeground(UITheme.ERROR_COLOR);
    }
    
    /**
     * Setup the layout of the form
     */
    private void setupLayout() {
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(UITheme.SPACING_LARGE, UITheme.SPACING_LARGE));
        contentPane.setBackground(UITheme.BACKGROUND_PRIMARY);
        contentPane.setBorder(BorderFactory.createEmptyBorder(
            UITheme.SPACING_LARGE, 
            UITheme.SPACING_LARGE, 
            UITheme.SPACING_LARGE, 
            UITheme.SPACING_LARGE
        ));
        
        // Header panel
        JPanel headerPanel = createHeaderPanel();
        contentPane.add(headerPanel, BorderLayout.NORTH);
        
        // Form panel
        JPanel formPanel = createFormPanel();
        contentPane.add(formPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = createButtonPanel();
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        
        setContentPane(contentPane);
    }
    
    /**
     * Create the header panel with title
     */
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(UITheme.BACKGROUND_PRIMARY);
        
        JLabel titleLabel = new JLabel("ATP Management Workstation", SwingConstants.CENTER);
        titleLabel.setFont(UITheme.FONT_HEADER);
        titleLabel.setForeground(UITheme.PRIMARY_DARK);
        panel.add(titleLabel, BorderLayout.CENTER);
        
        JLabel subtitleLabel = new JLabel("Please enter your credentials", SwingConstants.CENTER);
        subtitleLabel.setFont(UITheme.FONT_NORMAL);
        subtitleLabel.setForeground(UITheme.TEXT_SECONDARY);
        panel.add(subtitleLabel, BorderLayout.SOUTH);
        
        panel.setBorder(BorderFactory.createEmptyBorder(
            0, 0, UITheme.SPACING_LARGE, 0
        ));
        
        return panel;
    }
    
    /**
     * Create the form panel with input fields
     */
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(UITheme.BACKGROUND_PRIMARY);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(
            UITheme.SPACING_SMALL, 
            UITheme.SPACING_MEDIUM, 
            UITheme.SPACING_SMALL, 
            UITheme.SPACING_MEDIUM
        );
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Username label and field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(UITheme.FONT_NORMAL);
        usernameLabel.setForeground(UITheme.TEXT_PRIMARY);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(usernameLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(usernameField, gbc);
        
        // Password label and field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(UITheme.FONT_NORMAL);
        passwordLabel.setForeground(UITheme.TEXT_PRIMARY);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panel.add(passwordLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(passwordField, gbc);
        
        // Status label
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(UITheme.SPACING_MEDIUM, 0, 0, 0);
        panel.add(statusLabel, gbc);
        
        // Setup accessibility associations
        AccessibilityHelper.associateLabelWithComponent(usernameLabel, usernameField);
        AccessibilityHelper.associateLabelWithComponent(passwordLabel, passwordField);
        
        return panel;
    }
    
    /**
     * Create the button panel
     */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, UITheme.SPACING_MEDIUM, 0));
        panel.setBackground(UITheme.BACKGROUND_PRIMARY);
        panel.setBorder(BorderFactory.createEmptyBorder(UITheme.SPACING_LARGE, 0, 0, 0));
        
        panel.add(cancelButton);
        panel.add(loginButton);
        
        return panel;
    }
    
    /**
     * Setup keyboard navigation
     */
    private void setupNavigation() {
        // Add focus indicators
        NavigationHelper.addFocusIndicator(usernameField);
        NavigationHelper.addFocusIndicator(passwordField);
        
        // Set mnemonics
        NavigationHelper.setButtonMnemonic(loginButton, KeyEvent.VK_L);
        NavigationHelper.setButtonMnemonic(cancelButton, KeyEvent.VK_C);
        
        // Set default button
        NavigationHelper.setDefaultButton(this, loginButton);
        
        // Setup standard key bindings (Escape to close)
        NavigationHelper.setupStandardKeyBindings(this);
        
        // Set focus traversal order
        NavigationHelper.setFocusTraversalOrder(
            getContentPane(), 
            usernameField, 
            passwordField, 
            loginButton, 
            cancelButton
        );
        
        // Password field should trigger login on Enter
        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });
    }
    
    /**
     * Setup accessibility features
     */
    private void setupAccessibility() {
        // Configure accessible text fields
        AccessibilityHelper.configureAccessibleTextField(
            usernameField, 
            "Username", 
            "Enter your username to log in to the ATP system"
        );
        
        AccessibilityHelper.configureAccessibleTextField(
            passwordField, 
            "Password", 
            "Enter your password to log in to the ATP system"
        );
        
        // Configure accessible buttons
        AccessibilityHelper.configureAccessibleButton(
            loginButton, 
            "Login", 
            "Click to log in to the ATP Management Workstation"
        );
        
        AccessibilityHelper.configureAccessibleButton(
            cancelButton, 
            "Cancel", 
            "Click to cancel login and exit the application"
        );
        
        // Set accessible information for status label
        AccessibilityHelper.setAccessibleInfo(
            statusLabel, 
            "Status message", 
            "Displays login status and error messages"
        );
    }
    
    /**
     * Perform login validation
     * 
     * DEMONSTRATION ONLY - This is placeholder logic for demonstration purposes.
     * In production, this should:
     * 1. Validate credentials against the database using ConnectDB
     * 2. Call CheckUser to verify permissions
     * 3. Create CreatMWSystemLog entry for the login
     * 4. Open frmMain window on success
     * 5. Close this login window
     */
    private void performLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        // Clear any previous errors
        AccessibilityHelper.clearError(usernameField);
        AccessibilityHelper.clearError(passwordField);
        statusLabel.setText(" ");
        
        // Validate input
        if (username.isEmpty()) {
            showError(usernameField, "Username is required");
            usernameField.requestFocus();
            return;
        }
        
        if (password.isEmpty()) {
            showError(passwordField, "Password is required");
            passwordField.requestFocus();
            return;
        }
        
        // DEMONSTRATION ONLY - Shows success message
        // For production: Implement actual authentication logic here
        showSuccess("Login successful!");
        
        // In production implementation:
        // 1. ConnectDB connDB = new ConnectDB();
        // 2. Validate credentials
        // 3. CheckUser checkUser = new CheckUser();
        // 4. Verify permissions
        // 5. new CreatMWSystemLog(username, "管理電腦", "使用者", "登入", null);
        // 6. frmMain mainFrame = new frmMain(userData);
        // 7. mainFrame.setVisible(true);
        // 8. this.dispose();
    }
    
    /**
     * Perform cancel action
     */
    private void performCancel() {
        int result = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to exit?",
            "Confirm Exit",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    
    /**
     * Show error message
     */
    private void showError(JComponent component, String message) {
        AccessibilityHelper.indicateError(component, message);
        statusLabel.setText(message);
        statusLabel.setForeground(UITheme.ERROR_COLOR);
        
        // Announce to screen reader
        AccessibilityHelper.announceToScreenReader(statusLabel, "Error: " + message);
    }
    
    /**
     * Show success message
     */
    private void showSuccess(String message) {
        statusLabel.setText(message);
        statusLabel.setForeground(UITheme.SUCCESS_COLOR);
        
        // Announce to screen reader
        AccessibilityHelper.announceToScreenReader(statusLabel, message);
    }
    
    /**
     * Main method for testing
     */
    public static void main(String[] args) {
        try {
            // Use system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frmLoginEnhanced frame = new frmLoginEnhanced();
                frame.setVisible(true);
            }
        });
    }
}
