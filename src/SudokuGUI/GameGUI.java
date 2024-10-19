/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuGUI;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author paige
 */
public class GameGUI {
    
    private JFrame frame = new JFrame();
    
    private JTextField[][] cells = new JTextField[9][9];
    private int selectedRow = -1;
    private int selectedCol = -1;
    
    
    public GameGUI() {
        initialize();
    }
    
    private void initialize() {
        frame = new JFrame("PDC Sudoku Project");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 700);

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Create the title panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("PLAY SUDOKU.");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titlePanel.add(titleLabel);
        titlePanel.setBackground(Color.WHITE);

        // Create the grid panel with a 9x9 GridLayout
        JPanel gridPanel = this.sudokuGrid();

        // Create the number panel for numbers 1-9
        JPanel numberPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        numberPanel.setBackground(Color.WHITE);
        for (int i = 1; i <= 9; i++) {
            JLabel numberLabel = new JLabel(String.valueOf(i));
            numberLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            numberPanel.add(numberLabel);
        }

        // Add panels to the main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH); // Add the title at the top
        mainPanel.add(gridPanel, BorderLayout.CENTER); // Add the grid in the center
        mainPanel.add(numberPanel, BorderLayout.SOUTH); // Add the numbers at the bottom

        // Add the main panel to the frame
        frame.add(mainPanel);

        // Center the JFrame on the screen
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
    
    private JPanel sudokuGrid() {
        JPanel gridPanel = new JPanel(new GridLayout(9, 9));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around the grid
        gridPanel.setBackground(Color.WHITE);

        // Add text fields for each cell in the Sudoku grid
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                JTextField cell = new JTextField(1);
                cell.setHorizontalAlignment(JTextField.CENTER);

                // Create borders for the cells
                int top = 1, left = 1, bottom = 1, right = 1;

                // Thicker border for 3x3 grid boundaries
                if (row % 3 == 0) top = 3;
                if (col % 3 == 0) left = 3;
                if (row == 8) bottom = 3; // Bottom border for the last row
                if (col == 8) right = 3;  // Right border for the last column

                cell.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));
                gridPanel.add(cell);
            }
        }
        
        return gridPanel;
    }
}
