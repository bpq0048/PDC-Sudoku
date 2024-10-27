/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Sudoku;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PuzzleGeneratorTest {
    
    private PuzzleGenerator puzzleGenerator;

    @Before
    public void setUp() {
        // Set up the PuzzleGenerator with a medium difficulty level for testing
        puzzleGenerator = new PuzzleGenerator(3);
    }
    
    @After
    public void tearDown() {
        puzzleGenerator = null; // Clean up after each test
    }

    /**
     * Test of getPuzzleBoard method, of class PuzzleGenerator.
     */
    @Test
    public void testGetPuzzleBoard() {
        System.out.println("getPuzzleBoard");
        
        Board result = puzzleGenerator.getPuzzleBoard();
        assertNotNull("Puzzle board should not be null", result);
    }

    /**
     * Test of getCompleteBoard method, of class PuzzleGenerator.
     */
    @Test
    public void testGetCompleteBoard() {
        System.out.println("getCompleteBoard");
        
        CompleteBoard result = puzzleGenerator.getCompleteBoard();
        assertNotNull("Complete board should not be null", result);
        
        // Optional: Check that the complete board is fully filled (no empty cells)
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                assertNotEquals("Complete board should not have empty cells", 0, result.getCell(row, col).getValue());
            }
        }
    }
}
