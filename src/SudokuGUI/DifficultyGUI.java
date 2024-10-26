/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuGUI;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Handles difficulty drop-down selector. 
 * 
 * This class manages the selection of the difficulty for the game of Sudoku via a 
 * drop-down menu. 
 * 
 * @author paige
 */
public class DifficultyGUI extends JPanel {
    
    private JComboBox<String> difficultyDropdown;       // JComboBox for selecting difficulty levels
    private HashMap<String, Integer> difficultyLevels;  // A HashMap that associates difficulty names with their corresponding integer values

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

        // Initialize the HashMap for difficulty levels
        difficultyLevels = new HashMap<>();
        
        // Populate the HashMap with difficulty names and their respective values
        difficultyLevels.put("Beginner", 1);
        difficultyLevels.put("Easy", 2);
        difficultyLevels.put("Medium", 3);
        difficultyLevels.put("Hard", 4);
        difficultyLevels.put("Expert", 5);

        // Create a JLabel for the difficulty selection prompt with styling
        JLabel difficultyLabel = new JLabel("Select Difficulty");
        difficultyLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        
        // Add the label to the panel
        add(difficultyLabel);

        // Create an array from the difficulty level keys to populate the JComboBox
        String[] difficulties = difficultyLevels.keySet().toArray(new String[0]);
        
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
        return difficultyLevels.getOrDefault(selectedDifficulty, -1);
    }
}
