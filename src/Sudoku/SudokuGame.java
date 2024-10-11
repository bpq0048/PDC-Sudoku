/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sudoku;

import java.util.HashMap;

/**
 * SudokuGame class extends the Game class to implement a Sudoku-specific game with functionalities.
 * 
 * This class manages setting up the board, starting the game, loading saved games, and ending the game.
 * Additionally, this class manages a selecting the difficulty of the Sudoku puzzle and the main game loop.
 *
 * @author paige
 */
public class SudokuGame extends Game {
    
    private boolean useSavedGame; // Flag indicating whether the game is using a saved game
    
    /**
     * Constructor for initializing a new normal game of Sudoku.
     * 
     * Sets up a new game instance by using the Game class with the specified user,
     * and configures the game settings before it sets up
     * the Sudoku board and starts the game loop.
     * 
     * @param user The user who is playing the game.
     */
    public SudokuGame(User user) {
        super(user);
        this.useSavedGame = false;
        this.setupBoard();
        this.startGame();
    }
    
    /**
     * Initializes and runs the game loop for a round of Sudoku.
     * 
     * This method manages the main gameplay loop, where it prompts the user for input,
     * checks the validity of their moves, updates the board, and handles game states
     * such as checking for win conditions or losing lives.
     */
    private void play() {
        // Main game loop: continues to run as long as the board is not completely filled and the player still has lives remaining
        while (!board.isComplete() && lives != 0) {
            
            // Displays the current state of the Sudoku board to the player
            System.out.println();
            board.printBoard();
            
            // Prompts the user to input a row number for the cell they wish to fill
            System.out.print("Select the row (1-9): ");
            int row = getValidInput(1, 9);
            if (row == -1) continue; // Restarts the loop if user paused the game or picked a hint
            
            // Prompts the user to input a column number for the cell they wish to fill
            System.out.print("Select the column (1-9): ");
            int col = getValidInput(1, 9);
            if (col == -1) continue; // Restarts the loop if user paused the game or picked a hint
            
            // Checks if the selected cell is already filled; if so, prompts the user to select a different cell
            if (!board.isEmptyCell(row-1, col-1)) {
                System.out.println("That spot is already filled! Please select a different cell.");
                continue; // Skip the rest of the loop iteration and prompt for input again
            }
            
            // Prompts the user to input a number to place in the selected cell
            System.out.print("Enter the number (1-9): ");
            int num =getValidInput(1, 9);
            if (num == -1) continue; // Restarts the loop if user paused the game or picked a hint
            
            // Verifies if all input information is correct
            System.out.println("Are you sure you want to add " + num + " at (" + row + "," + col + ")?");
            System.out.println("  (1) Yes");
            System.out.println("  (2) No");
            int input = getValidInput(1, 2);
            if (input == 2 || input == -1) continue; // Restarts the loop if user paused the game, picked a hint, or picked no
            
            // Checks the user's move by comparing their input to the completed board's cell value
            if (completeBoard.getCell(row-1, col-1).getValue() == num) {
                // If correct, update the board with the user's input
                board.updateCell(row-1, col-1, num);
                System.out.println("That's Correct!");
            }
            else {
                // If incorrect, decrements the user's lives
                lives--;
                System.out.println("Incorrect! Lives remaining: " + lives);
            }
        }
        
        // Ends the game
        this.endGame();
    }
    
    /**
     * Prompts the user to select the difficulty level for the current round of Sudoku.
     * 
     * The method gets a chosen difficulty level, which is then used to determine the number of
     * cells removed from a complete Sudoku board to create the puzzle.
     * 
     * @return An integer representing the difficulty level chosen by the user.
     */
    private int selectDifficulty() {
        // Display the difficulty level options to the user
        System.out.println("\nPlease select your difficulty level:");
        System.out.println("  (1) Beginner");
        System.out.println("  (2) Easy");
        System.out.println("  (3) Medium");
        System.out.println("  (4) Hard");
        System.out.println("  (5) Expert");
        
        // Prompts the user for a valid input and returns the selected difficulty
        return getValidInput(1, 5);
    }
    
