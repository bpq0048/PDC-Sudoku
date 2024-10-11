/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Represents a complete Sudoku board. 
 * 
 * This class extends the base class 'Board' and is responsible for generating a fully completed 
 * Sudoku board that adheres to Sudoku rules. A complete board means all cells are filled with 
 * numbers such that each number from 1 to 9 appears exactly once in each row, column, and 3x3 
 * subgrid. This class contains methods to generate the complete board and check the validity
 * of placing numbers in cells.
 * 
 * @author paige
 */
public class CompleteBoard extends Board {
    
    /**
     * Initializes CompleteBoard object.
     */
    public CompleteBoard() {
        super();
        generateCompleteBoard(0, 0); // Start generating the board from the first cell
    }
    
    /**
     * Recursively generates a complete Sudoku board using backtracking. 
     * 
     * @param row The current row index where the number is to be placed.
     * @param col The current column index where the number is to be placed.
     * @return true if a valid complete board is generated; false otherwise.
     */
    private boolean generateCompleteBoard(int row, int col) {
        if (row == 9) {
            return true; // Board is complete
        }
        if (col == 9) {
            return generateCompleteBoard(row + 1, 0); // Moving onto the next row and starting at column 0
        }
        
        // Get a shuffled list of numbers from 1 to 9 to fill the cell
        ArrayList<Integer> numbers = getShuffledNumbers();

        // Try placing each number in the current cell
        for (int num : numbers) {
            // Check if the number can be placed in the current cell
            if (isSafeToPlace(row, col, num)) {
                // Set the cell value
                grid[row][col].setValue(num);
                
                // Recursively attempt to fill the rest of the board
                if (generateCompleteBoard(row, col + 1)) {
                    return true; // If successful, return true
                }
                
                // Reset the cell if placing the number did not lead to a valid solution
                grid[row][col].setValue(0);
            }
        }

        return false; // No valid placement was found for any number
    }
    
    
    /**
     * Returns a shuffled list of numbers from 1 to 9.
     * 
     * Used to randomize the order in which numbers are attempted to be added in each cell.
     * 
     * @return An ArrayList of integers from 1 to 9 in a random order.
     */
    private ArrayList<Integer> getShuffledNumbers() {
        ArrayList<Integer> numbers = new ArrayList<>();
        
        // Add numbers 1 to 9 to the list
        for (int i = 1; i <= 9; i++) {
            numbers.add(i);
        }
        
        // Shuffle the numbers to randomize their order
        Collections.shuffle(numbers, new Random());
        return numbers;
    }
    
    
    /**
     * Checks whether a number is safe to place in the specified cell.
     * 
     * Verifies if the number is safe to place by checking all of the numbers present in
     * the current row, column and subgrid.
     * 
     * @param currentRow The row index of the cell.
     * @param currentCol The column index of the cell.
     * @param number The number to check for placement.
     * @return true if it is safe to place the number; false otherwise. 
     */
    private boolean isSafeToPlace(int currentRow, int currentCol, int number) {
        return !isNumberInRow(currentRow, number) && 
               !isNumberInCol(currentCol, number) && 
               !isNumberInSubgrid(currentRow, currentCol, number);
    }
    
    
    /**
     * Checks if a given number is already present in the specified row.
     * 
     * @param currentRow The row index to check.
     * @param number The number to look for. 
     * @return true is the number is found; false otherwise.
     */
    private boolean isNumberInRow(int currentRow, int number) {
        // Iterate through each column in the current row
        for (int col = 0; col < 9; col++) {
            if (grid[currentRow][col].getValue() != 0 && grid[currentRow][col].getValue() == number) {
                return true; // Number is found
            }
        }
        return false; // Number is not found
    }
    
    
    /**
     * Checks if a given number is already present in the specified column.
     * 
     * @param currentCol The column index to check.
     * @param number The number to look for.
     * @return true is the number is found; false otherwise.
     */
    private boolean isNumberInCol(int currentCol, int number) {
        // Iterate through each column in the current column
        for (int row = 0; row < 9; row++) {
            if (grid[row][currentCol].getValue() != 0 && grid[row][currentCol].getValue() == number) {
                return true; // Number is found
            }
        }
        return false; // Number is not found
    }
    
    
    /**
     * Checks if a given number is already present in the subgrid that contains the specified cell.
     * 
     * @param currentRow The row index of the cell.
     * @param currentCol The column index of the cell.
     * @param number The number to look for.
     * @return true is the number is found; false otherwise.
     */
    private boolean isNumberInSubgrid(int currentRow, int currentCol, int number) {
        // Determine the starting row and column indices of the 3x3 subgrid
        int gridRow = (currentRow / 3) * 3;
        int gridCol = (currentCol / 3) * 3;
        
        // Iterate through the cells in the 3x3 subgrid
        for (int row = gridRow; row < gridRow + 3; row++) {
            for (int col = gridCol; col < gridCol + 3; col++) {
                if(grid[row][col].getValue() != 0 && grid[row][col].getValue() == number) {
                    return true; // Number is found
                }
            }
        }
        return false; // Number is not found
    }
}
