/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Sudoku;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CompleteBoardTest {

    private CompleteBoard completeBoard;

    @Before
    public void setUp() {
        completeBoard = new CompleteBoard();
    }

    @After
    public void tearDown() {
        completeBoard = null; // Clean up after each test
    }

    /**
     * Test that the complete board is fully populated with numbers 1-9.
     */
    @Test
    public void testCompleteBoardIsFilled() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                assertNotEquals("Cell at (" + row + ", " + col + ") should be filled", 0, completeBoard.getCell(row, col).getValue());
            }
        }
    }

    /**
     * Test that all rows contain unique numbers.
     */
    @Test
    public void testRowsContainUniqueNumbers() {
        for (int row = 0; row < 9; row++) {
            boolean[] seen = new boolean[10]; // Index 0 is unused
            for (int col = 0; col < 9; col++) {
                int value = completeBoard.getCell(row, col).getValue();
                assertFalse("Duplicate value " + value + " found in row " + row, seen[value]);
                seen[value] = true; // Mark this value as seen
            }
        }
    }

    /**
     * Test that all columns contain unique numbers.
     */
    @Test
    public void testColumnsContainUniqueNumbers() {
        for (int col = 0; col < 9; col++) {
            boolean[] seen = new boolean[10]; // Index 0 is unused
            for (int row = 0; row < 9; row++) {
                int value = completeBoard.getCell(row, col).getValue();
                assertFalse("Duplicate value " + value + " found in column " + col, seen[value]);
                seen[value] = true; // Mark this value as seen
            }
        }
    }

    /**
     * Test that all 3x3 subgrids contain unique numbers.
     */
    @Test
    public void testSubgridsContainUniqueNumbers() {
        for (int gridRow = 0; gridRow < 3; gridRow++) {
            for (int gridCol = 0; gridCol < 3; gridCol++) {
                boolean[] seen = new boolean[10]; // Index 0 is unused
                for (int row = gridRow * 3; row < gridRow * 3 + 3; row++) {
                    for (int col = gridCol * 3; col < gridCol * 3 + 3; col++) {
                        int value = completeBoard.getCell(row, col).getValue();
                        assertFalse("Duplicate value " + value + " found in subgrid (" + gridRow + ", " + gridCol + ")", seen[value]);
                        seen[value] = true; // Mark this value as seen
                    }
                }
            }
        }
    }
}
