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
public class LivesGUI extends JPanel {
    
    private JLabel livesLabel;
    private Game game;
    
    /**
     * Constructor for LivesGUI.
     * 
     * @param game The current game instance.
     */
    public LivesGUI(Game game) {
        this.game = game;
        setBackground(Color.WHITE);
        initialize();
    }

    /**
     * Initializes the Lives panel.
     */
    private void initialize() {
        // Load heart icon image
        ImageIcon heartIcon = new ImageIcon("path/to/heart_icon.png"); // Replace with your icon path
        JLabel heartLabel = new JLabel(heartIcon);
        
        livesLabel = new JLabel("Lives: " + game.getLives());
        livesLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        add(heartLabel);
        add(livesLabel);
    }

    // Update the lives label when lives change
    public void updateLives() {
        livesLabel.setText("Lives: " + game.getLives());
    }
}
