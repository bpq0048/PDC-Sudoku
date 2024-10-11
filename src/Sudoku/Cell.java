/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sudoku;

/**
 * Represents a single cell in a Sudoku board.
 * 
 * This class manages a Cell object, which has a value and an editable state. The value
 * is an integer between 0 and 9, where 0 represents an empty cell. The editable state
 * indicates whether the cell's value can be changed by the user during the game.
 * 
 * @author paige
 */
public class Cell {
    
    private int value;          // The value of the cell (0-9)
    private boolean isEditable; // A flag indicating if the cell can be edited
    
    /**
     * Constructs a new Cell.
     * 
     * @param value The initial value of the cell (0-9).
     * @param isEditable A flag indicating if the cell is editable.
     */
    public Cell(int value, boolean isEditable) {
        if (0 <= value && value <= 9) {
            this.value = value;
        } else {
            this.value = 0;
        }
        this.isEditable = isEditable;
    }

    /**
     * Retrieves the current value of the cell.
     * 
     * @return The value of the cell (0-9)
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the value of the cell, if it is editable and the value is between 0 and 9.
     * 
     * @param value The new value of the cell (0-9).
     */
    public void setValue(int value) {
        if (isEditable && 0 <= value && value <= 9) {
            this.value = value;
        }
    }

    /**
     * Checks if the cell is editable. 
     * 
     * @return true if the cell is editable; false otherwise.
     */
    public boolean isEditable() {
        return isEditable;
    }

    /**
     * Sets whether the cell is editable.
     * 
     * @param isEditable The new editable status of the cell.
     */
    public void setEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }
}
