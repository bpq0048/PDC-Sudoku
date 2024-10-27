/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuGUI;

import Sudoku.DBHandler;
import Sudoku.User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Handles user authentication actions, such as login and sign up.
 * 
 * Implements ActionListener to respond to button clicks.
 * 
 * @author paige
 */
class AuthActionListener implements ActionListener {
    
    private String mode;                    // Mode to set to "login" or "signup"
    private JTextField usernameField;       // Field for entering username
    private JPasswordField passwordField;   // Field for entering password
    private JLabel messageLabel;            // Label for displaying messages to user
    private JFrame frame;                   // The current frame to close on successful login/signup
    
    private DBHandler dbHandler;            // Database handler for user data operations
    
    /**
     * Constructor for AuthActionListener
     * 
     * @param mode The mode of authentication ("login" or "signup").
     * @param usernameField The text field for username input.
     * @param passwordField The password field for password input.
     * @param messageLabel The label for displaying messages to the user. 
     * @param frame  The current JFrame to be disposed on success.
     */
    public AuthActionListener(
            String mode, JTextField usernameField, JPasswordField passwordField, 
            JLabel messageLabel, JFrame frame) {
        
        this.mode = mode;
        this.usernameField = usernameField;
        this.passwordField = passwordField;
        this.messageLabel = messageLabel;
        this.frame = frame;
        
        this.dbHandler = new DBHandler(); // Initialize DBHandler for database operations
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // Get trimmed username and password from input fields
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        // Validate username input
        if (username.isEmpty() || username.contains(" ")) {
            messageLabel.setText("Username cannot be empty or contain spaces.");
            return;
        }
        
        // Validate password input
        if (password.isEmpty() || password.contains(" ")) {
            messageLabel.setText("Password cannot be empty or contain spaces.");
            return;
        }

        // Determine action based on mode
        if ("login".equalsIgnoreCase(mode)) {
            handleLogin(username, password);
        } else if ("signup".equalsIgnoreCase(mode)) {
            handleSignUp(username, password);
        }
    }
    
    /**
     * Handle user login process. 
     * 
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     */
    private void handleLogin(String username, String password) {
        User user = dbHandler.getUser(username); // Retrieve user from the database
        
        if (user != null) { // If user exists
            // Check if the provided password matches the stored password
            if (user.getPassword().equals(password)) {
                messageLabel.setText("Login successful! Redirecting...");
                this.toHomePage(user); // Redirect to home page on success
                
            } else {
                messageLabel.setText("Incorrect password. Please try again.");
            }
        } else {
            messageLabel.setText("No user found. Please create an account.");
        }
    }

    /**
     * Handles user signup process.
     * 
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     */
    private void handleSignUp(String username, String password) {
        User user = dbHandler.getUser(username); // Check if the user already exists
        
        if (user != null) { // If user already exists
            messageLabel.setText("Username already taken. Please try a different one.");
        } else {
            // Create a new user and add to the database
            User newUser = new User(username, password);
            dbHandler.addUser(username, password);
            
            messageLabel.setText("Sign-up successful! Redirecting...");
            this.toHomePage(dbHandler.getUser(username)); // Redirect to home page on success
        }
    }
    
    /**
     * Redirects to the home page after successful login or sign-up.
     * 
     * @param user The authenticated user object.
     */
    private void toHomePage(User user) {
        frame.dispose(); // Close the current frame
        HomePageGUI homePage = new HomePageGUI(user); // Open the home page
    }
}
