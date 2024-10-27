/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Sudoku;

import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class DBHandlerTest {

    private DBHandler instance;

    @BeforeClass
    public static void setUpClass() {
        // Any setup needed for all tests (e.g., initialize shared resources).
    }

    @AfterClass
    public static void tearDownClass() {
        // Cleanup after all tests are run.
    }

    @Before
    public void setUp() {
        instance = new DBHandler();
        instance.setupDB();  // Setup the database before each test
    }

    @After
    public void tearDown() {
        instance.closeConnection();  // Ensure connection is closed after each test
    }

    @Test
    public void testSetupDB() {
        // Assuming setupDB initializes some database structures
        assertTrue("Database should be set up successfully", instance.isDBSetUp());
    }

    @Test
    public void testAddUser() {
        String username = "testUser";
        String password = "password123";
        
        instance.addUser(username, password);
        User result = instance.getUser(username);
        assertNotNull("User should be added successfully", result);
    }

    @Test
    public void testGetUser() {
        String username = "testUser";
        
        User result = instance.getUser(username);
        assertNotNull("Should return a user", result);
    }

    @Test
    public void testUpdateBestTime() {
        long userId = 1L;  // Use a valid userId from the test setup
        int difficulty = 1;  // Assuming 1 corresponds to a valid difficulty level
        Long bestTime = 120L; // Example time in seconds

        boolean result = instance.updateBestTime(userId, difficulty, bestTime);
        assertTrue("Best time should be updated successfully", result);
    }

    @Test
    public void testSaveGame() {
        long userId = 1L;  // Use a valid userId from the test setup
        int difficulty = 1;  // Valid difficulty level
        long elapsedTime = 300L; // Example elapsed time
        int lives = 3;
        int hints = 2;
        String board = "123456789...";
        String puzzleBoard = "1...34567...";
        String completeBoard = "123456789...";

        boolean result = instance.saveGame(userId, difficulty, elapsedTime, lives, hints, board, puzzleBoard, completeBoard);
        assertTrue("Game should be saved successfully", result);
    }

    @Test
    public void testHasSavedGame() {
        long userId = 1L;  // Use a valid userId from the test setup
        instance.saveGame(userId, 1, 100L, 3, 2, "boardString", "puzzleString", "completeString");
        
        boolean result = instance.hasSavedGame(userId);
        assertTrue("Should indicate that there is a saved game", result);
    }

    @Test
    public void testGetSavedGame() {
        long userId = 1L;  // Use a valid userId from the test setup
        HashMap<String, String> expectedSavedGame = new HashMap<>();
        expectedSavedGame.put("difficulty", "1");
        expectedSavedGame.put("elapsedTime", "100");
        expectedSavedGame.put("lives", "3");
        expectedSavedGame.put("hints", "2");
        expectedSavedGame.put("board", "123456789...");
        expectedSavedGame.put("puzzleBoard", "1...34567...");
        expectedSavedGame.put("completedBoard", "123456789..."); // Updated field name

        instance.saveGame(userId, 1, 100L, 3, 2, "123456789...", "1...34567...", "123456789...");

        HashMap<String, String> result = instance.getSavedGame(userId);
        assertNotNull("Saved game should not be null", result);

        // Debugging output
        System.out.println("Result from getSavedGame: " + result);

        assertEquals("Saved game should match", expectedSavedGame, result);
    }

    @Test
    public void testRemoveSavedBoard() {
        long userId = 1L;  // Use a valid userId from the test setup
        instance.saveGame(userId, 1, 100L, 3, 2, "boardString", "puzzleString", "completeString");
        instance.removeSavedBoard(userId);
        
        boolean result = instance.hasSavedGame(userId);
        assertFalse("Saved game should be removed", result);
    }

    @Test
    public void testCloseConnection() {
        instance.closeConnection();
    }
}
