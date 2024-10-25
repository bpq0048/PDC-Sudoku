/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author paige
 */
public class MainPageGUI extends JPanel {
    
    public MainPageGUI(CardLayout cardLayout, JPanel cardPanel) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Add label for title
        JLabel titleLabel = new JLabel("PLAY SUDOKU.", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBorder(new EmptyBorder(20, 10, 20, 10));
        add(titleLabel, BorderLayout.NORTH);
        
        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        
        // Add login and signup buttons
        JButton loginButton = new JButton("LOGIN");
        JButton signUpButton = new JButton("SIGN UP");
        
        // Customize button visuals
        loginButton.setBackground(Color.LIGHT_GRAY);
        loginButton.setForeground(Color.BLACK);
        signUpButton.setBackground(Color.LIGHT_GRAY);
        signUpButton.setForeground(Color.BLACK);
        
        // Add action listeners to the buttons
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "loginPage");  // Switch to loginPage
            }
        });
        
        // Add action listeners to the buttons
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "signUpPage");  // Switch to signUpPage
            }
        });
        
        buttonPanel.add(loginButton);
        buttonPanel.add(signUpButton);
        add(buttonPanel, BorderLayout.CENTER);
    }
}
