/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package SudokuGUI;

import Sudoku.DBHandler;
import Sudoku.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AuthActionListenerTest {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel messageLabel;
    private JFrame frame;
    private DBHandler dbHandler;
    private AuthActionListener authActionListener;

    @Before
    public void setUp() {
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        messageLabel = new JLabel();
        frame = new JFrame();
        
        // Assume DBHandler is implemented and we will use a simple one here
        dbHandler = new DBHandler() {
            @Override
            public User getUser(String username) {
                // Mock behavior for testing purposes
                if ("existingUser".equals(username)) {
                    return new User(username, "correctPassword");
                }
                return null; // User not found
            }
        };

        authActionListener = new AuthActionListener("login", usernameField, passwordField, messageLabel, frame);
    }

    @After
    public void tearDown() {
        frame.dispose();
    }

    @Test
    public void testLoginSuccess() {
        usernameField.setText("existingUser");
        passwordField.setText("somePassword");

        // Simulate action performed
        authActionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));

        // Check that the message label indicates success
        assertEquals("Login successful! Redirecting...", messageLabel.getText());
    }

    @Test
    public void testLoginIncorrectPassword() {
        usernameField.setText("existingUser");
        passwordField.setText("wrongPassword");

        // Simulate action performed
        authActionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));

        // Check that the message label indicates incorrect password
        assertEquals("Incorrect password. Please try again.", messageLabel.getText());
    }

    @Test
    public void testLoginUserNotFound() {
        usernameField.setText("unknownUser");
        passwordField.setText("somePassword");

        // Simulate action performed
        authActionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));

        // Check that the message label indicates no user found
        assertEquals("No user found. Please create an account.", messageLabel.getText());
    }

    @Test
    public void testSignupSuccess() {
        authActionListener = new AuthActionListener("signup", usernameField, passwordField, messageLabel, frame);
        usernameField.setText("uniqueUser");
        passwordField.setText("newPassword");

        // Simulate action performed
        authActionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));

        // Check that the message label indicates success
        assertEquals("Sign-up successful! Redirecting...", messageLabel.getText());
    }

    @Test
    public void testSignupUserAlreadyExists() {
        // Change to signup mode
        authActionListener = new AuthActionListener("signup", usernameField, passwordField, messageLabel, frame);
        usernameField.setText("existingUser"); // Use the existing user for signup
        passwordField.setText("somePassword");

        // Simulate action performed
        authActionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));

        // Check that the message label indicates username is taken
        assertEquals("Username already taken. Please try a different one.", messageLabel.getText());
    }

    @Test
    public void testEmptyUsername() {
        usernameField.setText(""); // Empty username
        passwordField.setText("somePassword");

        // Simulate action performed
        authActionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));

        // Check that the message label indicates invalid username
        assertEquals("Username cannot be empty or contain spaces.", messageLabel.getText());
    }

    @Test
    public void testEmptyPassword() {
        usernameField.setText("someUser");
        passwordField.setText(""); // Empty password

        // Simulate action performed
        authActionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));

        // Check that the message label indicates invalid password
        assertEquals("Password cannot be empty or contain spaces.", messageLabel.getText());
    }

    @Test
    public void testUsernameContainsSpaces() {
        usernameField.setText("user name"); // Username with spaces
        passwordField.setText("somePassword");

        // Simulate action performed
        authActionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));

        // Check that the message label indicates invalid username
        assertEquals("Username cannot be empty or contain spaces.", messageLabel.getText());
    }

    @Test
    public void testPasswordContainsSpaces() {
        usernameField.setText("someUser");
        passwordField.setText("pass word"); // Password with spaces

        // Simulate action performed
        authActionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));

        // Check that the message label indicates invalid password
        assertEquals("Password cannot be empty or contain spaces.", messageLabel.getText());
    }
}

