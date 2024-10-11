/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sudoku;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Abstract class representing a game of Sudoku.
 * 
 * This class manages the core functionality of a Sudoku game, including user interactions,
 * game setup, timing, hint management, and game state saving and loading.
 * 
 * Subclasses should implement the abstract methods to provide specific game logic.
 * 
 * @author paige
 */
public abstract class Game {
    
    // Variables for a game of Sudoku
    protected User user;                       // The current user playing the game
    protected Board board;                     // The current puzzle board
    protected PuzzleGenerator puzzleGenerator; // Generates the Sudoku puzzle
    protected CompleteBoard completeBoard;     // The fully solved board
    protected int difficulty;                  // Difficulty level of the board
    protected Timer timer;                     // Timer to track the time spent on the game
    protected HintSystem hint;                 // System to manage hint functionality
    protected int lives;                       // Number of lives remaining in the game
    protected boolean gameRunning;             // A flag indicating if game is running
    
    // Variables for handling user inputs and saving data
    protected Scanner scan;                    // Scanner to read user input
    protected FileHandler fileHandler;         // Handler for reading and writing user data to files
    protected HashMap<String, User> users;     // Map of users for storing and accessing user data
    
    /**
     * Constructor for initializing a new game of Sudoku.
     * 
     * Initializes the game with a given user, sets up the game components including the timer, scanner,
     * file handler, and hint system, and then sets up the board and starts the game.
     * 
     * @param user The user who is playing the game.
     */
    public Game(User user) {
        // Initializes variables
        this.board = new Board();
        this.completeBoard = new CompleteBoard();
        this.gameRunning = false;
        this.timer = new Timer();
        this.hint = new HintSystem();
        
        this.user = user;
        this.scan = new Scanner(System.in);
        this.fileHandler = new FileHandler();
        
        this.users = fileHandler.readUserData(); // Loads existing data from file
    }
    
    // Abstract methods to be implemented by subclasses
    protected abstract void setupBoard();  // Method to set up the board
    protected abstract void startGame();   // Method to start the game
    protected abstract void endGame();     // Method to end the game

    /**
     * Starts the game timer.
     */
    protected void startTimer() {
        timer.start();
    }

    /**
     * Stops the game timer.
     */
    protected void stopTimer() {
        timer.stop();
    }

    /**
     * Resets the game timer to zero.
     */
    protected void resetTimer() {
        timer.reset();
    }

    /**
     * Updates the user's best time in the user data.
     * 
     * Updates the user data in the HashMap and writes it to the file to ensure the best time is saved.
     */
    protected void updateBestTime() {
        this.users.put(user.getUsername(), user); // Update the user's best time in the user map
        fileHandler.writeUserData(this.users);    // Write updated user data to the file
    }
    
