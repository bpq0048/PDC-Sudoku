/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuGUI;

import Sudoku.FileHandler;
import Sudoku.Game;
import Sudoku.User;
import java.awt.*;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.*;

public class SudokuGUI {
    
    private Scanner scan;                // Scanner to read user input
    private FileHandler fileHandler;     // Handler for reading and writing user data to files
    private HashMap<String, User> users; // Map of users for storing and accessing user data

    /**
     * Constructor for initializes Sudoku GUI.
     */
    public SudokuGUI() {
        this.scan = new Scanner(System.in);
        this.fileHandler = new FileHandler();
        this.users = fileHandler.readUserData();
    }
    
    public static void main(String[] args) {
        SudokuGUI sudoku = new SudokuGUI();
        
        JFrame frame =  new JFrame("PDC Sudoku Project"); 
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);
        
        // Create instances of page classes
        MainPageGUI mainPage = new MainPageGUI(cardLayout, cardPanel);
        LoginPageGUI loginPage = new LoginPageGUI(sudoku.users, cardLayout, cardPanel);
        SignUpPageGUI signUpPage = new SignUpPageGUI(sudoku.users, cardLayout, cardPanel);
        
        // Add pages to card panel
        cardPanel.add(mainPage, "mainPage");
        cardPanel.add(loginPage, "loginPage");
        cardPanel.add(signUpPage, "signUpPage");
        
        frame.add(cardPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        // Show initial page
        cardLayout.show(cardPanel, "mainPage");
    }
}
