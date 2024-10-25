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
public class HomePageGUI extends JPanel {
    
    private User user;
    private JComboBox<String> difficultyDropdown;
    private HashMap<String, Integer> difficultyLevels;
    private JButton newGameButton;
    
    private CardLayout cardLayout;
    private JPanel cardPanel;
    
    public HomePageGUI(User user, CardLayout cardLayout, JPanel cardPanel) {
        this.user = user;
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        
        // Initialize the difficulty map
        difficultyLevels = new HashMap<>();
        difficultyLevels.put("Beginner", 1);
        difficultyLevels.put("Easy", 2);
        difficultyLevels.put("Medium", 3);
        difficultyLevels.put("Hard", 4);
        difficultyLevels.put("Expert", 5);
        
        initialize();
    }
    
    private void initialize() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Label for home page title
        JLabel homeLabel = new JLabel("PLAY SUDOKU.", JLabel.CENTER);
        homeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        homeLabel.setBorder(new EmptyBorder(20, 10, 20, 10));
        add(homeLabel, BorderLayout.NORTH);
        
        // Panel for difficulty selection and new game button
        JPanel difficultyPanel = new JPanel();
        difficultyPanel.setLayout(new FlowLayout());
        difficultyPanel.setBackground(Color.WHITE);
        
        // Add difficulty dropdown
        JLabel difficultyLabel = new JLabel("Select Difficulty");
        difficultyLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        difficultyPanel.add(difficultyLabel);
        
        String[] difficulties = {"Beginner", "Easy", "Medium", "Hard", "Expert"};
        difficultyDropdown = new JComboBox<>(difficulties);
        difficultyDropdown.setFont(new Font("Arial", Font.PLAIN, 18));
        difficultyPanel.add(difficultyDropdown);
        
        // Add New Game button
        newGameButton = new JButton("New Game");
        newGameButton.setFont(new Font("Arial", Font.BOLD, 20));
        difficultyPanel.add(newGameButton);

        add(difficultyPanel, BorderLayout.CENTER);
        
        // Action listener for the New Game button
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedDifficulty = (String) difficultyDropdown.getSelectedItem();
                int difficultyValue = difficultyLevels.get(selectedDifficulty);
                startNewGame(difficultyValue);
            }
        });
    }

    // Method to start a new game with the selected difficulty
    private void startNewGame(int difficulty) {
        // Logic to create and start a new game with the selected difficulty
        System.out.println("Starting a new game with difficulty: " + difficulty);
        
        GameGUI gamePage = new GameGUI(user);
        cardPanel.add(gamePage, "gamePage");
        cardLayout.show(cardPanel, "gamePage");
    }
}