    /**
     * Prompts the user for an integer input within a specified range and validates the input.
     * 
     * This method will continuously prompt the user until a valid integer input is provided or 
     * the user chooses to exit the input loop by entering 'X'.
     * 
     * @param lowerRange The lower bound of the valid input range (inclusive).
     * @param upperRange The upper bound of the valid input range (inclusive).
     * @return A valid integer input within the specified range or -1.
     */
    protected int getValidInput(int lowerRange, int upperRange) {
        final String EXIT_COMMAND = "X";
        final String PAUSE_COMMAND = "P";
        final String HINT_COMMAND = "H";

        while (true) {
            String input = scan.nextLine().trim(); // Trim to remove leading/trailing spaces

            switch (input.toUpperCase()) {
                case EXIT_COMMAND: // Exits the game if 'X' is pressed
                    this.exitGame();
                    break;

                case PAUSE_COMMAND:
                    this.pauseGame(); // Pauses the game is 'P' is pressed
                    return -1; // Returning -1 as a special value to indicate a special request

                case HINT_COMMAND:
                    this.getHint(); // Provides the user a hint if 'H' is pressed
                    return -1; // Returning -1 as a special value to indicate a special request

                default:
                    // Attempt to parse the input as an integer
                    try {
                        int numInput = Integer.parseInt(input);
                        if (numInput >= lowerRange && numInput <= upperRange) {
                            return numInput; // Valid input within range
                        } else {
                            System.out.println("Please enter a number within the range " + lowerRange + " to " + upperRange + ".");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                    }
                    break;
            }
        }
    }
    
    /**
     * Provides the value of a given cell as a hint.    
     * 
     * The method prompts a user for the cell that they wish to use the hint on, 
     * then provides the value found in that cell.
     */
    private void getHint() {
        // Checks if user has any hints remaining
        if (hint.getHintCount() == 0) {
            System.out.println("\nNo hints remaining!");
            return; // Exits method is user has no hints left
        }
        
        // Prompts the user if they would like to use a hint
        System.out.println("\n -> Hints Remaining: " + hint.getHintCount());
        System.out.println("Are you sure you would like a hint?");
        System.out.println("  (1) Yes");
        System.out.println("  (2) No");
        if (getValidInput(1, 2) == 2) return; // Exits method if user decides not to use a hint
        
        // Prompts the user to input a row number for the cell they wish to fill
        System.out.print("Please select the row (1-9): ");
        int row = getValidInput(1, 9) - 1;

        // Prompts the user to input a column number for the cell they wish to fill
        System.out.print("Select the column (1-9): ");
        int col = getValidInput(1, 9) - 1;
        
        // Confirms that the user wishes to use their hint for that cell
        System.out.println("\nAre you sure you want a hint for " + "(" + (row+1) + "," + (col+1) + ")?");
        System.out.println("  (1) Yes");
        System.out.println("  (2) No");
        if (getValidInput(1, 2) == 2) return;
        
        // Reveal hint
        int cellValue = hint.getHint(row, col, completeBoard);
        System.out.println("\nThe value of " + "(" + (row+1) + "," + (col+1) + ") is " + cellValue + "!");
        System.out.println(" -> Hints Left: " + hint.getHintCount());
    }
    
    /**
     * Pauses the Sudoku game.
     * 
     * This method pauses the game by pausing the timer used to measure how long it takes a user to solve the puzzle.
     * Users are shown their current time spent on the puzzle and are given the option
     * to resume the game or quit. 
     */
    private void pauseGame() {
        boolean isPaused = true; // Initialize pause state
        timer.pause();           // Pauses the timer
        
        // Prints out a box with information for the user
        System.out.println();
        
        // Array of text for the box
        String[] pauseInfo = {
            "The game is now paused!",
            "Current time: " + timer.getTime(),
            " -> Type 'P' to resume the game",
            " -> Type 'X' to exit the game"
        };

        // Find the length of the longest string for the box width
        int maxLength = 0;
        for (String line : pauseInfo) {
            if (line.length() > maxLength) {
                maxLength = line.length();
            }
        }

        int boxWidth = maxLength + 4; // Adding padding for the box borders

        // Print the top border of the box
        System.out.print("    ");
        for (int i = 0; i < boxWidth; i++) {
            System.out.print("-");
        }
        System.out.println();

        // Print each line of controls within the box
        for (String line : pauseInfo) {
            System.out.println("    | " + line + " ".repeat(boxWidth - line.length() - 3) + "|");
        }

        // Print the bottom border of the box
        System.out.print("    ");
        for (int i = 0; i < boxWidth; i++) {
            System.out.print("-");
        }
        System.out.println();
        
        // Keeps the game paused until user enters 'P' or 'X'
        while (isPaused) {
            String input = scan.nextLine().trim(); // Gets user input

            // If the user inputs 'P', the game unpauses
            if (input.equalsIgnoreCase("P")) {
                isPaused = false; // Unpauses the game
            }
            else if (input.equalsIgnoreCase("X")) {
                exitGame(); // Exits game
            }
            else {
                System.out.print("Invalid input. Enter 'P' to resume the game or 'X' to exit: ");
            }
        }
        
        // Resumes the timer
        timer.resume();
    }
    
    /**
     * Exits the Sudoku game and gives users a chance to save the current board state.
     * 
     * If the user has already saved a board, the user will have to confirm if they wish to override that save.
     * User data is saved regardless before exiting the game.
     */
    protected void exitGame() {
        // Checks if the board is not complete and a game is ongoing
        if (!board.isComplete() && gameRunning) {
            // Prompts the user to save the current board
            System.out.println("\nWould you like to save the current board before exiting?");
            System.out.println("  (1) Yes");
            System.out.println("  (2) No");
            int userChoice = getValidInput(1, 2); // Get user input for saving the board

            // If the user chooses to save the board
            if (userChoice == 1) {
                if (fileHandler.checkForSavedBoard(user)) {
                    System.out.println("You appear to already have a board saved, continuing would override that save.");
                    System.out.println("  (1) Yes, I would like to override that save");
                    System.out.println("  (2) No");
                    userChoice = getValidInput(1, 2); // Get user input for overriding the save
                    
                    if (userChoice == 2) {
                        // Notifies the user that the game is exiting without saving
                        System.out.println("\nExiting Sudoku game without saving. See you next time!");

                        // Saves the user data before exiting
                        fileHandler.writeUserData(users);
                        System.exit(0); // Exiting the program
                    }
                }

                // Saves the current game state if there is not a game saved or the user chooses to override save
                this.saveGame();
                System.out.println("Sudoku board has been saved.");
            }
            
        }
        
        // Display all of users best times
        System.out.println("\nYour best times are: ");
        user.printAllTimes();
        
        // Notifies the user that the game is exiting
        System.out.println("\nExiting Sudoku game. See you next time!");
        
        // Saves the user data before exiting
        fileHandler.writeUserData(users);
        System.exit(0); // Exiting the program
    }
    
    /**
     * Saves the current board state.
     * 
     * This method calls the file handler to save the current game state, including
     * the user's best time, number of lives, hint count, and the current and completed
     * boards.
     */
    private void saveGame() {
        fileHandler.saveSudokuBoard(
                user, 
                difficulty,
                timer.getMillis(), 
                lives, 
                hint.getHintCount(), 
                board.getGrid(), 
                completeBoard.getGrid());
    }
}
