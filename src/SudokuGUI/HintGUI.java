/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuGUI;

import Sudoku.Game;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author paige
 */
public class HintGUI extends JPanel {
    
    private JLabel hintsLabel;       // Label to display the hint count
    private Game game;        // Reference to the current game

    /**
     * Constructor for the HintGUI.
     * 
     * @param game The current Sudoku game.
     */
    public HintGUI(Game game) {
        this.game = game;
        setBackground(Color.WHITE);
        initialize();
    }

    /**
     * Initializes the Hint panel.
     */
    private void initialize() {
        // Load heart icon image
        ImageIcon heartIcon = new ImageIcon("path/to/heart_icon.png"); // Replace with your icon path
        JLabel heartLabel = new JLabel(heartIcon);
        
        hintsLabel = new JLabel("Hints: " + game.getHintCount());
        hintsLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        add(heartLabel);
        add(hintsLabel);
    }

    // Update the lives label when lives change
    public void updateHints() {
        hintsLabel.setText("Hints: " + game.getHintCount());
    }
}
