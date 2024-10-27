/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sudoku;

import java.util.Random;

/**
 * Generates a Sudoku puzzle based on the specified difficulty level. 
 * 
 * This class extends the base class 'Board' and is responsible for generating a puzzle board by 
 * removing cells from a complete Sudoku board that adheres to Sudoku rules. A puzzle board must 
 * have one unique solution. It includes methods to generate a puzzle while checking that there
 * is only one unique solution. 
 * 
 * @author paige
 */
public class PuzzleGenerator {
    
    private Board puzzleBoard;           // The puzzle board
    private CompleteBoard completeBoard; // A complete Sudoku board that adheres to Sudoku rules
    private final int difficulty;        // The difficulty level for the puzzle board
    
    // Number of cells to be removed when generating puzzle boards for each difficulty
    private static final int BEGINNER = 28;
    private static final int EASY = 45;
    private static final int MEDIUM = 49;
    private static final int HARD = 53;
    private static final int EXPERT = 64;

    /**
     * Initializes PuzzleGenerator object.
     * 
     * @param difficulty The difficulty level of the puzzle to be generated.
     */
    public PuzzleGenerator(int difficulty) {
        this.difficulty = difficulty;
        generatePuzzle(); // Generate the puzzle based on difficulty level
    }
    
    /**
     * Retrieves the puzzle board.
     * 
     * @return A PuzzleBoard object.
     */
    public Board getPuzzleBoard() {
        return puzzleBoard;
    }
    
    /**
     * Retrieves the complete board used to generate the puzzle.
     * 
     * @return A CompleteBoard object.
     */
    public CompleteBoard getCompleteBoard() {
        return completeBoard;
    }
    
    /**
     * Generates a Sudoku puzzle.
     * 
     * This method generates the puzzle by removing a specific number of cells based
     * on difficulty level from a complete board. 
     */
    private void generatePuzzle() {
        // Create a fully completed Sudoku board
        completeBoard = new CompleteBoard();

        // Initialize the puzzle board and copy the completed board
        puzzleBoard = new Board();
        copyCompletePuzzle(completeBoard);

        // Remove numbers to create the puzzle
        removeNumbers();
    }
    
    /**
     * Copies the cells from the complete board to the puzzle board.
     * 
     * @param completeBoard The fully completed Sudoku board.
     */
    private void copyCompletePuzzle(CompleteBoard completeBoard) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int value = completeBoard.getCell(row, col).getValue();
                puzzleBoard.updateCell(row, col, value); // Copy each cell value
            }
        }
    }
    
    /**
     * Removes cells to create Sudoku puzzle.
     * 
     * This method randomly removes numbers (the amount removed is determined by difficulty
     * level) while ensuring that there is only one unique solution.
     */
    private void removeNumbers() {
        int cellsToRemove = puzzleDifficulty(); // Determine number of cells to remove
        int attempts = 0; // Counter for number of attemps to remove cells
        Random rand = new Random(); 
        
        // Continue until there are no more cells to remove or number of attempts is greater than cells to remove
        while (0 < cellsToRemove && attempts < cellsToRemove) {
            // Selects random row and column
            int row = rand.nextInt(9);
            int col = rand.nextInt(9);
            
            // Checks if the cell has a number
            if (puzzleBoard.getCell(row, col).getValue() != 0) {
                int removedCell = puzzleBoard.getCell(row, col).getValue();
                puzzleBoard.updateCell(row, col, 0); // Remove the cell
                
                // Check if the puzzle still has a unique solution
                if (!hasUniqueSolution()) {
                    puzzleBoard.updateCell(row, col, removedCell); // Restore cell if not unique
                } else {
                    cellsToRemove--; // Decrement number of cells to remove
                    attempts = 0; // Reset attempts counter
                }
                attempts++; // Increment attempts counter
            }
        }
    }
    
    /**
     * Determines the number of cells to remove based on the difficulty level. 
     * 
     * @return The number of cells to remove.
     */
    public int puzzleDifficulty() {
        switch (difficulty) {
            case 1:
                return BEGINNER;
            case 2:
                return EASY;
            case 3:
                return MEDIUM;
            case 4:
                return HARD;
            case 5:
                return EXPERT;
            default:
                return 0;
        }
    }
    
    /**
     * Checks if the current puzzle board has a unique solution.
     * 
     * @return true if the puzzle has a unique solution; false otherwise.
     */
    private boolean hasUniqueSolution() {
        return countSolutions(0, 0) == 1; // Count solutions and check if there is exactly one
    }
    
    /**
     * Recursively counts the number of solutions for the puzzle board. 
     * 
     * @param row The current row index.
     * @param col The current column index. 
     * @return The number of solutions for the current board.
     */
    private int countSolutions(int row, int col) {
        if (row == 9) return 1; // Return 1 if we have filled all rows
        if (col == 9) return countSolutions(row + 1, 0); // Move to the next row

        // Skip any filled cells
        if (puzzleBoard.grid[row][col].getValue() != 0) {
            return countSolutions(row, col + 1);
        }

        int count = 0; // Solution count
        
        // Iterate over each valid number input
        for (int num = 1; num <= 9; num++) {
            if (isSafeToPlace(row, col, num)) {
                puzzleBoard.grid[row][col].setValue(num);
                count += countSolutions(row, col + 1);
                if (count > 1) break; // Stop if more than one solution is found
            }
        }
        
        puzzleBoard.grid[row][col].setValue(0); // Reset the cell value
        return count; // Number of solutions
    }

    /**
     * Checks whether placing a given number in a specified cell is valid.
     * 
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @param num The number to check.
     * @return true if placing the number is valid; false otherwise.
     */
    private boolean isSafeToPlace(int row, int col, int num) {
        // Iterate over the row and column of the chosen cell for given number
        for (int i = 0; i < 9; i++) {
            if (puzzleBoard.grid[row][i].getValue() == num || 
                puzzleBoard.grid[i][col].getValue() == num) return false; // Number is in row or column
            
            int subGridRow = (row / 3) * 3 + i / 3;
            int subGridCol = (col / 3) * 3 + i % 3;
            if (puzzleBoard.grid[subGridRow][subGridCol].getValue() == num) return false; // Number is in subgrid
        }
        return true; // Number is valid
    }
}
