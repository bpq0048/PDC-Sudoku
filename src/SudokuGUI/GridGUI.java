/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuGUI;

import Sudoku.Game;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * Represents the game board for GameGUI.
 * 
 * @author paige
 */
public class GridGUI extends JPanel {
    
    private final JTextField[][] cells; // 2D array for text fields representing the Sudoku grid
    private final Game game;            // Reference to the Game object to access board data
    
    private int currentRow = -1; // Track the currently selected row
    private int currentCol = -1; // Track the currently selected column

    /**
     * Constructor to initialize the grid with the given Game object.
     * 
     * @param game The game of Sudoku.
     */
    public GridGUI(Game game) {
        this.cells = new JTextField[9][9];
        this.game = game;
        
        setLayout(new GridLayout(9, 9));
        initializeGrid();
    }

    /**
     * Method to initialize the grid and populate it with JTextField or JLabel
     */
    private void initializeGrid() {
        // Panel styling
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);
        
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int puzzleValue = game.getPuzzleBoard().getCell(row, col).getValue();
                int currentValue = game.getBoard().getCell(row, col).getValue();
            
                // If the puzzle value is 0 (empty), create an editable JTextField
                if (puzzleValue == 0 && currentValue == 0) {
                    JTextField cell = new JTextField(1);
                    cell.setHorizontalAlignment(JTextField.CENTER);
                    applyCellStyling(cell, row, col);
                    cells[row][col] = cell;
                    add(cell); 
                    
                    setupCellListeners(cell, row, col); // Setup listeners for input
                    
                } else {
                    // Create a JLabel for non-editable cells
                    JLabel cellLabel;
                    if (puzzleValue != 0) {
                        // Use the puzzle value if it exists and make it uneditable
                        cellLabel = new JLabel(String.valueOf(puzzleValue));
                    } else {
                        // If currentValue exists but not in puzzle, display it in blue
                        cellLabel = new JLabel(String.valueOf(currentValue));
                        cellLabel.setForeground(Color.BLUE); // Show added values in blue
                    }

                    // Apply styling to the cell
                    cellLabel.setHorizontalAlignment(JLabel.CENTER);
                    applyCellStyling(cellLabel, row, col); 
                    
                    add(cellLabel); // Add the label to the panel
                }
            }
        }
    }

    /**
     * Method to setup listeners for each cell.
     * 
     * @param cell The cell to apply listeners to.
     * @param row The row location of the cell.
     * @param col The column location of the cell.
     */
    private void setupCellListeners(JTextField cell, int row, int col) {
        // Limit input to a single digit by using a DocumentFilter
        ((AbstractDocument) cell.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("[1-9]")) { // Allow only digits 1-9
                    fb.replace(0, fb.getDocument().getLength(), text, attrs); // Replace entire content
                }
            }

            @Override
            public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("[1-9]")) {
                    fb.replace(0, fb.getDocument().getLength(), text, attrs); // Replace entire content
                }
            }
        });
        
        // Listener for mouse clicks to track current cell
        cell.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentRow = row; // Set the current row
                currentCol = col; // Set the current column
            }
        });

        // Listener for changes in the text field
        cell.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validateInput(cell, row, col); // Validate input on text insert
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateInput(cell, row, col); // Validate input on text removal
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateInput(cell, row, col); // Validate input on text change
            }
        });
    }

    /**
     * Method to validate the input in a cell.
     * 
     * @param cell The cell to apply listeners to.
     * @param row The row location of the cell.
     * @param col The column location of the cell.
     */
    private void validateInput(JTextField cell, int row, int col) {
        String text = cell.getText(); // Get the current text
        
        if (text.isEmpty()) {
            return; // Skip validation if the cell is empty
        }
        
        int value = Integer.parseInt(text); // Parse the input value
        GameGUI parentGameGUI = (GameGUI) SwingUtilities.getAncestorOfClass(GameGUI.class, this); // Get parent GameGUI

        // Check if the input value matches the solution
        if (value == game.getCompleteBoard().getCell(row, col).getValue()) {
            // If the input is correct, make the cell non-editable
            cell.setEditable(false);
            cell.setForeground(Color.BLUE);
            cell.setBackground(Color.WHITE);
            
            game.getBoard().updateCell(row, col, value); // Update the game board
            
            parentGameGUI.checkGameCompletion(); // Check for game completion
            
        } else {
            cell.setForeground(Color.RED); // Indicate incorrect input
            cell.setBackground(Color.PINK); // Change background to indicate error
        
            if (parentGameGUI != null) {
                parentGameGUI.checkLives(); // Decrement lives and handle game end if necessary
            }
        }
    }

    /**
     * Method to apply styling to a cell or label.
     * 
     * @param cell The cell to apply listeners to.
     * @param row The row location of the cell.
     * @param col The column location of the cell.
     */
    private void applyCellStyling(JComponent cell, int row, int col) {
        cell.setFont(new Font("Arial", Font.BOLD, 20)); // Set font style and size
        cell.setBackground(Color.WHITE); // Set background color
        
        // Create borders for the cells
        int top = 1, left = 1, bottom = 1, right = 1;
        
        // Thicker border for 3x3 grid boundaries
        if (row % 3 == 0) top = 4;
        if (col % 3 == 0) left = 4;
        if (row == 8) bottom = 4; 
        if (col == 8) right = 4;
        
        cell.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK)); // Apply border
    }

    /**
     * Method to use a hint for the current cell.
     */
    public void useHint() {
        if (game.getPuzzleBoard().getCell(currentRow, currentCol).getValue() != 0) return;
        
        int value = game.useHint(currentRow, currentCol); // Get hint value from the game
        cells[currentRow][currentCol].setText(String.valueOf(value)); // Display hint in the cell
    }

    // Getters for current cell coordinates
    public int getCurrentRow() {
        return currentRow;
    }

    public int getCurrentCol() {
        return currentCol;
    }
}
