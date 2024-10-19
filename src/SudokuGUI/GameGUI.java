/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuGUI;

import Sudoku.Board;
import Sudoku.CompleteBoard;
import Sudoku.Game;
import Sudoku.PuzzleGenerator;
import Sudoku.SudokuGame;
import Sudoku.User;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author paige
 */
public class GameGUI {
    
    User user = new User("test", "test");
    //private SudokuGame sudokuGame = new SudokuGame(user);
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
        
        PuzzleGenerator puzzleGenerator = new PuzzleGenerator(2);
        Board completeBoard = puzzleGenerator.getCompleteBoard();
        Board board = puzzleGenerator.getPuzzleBoard();

        // Add text fields for each cell in the Sudoku grid
    for (int row = 0; row < 9; row++) {
        for (int col = 0; col < 9; col++) {
            if (board.getCell(row, col).getValue() == 0) {
                JTextField cell = new JTextField(1);
                cell.setHorizontalAlignment(JTextField.CENTER);
                this.applyCellStyling(cell, row, col);
                gridPanel.add(cell);
                
                // Action Listener to detect user input
                int finalRow = row; // Required to use row inside lambda
                int finalCol = col; // Required to use col inside lambda
                cell.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                    @Override
                    public void insertUpdate(javax.swing.event.DocumentEvent e) {
                        checkInput(cell, finalRow, finalCol, completeBoard);
                    }

                    @Override
                    public void removeUpdate(javax.swing.event.DocumentEvent e) {
                        checkInput(cell, finalRow, finalCol, completeBoard);
                    }

                    @Override
                    public void changedUpdate(javax.swing.event.DocumentEvent e) {
                        checkInput(cell, finalRow, finalCol, completeBoard);
                    }
                });
            }
            else {
                JLabel cellLabel = new JLabel(String.valueOf(board.getCell(row, col).getValue()));
                cellLabel.setHorizontalAlignment(JLabel.CENTER);
                this.applyCellStyling(cellLabel, row, col);
                gridPanel.add(cellLabel);
            }
        }
    }
        
        return gridPanel;
    }
    
    // Method to check the input and update the cell if correct
private void checkInput(JTextField cell, int row, int col, Board completeBoard) {
    String text = cell.getText();
    if (!text.isEmpty()) {
        try {
            int value = Integer.parseInt(text);
            if (value == completeBoard.getCell(row, col).getValue()) {
                // If the input is correct, make the cell non-editable
                cell.setEditable(false);
                cell.setForeground(Color.GREEN); // Optional: Change text color to indicate correctness
            } else {
                cell.setForeground(Color.RED); // Optional: Indicate incorrect input
            }
        } catch (NumberFormatException ex) {
            // Handle non-numeric input
            cell.setForeground(Color.RED); // Optional: Indicate incorrect input
        }
    }
}

    private void applyCellStyling(JComponent cell, int row, int col) {
        // Set font size and style
        Font font = new Font("Arial", Font.BOLD, 22); // Adjust font name, style, and size as needed
        cell.setFont(font);

        // Create borders for the cells
        int top = 1, left = 1, bottom = 1, right = 1;

        // Thicker border for 3x3 grid boundaries
        if (row % 3 == 0) top = 3;
        if (col % 3 == 0) left = 3;
        if (row == 8) bottom = 3; // Bottom border for the last row
        if (col == 8) right = 3;  // Right border for the last column

        cell.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));
    }
}
