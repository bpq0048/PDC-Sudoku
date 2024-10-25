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
        
        // Reset the game if the user wants to play again
        this.setupBoard();
        this.startGame();
    }
}
