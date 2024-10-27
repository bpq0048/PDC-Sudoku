/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sudoku;

/**
 * Represents a 9x9 Sudoku board consisting of cells.
 * 
 * This class manages the creation and manipulation of a Sudoku board, represented by a
 * grid of cells. Each cell holds a value between 0 and 9, where 0 represents an empty
 * cell. It provides methods for initializing, loading, printing, updating and checking
 * the state of the grid.
 * 
 * @author paige
 */
public class Board {
    
    protected Cell[][] grid; // A 2D grid array representing the grid of cells in a Sudoku board
    
    /**
     * Constructs a new 9x9 Sudoku board consisting of empty cells.
     */
    public Board() {
        this.grid = new Cell[9][9]; 
        initializeBoard(); // Initalize the board with empty cells
    }
    
    /**
     * Initializes the board with empty cells.
     * 
     * Each cell is initialized with a value of 0 and marked as editable.
     */
    private void initializeBoard() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                grid[row][col] = new Cell(0, true);
            }
        }
    }
    
    /**
     * Loads a board from a String representation of its content.
     * 
     * @param board A String representing the boards cells row by row.
     */
    public void loadBoard(String board) {
        int charIndex = 0;
        
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                char c = board.charAt(charIndex);
                grid[row][col] = new Cell(Character.getNumericValue(c), true);
                charIndex++;
            }
        }
    }
    
    /**
     * Checks if the board is completely filled with non-zero values.
     * 
     * @return true if all cells are filled; false otherwise.
     */
    public boolean isComplete() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (grid[row][col].getValue() == 0) {
                    return false; // An empty cell was found
                }
            }
        }
        return true; // All cells are filled
    }
    
    /**
     * Retrieves the cell at the specified row and column.
     * 
     * @param row The row index of the cell (0-8).
     * @param col The column index of the cell (0-8).
     * @return The Cell object at the specified position.
     */
    public Cell getCell(int row, int col) {
        return grid[row][col];
    }

    /**
     * Updates the value of a specific cell on the board.
     * 
     * @param row The row index of the cell to update (0-8).
     * @param col The column index of the cell to update (0-8).
     * @param value The new value to set in the cell (1-9).
     */
    public void updateCell(int row, int col, int value) {
        grid[row][col].setValue(value);
    }
    
    /**
     * Checks if a specified cell on the board is empty.
     * 
     * @param row The row index of the cell to check (0-8).
     * @param col The column index of the cell to check (0-8).
     * @return true if the cell is empty (value is 0); false otherwise.
     */
    public boolean isEmptyCell(int row, int col) {
        return grid[row][col].getValue() == 0;
    }

    /**
     * Retrieves the grid representation of the board.
     * 
     * @return The grid of the board.
     */
    public Cell[][] getGrid() {
        return grid;
    }

    /**
     * Sets the content of the grid with another grid.
     * 
     * @param grid A 2D grid array representing the grid of cells in a Sudoku board.
     */
    public void setGrid(Cell[][] grid) {
        this.grid = grid;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sb.append(grid[i][j].getValue());
            }
        }
        return sb.toString();
    }
}
