/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuGUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * SudokuGUI class representing the login/sign-up page. 
 * 
 * @author paige
 */
public class SudokuGUI extends JPanel {

    private JTextField usernameField;       // Field for entering a username
    private JPasswordField passwordField;   // Field for entering a password
    private JLabel messageLabel;            // Label for displaying messages to the user

    /**
     * Constructor to create the GUI.
     */
    public SudokuGUI() {
        this.initialize();  // Initialize the GUI components
    }

    /**
     * Initialize the GUI.
     */
    private void initialize() {
        JFrame frame = new JFrame("PLAY SUDOKU.");  // Create a new frame for the GUI
        
        // Set panel styling
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Add labels and panels to frame
        add(createTitleLabel(), BorderLayout.NORTH);
        add(createFormPanel(), BorderLayout.CENTER);
        add(createButtonPanel(frame), BorderLayout.SOUTH);

        frame.add(this);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 350);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JLabel createTitleLabel() {
        JLabel titleLabel = new JLabel("PLAY SUDOKU.", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setBorder(new EmptyBorder(20, 10, 20, 10));
        return titleLabel;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(20, 50, 20, 50));
        formPanel.setBackground(Color.WHITE);
        formPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        formPanel.add(usernameField);
        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);
        
        return formPanel;
    }

    private JPanel createButtonPanel(JFrame frame) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        messageLabel = new JLabel("", JLabel.CENTER);
        messageLabel.setForeground(Color.RED);
        messageLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        buttonPanel.add(messageLabel);

        buttonPanel.add(createButton("LOGIN", "login", frame));
        buttonPanel.add(createButton("SIGN UP", "signup", frame));

        return buttonPanel;
    }

    private JButton createButton(String text, String action, JFrame frame) {
        JButton button = new JButton(text);
        button.setBackground(Color.LIGHT_GRAY);
        button.setForeground(Color.BLACK);
        button.addActionListener(new AuthActionListener(action, usernameField, passwordField, messageLabel, frame));
        
        return button;
    }

    public static void main(String[] args) {
        new SudokuGUI();
    }
}
