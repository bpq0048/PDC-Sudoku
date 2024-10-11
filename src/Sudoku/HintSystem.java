/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sudoku;

/**
 * Manages the hint function in the Sudoku game.
 * 
 * Provides methods to retrieve a hint for a given cell on the Sudoku board and 
 * manages the number of hints remaining for the user.
 * 
 * @author paige
 */
public class HintSystem {
    
    private int hintCount; // Number of hints remaining for the user

    /**
     * Initializes the HintSystem with a default of 3 hints.
     */
    public HintSystem() {
        this.hintCount = 3;
    }
    
    /**
     * Provides the value of a given cell as a hint.
     * 
     * @param row The row index of the cell (0-8).
     * @param col The column index of the cell (0-8).
     * @param completeBoard The completed Sudoku board to get the hint from.
     * @return The value of the specified cell from the complete board.
     */
    public int getHint(int row, int col, Board completeBoard) {
        int cellNumber = completeBoard.getCell(row, col).getValue(); // Retrives the value for a given cell
        this.hintCount--; // Decrement the hint count
        return cellNumber;
    }

    /**
     * Retrieves the number of hints remaining.
     * 
     * @return The number of hints remaining.
     */
    public int getHintCount() {
        return hintCount;
    }

    /**
     * Sets the number of remaining hints. 
     * 
     * @param hintCount The new hint count.
     */
    public void setHintCount(int hintCount) {
        this.hintCount = hintCount;
    }
}
