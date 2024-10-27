/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuGUI;

import Sudoku.Game;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the lives GUI for GameGUI.
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
        livesLabel = new JLabel("Lives: " + game.getLives());
        livesLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        add(livesLabel);
    }

    // Update the lives label when lives change
    public void updateLives() {
        livesLabel.setText("Lives: " + game.getLives());
    }
}
