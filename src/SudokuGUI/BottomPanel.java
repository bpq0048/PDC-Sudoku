/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuGUI;

import Sudoku.Game;

import java.awt.*;
import javax.swing.*;

/**
 * Represents the bottom section of the GameGUI.
 * 
 * Includes components for the timer, lives, and hints.
 * 
 * @author paige
 */
public class BottomPanel extends JPanel {
    
    private final TimerGUI timerGUI;    // Component to display and manage the timer
    private final LivesGUI livesGUI;    // Component to display and manage player's lives
    private final HintGUI hintGUI;      // Component to display and manage hints

    /**
     * Constructor to initialize the BottomPanel with the game object.
     * 
     * @param game The Game object to interact with game logic. 
     */
    public BottomPanel(Game game) {
        // Set layout for the panel
        setLayout(new BorderLayout());
        
        // Initialize timer GUI with game timer
        this.timerGUI = new TimerGUI(game.getTimer());
        this.timerGUI.start();
        
        // Initialize lives and hint GUI
        this.livesGUI = new LivesGUI(game);
        this.hintGUI = new HintGUI(game);
        
        initializePanel();  // Set up the panel with its components
    }

    /**
     * Initializes the components of the panel and sets their properties.
     */
    private void initializePanel() {
        // Create timer label
        JLabel timerLabel = timerGUI.getTimeLabel();
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add components to the panel
        add(livesGUI, BorderLayout.WEST);
        add(hintGUI, BorderLayout.EAST);
        add(timerLabel, BorderLayout.CENTER);
        
        // Set panel stylings
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }
    
    /**
     * Updates the displayed lives count in the lives GUI.
     */
    public void updateLivesCount() {
        livesGUI.updateLives(); // Call update method on lives GUI
    }
    
    /**
     * Updates the displayed hint count in the hint GUI.
     */
    public void updateHintCount() {
        hintGUI.updateHints(); // Call update method on hint GUI
    }
    
    /**
     * Pauses the timer in the timer GUI.
     */
    public void pauseTimer() {
        timerGUI.stop(); // Stop the timer
    }
    
    /**
     * Resumes the timer in the timer GUI.
     */
    public void resumeTimer() {
        timerGUI.start(); // Start the timer
    }
}
