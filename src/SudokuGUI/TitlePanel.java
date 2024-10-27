/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuGUI;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Represents the title section of the GameGUI.
 * 
 * Contains buttons for quitting the game, pausing the game, and using hints.
 * The buttons are configured with their respective action listeners.
 * 
 * @author paige
 */
public class TitlePanel extends JPanel {
    
    private JButton quitButton;     // Button to quit the game
    private JButton pauseButton;    // Button to pause the game
    private JButton hintButton;     // Button to request a hint

    /**
     * Constructor to initialize the TitlePanel with action listeners for each button.
     * 
     * @param quitListener ActionListener for the quit button.
     * @param pauseListener ActionListener for the pause button.
     * @param hintListener ActionListener for the hint button.
     */
    public TitlePanel(ActionListener quitListener, ActionListener pauseListener, ActionListener hintListener) {
        setLayout(new BorderLayout());
        
        // Create and configure buttons
        createButtons(quitListener, pauseListener, hintListener);
        
        // Add the buttons
        add(quitButton, BorderLayout.WEST);
        add(pauseButton, BorderLayout.EAST);
        add(hintButton, BorderLayout.CENTER);
        
        // Set panel styling
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    /**
     * Helper method to create and configure buttons with their action listeners.
     * 
     * @param quitListener ActionListener for the quit button.
     * @param pauseListener ActionListener for the pause button.
     * @param hintListener ActionListener for the hint button.
     */
    private void createButtons(ActionListener quitListener, ActionListener pauseListener, ActionListener hintListener) {
        quitButton = new JButton("QUIT GAME");
        quitButton.setBackground(Color.LIGHT_GRAY);
        quitButton.setForeground(Color.BLACK);
        quitButton.addActionListener(quitListener);

        pauseButton = new JButton("PAUSE");
        pauseButton.setBackground(Color.LIGHT_GRAY);
        pauseButton.setForeground(Color.BLACK);
        pauseButton.addActionListener(pauseListener);

        hintButton = new JButton("USE HINT");
        hintButton.setBackground(Color.LIGHT_GRAY);
        hintButton.setForeground(Color.BLACK);
        hintButton.addActionListener(hintListener);
    }
    
    

    // Getter methods to retrieve buttons for external use
    public JButton getHintButton() {
        return hintButton;
    }

    public JButton getQuitButton() {
        return quitButton;
    }

    public JButton getPauseButton() {
        return pauseButton;
    }
}