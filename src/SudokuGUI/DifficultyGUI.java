/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuGUI;

import javax.swing.*;
import java.awt.*;

/**
 * Handles difficulty drop-down selector. 
 * 
 * This class manages the selection of the difficulty for the game of Sudoku via a 
 * drop-down menu. 
 * 
 * @author paige
 */
public class DifficultyGUI extends JPanel {
    
    private final JComboBox<String> difficultyDropdown;       // JComboBox for selecting difficulty levels
    
    /**
     * Constructor to set up the DifficultyGUI panel.
     * 
     * Initializes the layout, background color, difficulty levels, and populates the JComboBox with difficulty options.
     */
    public DifficultyGUI() {
        // Set the layout to FlowLayout for arranging components in a line
        setLayout(new FlowLayout());
        
        // Set the background color of the panel to white
        setBackground(Color.WHITE);

        // Create a JLabel for the difficulty selection prompt with styling
        JLabel difficultyLabel = new JLabel("DIFFICULTY SELECTOR");
        difficultyLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        
        // Add the label to the panel
        add(difficultyLabel);

        // Define an ordered array of difficulty names
        String[] difficulties = {"Beginner", "Easy", "Medium", "Hard", "Expert"};
        
        // Initialize the JComboBox with the difficulty options abd styling
        difficultyDropdown = new JComboBox<>(difficulties);
        difficultyDropdown.setFont(new Font("Arial", Font.PLAIN, 18));
        
        // Add the JComboBox to the panel
        add(difficultyDropdown);
    }

    /**
     * Retrieves the integer value corresponding to the selected difficulty level.
     *
     * @return The integer value of the selected difficulty; returns -1 if the selection is invalid.
     */
    public int getSelectedDifficultyValue() {
        String selectedDifficulty = (String) difficultyDropdown.getSelectedItem();  // Get the selected item from the JComboBox
        switch (selectedDifficulty) {
            case "Beginner": return 1;
            case "Easy": return 2;
            case "Medium": return 3;
            case "Hard": return 4;
            case "Expert": return 5;
            default: return -1; // Invalid selection
        }
    }
}
