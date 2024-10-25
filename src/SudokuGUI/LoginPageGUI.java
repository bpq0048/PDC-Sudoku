/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuGUI;

import Sudoku.FileHandler;
import Sudoku.User;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author paige
 */
public class LoginPageGUI extends JPanel {
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel messageLabel;
    
    private HashMap<String, User> users;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public LoginPageGUI(HashMap<String, User> users, CardLayout cardLayout, JPanel cardPanel) {
        this.users = users;
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        
        initialize();
    }
    
    private void initialize() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Add label for login page title
        JLabel loginLabel = new JLabel("LOGIN PAGE", JLabel.CENTER);
        loginLabel.setFont(new Font("Arial", Font.BOLD, 30));
        loginLabel.setBorder(new EmptyBorder(20, 10, 20, 10));
        add(loginLabel, BorderLayout.NORTH);
        
        // Create panel for input fields
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(new EmptyBorder(20, 50, 20, 50));
        inputPanel.setBackground(Color.WHITE);

        // Username field
        inputPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        inputPanel.add(usernameField);

        // Password field
        inputPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        inputPanel.add(passwordField);

        // Add input panel to the center of the layout
        add(inputPanel, BorderLayout.CENTER);

        // Create panel for login button and message label
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(Color.WHITE);

        // Create login button
        JButton loginButton = new JButton("Login");
        buttonPanel.add(loginButton, BorderLayout.NORTH);

        // Message label for feedback
        messageLabel = new JLabel("", JLabel.CENTER);
        messageLabel.setForeground(Color.RED);
        messageLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        buttonPanel.add(messageLabel, BorderLayout.SOUTH);

        // Add the button panel to the south of the main layout
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listener to the login button
        loginButton.addActionListener(new LoginActionListener());
    }

    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            // Check if username exists
            if (username.isEmpty() || username.contains(" ")) {
                messageLabel.setText("Username cannot be empty or contain spaces.");
                return;
            }

            if (users.containsKey(username)) {
                User user = users.get(username);

                // Check if the password is correct
                if (user.getPassword().equals(password)) {
                    messageLabel.setText("Login successful! Redirecting...");

                    // Transition to HomePageGUI and pass the user
                    HomePageGUI homePage = new HomePageGUI(user, cardLayout, cardPanel);
                    cardPanel.add(homePage, "homePage");
                    cardLayout.show(cardPanel, "homePage");
                } else {
                    messageLabel.setText("Incorrect password. Please try again.");
                }
            } else {
                // Prompt to create a new account
                int response = JOptionPane.showConfirmDialog(null, "No user found. Would you like to create an account?", "Create Account", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    messageLabel.setText("Redirect to sign up page");
                    cardLayout.show(cardPanel, "signUpPage");
                } else {
                    messageLabel.setText("Login cancelled.");
                }
            }
        }
    }
}