/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuGUI;

import Sudoku.FileHandler;
import Sudoku.User;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author paige
 */
class AuthActionListener implements ActionListener {
    
    private String mode;
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel messageLabel;
    
    private JFrame frame; 
    
    private FileHandler fileHandler;     // Handler for reading and writing user data to files
    private HashMap<String, User> users; // Map of users for storing and accessing user data
    
    
    public AuthActionListener(
            String mode, JTextField usernameField, JPasswordField passwordField, 
            JLabel messageLabel, JFrame frame) {
        
        this.mode = mode;
        
        this.usernameField = usernameField;
        this.passwordField = passwordField;
        this.messageLabel = messageLabel;
        this.frame = frame;
        
        this.fileHandler = new FileHandler();
        this.users = fileHandler.readUserData();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || username.contains(" ")) {
            messageLabel.setText("Username cannot be empty or contain spaces.");
            return;
        }
        
        if (password.isEmpty() || password.contains(" ")) {
            messageLabel.setText("Password cannot be empty or contain spaces.");
            return;
        }

        if ("login".equalsIgnoreCase(mode)) {
            handleLogin(username, password);
        } else if ("signup".equalsIgnoreCase(mode)) {
            handleSignUp(username, password);
        }
    }
    
    private void handleLogin(String username, String password) {
        if (users.containsKey(username)) {
            User user = users.get(username);

            if (user.getPassword().equals(password)) {
                messageLabel.setText("Login successful! Redirecting...");
                this.toHomePage(user);
            } else {
                messageLabel.setText("Incorrect password. Please try again.");
            }
        } else {
            messageLabel.setText("No user found. Please create an account.");
        }
    }

    private void handleSignUp(String username, String password) {
        if (users.containsKey(username)) {
            messageLabel.setText("Username already taken. Please try a different one.");
        } else {
            User newUser = new User(username, password);
            users.put(username, newUser);
            
            fileHandler.writeUserData(users);
            
            messageLabel.setText("Sign-up successful! Redirecting...");
            this.toHomePage(newUser);
        }
    }
    
    private void toHomePage(User user) {
        // Close the current frame
        frame.dispose();
        
        HomePageGUI homePage = new HomePageGUI(user);
    }
}