    /**
     * Gives users a chance to load a previously saved game (given a game is saved).
     * 
     * Checks if a user has a saved Sudoku game, if they do it gives them the option to load and continue the game.
     */
    private void loadGame() {
        // Checks if user has a saved game
        if (!fileHandler.checkForSavedBoard(user)) {
            return; // returns if no saved game is found
        }
        
        // Loads saved data in a HashMap
        HashMap<String, String> boardData = fileHandler.loadSavedBoard(user);
        
        // Initializes saved data as variables
        int savedDifficulty = Integer.parseInt(boardData.get("difficulty"));
        int savedLives = Integer.parseInt(boardData.get("lives"));
        Timer savedTimer = new Timer();
        savedTimer.setElapsedTime(Long.parseLong(boardData.get("elapsedTime")));
        int savedHints = Integer.parseInt(boardData.get("hints"));
        
        // Gets difficulty level name
        String savedDifficultyName;
        switch(savedDifficulty) {
            case 1:
                savedDifficultyName = "Beginnier";
                break;
            case 2:
                savedDifficultyName = "Easy";
                break;
            case 3:
                savedDifficultyName = "Medium";
                break;
            case 4:
                savedDifficultyName = "Hard";
                break;
            case 5:
                savedDifficultyName = "Expert";
                break;
            default:
                savedDifficultyName = "Unknown";
                break;
        }
        
        // Prints out a box with information for the user
        System.out.println();
        
        // Array of text for the box
        String[] pauseInfo = {
            "You have a saved game! Game details: ",
            " -> Difficulty: " + savedDifficultyName,
            " -> Time: " + savedTimer.getTime(),
            " -> Lives: " + savedLives,
            " -> Hints: " + savedHints,
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
        
        // Promps user to decide to load saved game
        System.out.println("\nWould you like to load this board and continue?");
        System.out.println("  (1) Yes");
        System.out.println("  (2) No");
        System.out.println("  (3) I would like to delete this board");
        int userChoice = getValidInput(1, 3); // Get user input for loading saved game
        
        if (userChoice == 2) return; // If user chooses not to load game, exit method
        if (userChoice == 3) {
            fileHandler.removeSudokuBoard(user);
            System.out.println("Board has been removed!"); 
            return;
        }
        
        // Initializes all variables with saved data
        this.difficulty = Integer.parseInt(boardData.get("difficulty"));
        this.lives = Integer.parseInt(boardData.get("lives"));
        this.timer.setElapsedTime(Long.parseLong(boardData.get("elapsedTime")));
        this.hint.setHintCount(Integer.parseInt(boardData.get("hints")));
        this.board.loadBoard(boardData.get("puzzleBoard"));
        this.completeBoard.loadBoard(boardData.get("completedBoard"));
        
        // Set status to show that a board has been loaded
        useSavedGame = true;
    }

    /**
     * Initializes the game of Sudoku by creating the board.
     * 
     * The method gets the difficulty level for the puzzle, initializes the users lives and hints,  
     * then generates a puzzle and completed board for the user to solve.
     */
    @Override
    protected void setupBoard() {
        // Let user load saved game if one exists
        loadGame();
        
        // Return if a board has been loaded
        if (useSavedGame) {
            return;
        }
        
        // Prompts the user to select the difficulty level and stores the selection
        this.difficulty = selectDifficulty();
        
        // Initalizes the number of lives and hints a user has
        this.lives = 3;
        this.hint.setHintCount(3);
        
        // Generates  Sudoku board
        puzzleGenerator = new PuzzleGenerator(difficulty);  // Generates puzzle by making a complete board and removing cells based on difficulty
        completeBoard = puzzleGenerator.getCompleteBoard(); // Gets the fully complete board
        board = puzzleGenerator.getPuzzleBoard();           // Gets the puzzle board for users to solve
    }

    /**
     * Starts the game of Sudoku.
     * 
     * This method initializes the game by resetting the timer, starting the timer and running the main game loop.
     */
    @Override
    protected void startGame() {
        this.resetTimer();       // Resets the timer
        this.startTimer();       // Starts timer
        this.gameRunning = true; // Sets game running flag to true
        this.play();             // Run the main game method
    }

    /**
     * End the game of Sudoku after the user completes the board or runs out of lives.
     * 
     * This method stops the timer and checks if the user has completed the board.
     * If completed, it updates the user's best time if necessary and congratulates them.
     * If not completed, it notifies the user of the loss.
     * The method then prompts the user to either play another round or exit the game.
     */
    @Override
    protected void endGame() {
        this.stopTimer(); // Stops the timer
        gameRunning = false;
        
        // Checks if board was completed
        if (board.isComplete()) {
            // Congragulate the user if solved
            System.out.println("\nCongratulations! You've solved the puzzle!");
            System.out.println("You solved the puzzle in " + timer.getTime());

            // Updates best time if user gets a quicker time than their current best time
            if (timer.getMillis() < user.getBestTime(difficulty) && board.isComplete() || user.getBestTime(difficulty) == 0) {
                System.out.println("That's a new best time!");
                user.setBestTimes(timer.getMillis(), difficulty); // Sets the best time for the difficulty completed
                this.updateBestTime(); // Saves the users best times
            }
            
            if (useSavedGame) {
                fileHandler.removeSudokuBoard(user);
                useSavedGame = false;
            }
        }
        else {
            // Notify the user they lost the game
            System.out.println("\nUh oh! You lost!");
        }
        
        // Prompts the user to play another round or exit the game
        System.out.println("\nWould you like to play again?");
        System.out.println("  (1) Yes, I would like another puzzle");
        System.out.println("  (2) No, I would like to exit the game");
        int userChoice = getValidInput(1, 2); // Get user input for their choice
        
        if (userChoice == 2) exitGame(); // Exits game if they choose to exit
        
        // Reset the game if the user wants to play again
        this.setupBoard();
        this.startGame();
    }
}
