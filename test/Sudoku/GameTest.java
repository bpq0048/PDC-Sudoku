/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Sudoku;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {

    private User testUser;
    private Game game;

    @Before
    public void setUp() {
        // Create a test user and initialize the game
        testUser = new User("testUser", "password123");
        game = new Game(testUser, 1); // Initialize game with difficulty level 1
    }

    @After
    public void tearDown() {
        // Clean up resources if necessary
        game = null;
        testUser = null;
    }

    @Test
    public void testGameInitialization() {
        // Test that the game initializes with the correct user and difficulty
        assertNotNull("Game should be initialized", game);
        assertEquals("User should match", testUser, game.getUser());
        assertEquals("Difficulty should be set to 1", 1, game.getDifficulty());
    }

    @Test
    public void testValidInput() {
        // Test valid input against the complete board
        // Assuming the complete board has a value at row 0, column 0
        int validValue = game.getCompleteBoard().getCell(0, 0).getValue();
        assertTrue("Valid input should return true", game.validInput(0, 0, validValue));
        
        // Test invalid input
        int invalidValue = validValue + 1; // Assuming this is not the correct value
        assertFalse("Invalid input should return false", game.validInput(0, 0, invalidValue));
    }

    @Test
    public void testLivesManagement() {
        // Test initial lives
        assertEquals("Initial lives should be 3", 3, game.getLives());
        
        // Simulate losing a life
        game.setLives(2);
        assertEquals("Lives should be decreased to 2", 2, game.getLives());
    }

    @Test
    public void testHintSystem() {
        // Test initial hints
        assertEquals("Initial hints should be 3", 3, game.getHintCount());
        
        // Use a hint
        int row = 0; // Example row
        int col = 0; // Example column
        int hintValue = game.useHint(row, col); // Get hint for the cell
        assertNotEquals("Hint value should not be -1", -1, hintValue); // Assuming -1 indicates no hint available
    }
}
