/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuGUI;

import Sudoku.Game;
import Sudoku.HintSystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author paige
 */
public class HintGUI extends JPanel {
    
    private HintSystem hintSystem; // The hint system to manage hints
    private JLabel hintLabel;       // Label to display the hint count
    private JButton hintButton;     // Button to apply a hint
    private Game game;        // Reference to the current game

    /**
     * Constructor for the HintGUI.
     * 
     * @param hintSystem The HintSystem to manage hint functionality.
     * @param game The current Sudoku game.
     */
    public HintGUI(HintSystem hintSystem, Game game) {
        this.hintSystem = hintSystem;
        this.game = game;

        // Set up the panel layout
        setLayout(new FlowLayout());

        // Create and customize the lightbulb icon
        ImageIcon hintIcon = new ImageIcon("path/to/lightbulb/icon.png"); // Add your lightbulb icon path
        hintButton = new JButton(hintIcon);
        hintButton.setPreferredSize(new Dimension(50, 50));
        hintButton.setBorderPainted(false);
        hintButton.setContentAreaFilled(false);
        hintButton.setFocusPainted(false);
        //hintButton.addActionListener(new HintActionListener());

        // Label to show the remaining hints
        hintLabel = new JLabel("Hints left: " + hintSystem.getHintCount());
        hintLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        // Add components to the panel
        add(hintButton);
        add(hintLabel);
    }

    /**
     * Updates the hint count display.
     */
    public void updateHintCount() {
        hintLabel.setText("Hints left: " + hintSystem.getHintCount());
    }

    /**
     * Action listener for the hint button.
     
    private class HintActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (hintSystem.getHintCount() > 0) {
                // Apply the hint
                int hintValue = hintSystem.getHint(row, col, game.getCompleteBoard());
                game.getBoard().setCellValue(row, col, hintValue); // Set the hint in the game board
                updateHintCount(); // Update the hint count display

                // Optionally, you can refresh the GUI to reflect changes
                game.refreshBoard(); // Implement this method to refresh the board in your game
            } else {
                JOptionPane.showMessageDialog(HintGUI.this, "No hints left!");
            }
        }
    }*/
}
