/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuGUI;

import Sudoku.FileHandler;
import Sudoku.Game;
import Sudoku.User;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class SudokuGUI extends JPanel {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel messageLabel;

    public SudokuGUI() {
        // Initialize GUI
        this.initialize();
    }

    private void initialize() {
        JFrame frame = new JFrame("PLAY SUDOKU.");
        
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Title section
        JLabel titleLabel = new JLabel("PLAY SUDOKU.", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setBorder(new EmptyBorder(20, 10, 20, 10));
        add(titleLabel, BorderLayout.NORTH);

        // Form section
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(20, 50, 20, 50));
        formPanel.setBackground(Color.WHITE);

        formPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        add(formPanel, BorderLayout.CENTER);

        // Button section
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        messageLabel = new JLabel("", JLabel.CENTER);
        messageLabel.setForeground(Color.RED);
        messageLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        buttonPanel.add(messageLabel);

        JButton loginButton = new JButton("LOGIN");
        loginButton.setBackground(Color.LIGHT_GRAY);
        loginButton.setForeground(Color.BLACK);
        loginButton.addActionListener(new AuthActionListener("login", usernameField, passwordField, messageLabel, frame));
        buttonPanel.add(loginButton);

        JButton signUpButton = new JButton("SIGN UP");
        signUpButton.setBackground(Color.LIGHT_GRAY);
        signUpButton.setForeground(Color.BLACK);
        signUpButton.addActionListener(new AuthActionListener("signup", usernameField, passwordField, messageLabel, frame));
        buttonPanel.add(signUpButton);

        add(buttonPanel, BorderLayout.SOUTH);
        
        frame.add(this); // Add the SudokuGUI JPanel to the JFrame
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new SudokuGUI();
    }
}
