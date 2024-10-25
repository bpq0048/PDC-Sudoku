/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuGUI;

import Sudoku.User;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author paige
 */
public class SignUpPageGUI extends JPanel {
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel messageLabel;
    
    private HashMap<String, User> users;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    
    public SignUpPageGUI(HashMap<String, User> users, CardLayout cardLayout, JPanel cardPanel) {
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
        JButton loginButton = new JButton("Sign Up");
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
            if (password.isEmpty() || password.contains(" ")) {
                messageLabel.setText("Password cannot be empty or contain spaces.");
                return;
            }

            // Check if the username already exists
            if (users.containsKey(username)) {
                messageLabel.setText("Username already taken. Please try a different one.");
            } else {
                // Create new User object and add to users
                User newUser = new User(username, password);
                users.put(username, newUser);

                // Redirect to HomePageGUI with the new user
                messageLabel.setText("Sign-up successful! Redirecting...");
                HomePageGUI homePage = new HomePageGUI(newUser, cardLayout, cardPanel);
                cardPanel.add(homePage, "homePage");
                cardLayout.show(cardPanel, "homePage");
            }
        }
    }
}
