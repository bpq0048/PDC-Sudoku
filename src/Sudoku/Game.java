/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sudoku;

import java.util.HashMap;

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
public class Game {
    
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
    protected boolean useSavedGame;
    
    // Variables for handling user inputs and saving data
    protected FileHandler fileHandler;         // Handler for reading and writing user data to files
    protected HashMap<String, User> users;     // Map of users for storing and accessing user data
    
    /**
     * Constructor for initializing a new game of Sudoku.
     * 
     * Initializes the game with a given user, sets up the game components including the timer, scanner,
     * file handler, and hint system, and then sets up the board and starts the game.
     * 
     * @param user The user who is playing the game.
     * @param difficulty
     */
    public Game(User user, int difficulty) {
        // Initializes variables
        this.board = new Board();
        this.completeBoard = new CompleteBoard();
        this.gameRunning = false;
        this.timer = new Timer();
        this.hint = new HintSystem();
        
        this.user = user;
        this.difficulty = difficulty;
        
        this.fileHandler = new FileHandler();
        this.users = fileHandler.readUserData(); // Loads existing data from file
    
        this.setupBoard();
        this.startGame();
    }
    
    public Game(User user) {
        // Initializes variables
        this.board = new Board();
        this.completeBoard = new CompleteBoard();
        this.gameRunning = false;
        this.timer = new Timer();
        this.hint = new HintSystem();
        
        this.user = user;
        
        this.fileHandler = new FileHandler();
        this.users = fileHandler.readUserData();
        
        this.useSavedGame = true;
        
        this.setupBoard();
        this.startGame();
    }
    
    // Abstract methods to be implemented by subclasses
    public void setupBoard() {
        // Return if a board has been loaded
        if (useSavedGame) {
            // Let user load saved game if one exists
            loadGame();

            return;
        }
        
        // Initalizes the number of lives and hints a user has
        this.lives = 3;
        this.hint.setHintCount(3);
        
        // Generates  Sudoku board
        puzzleGenerator = new PuzzleGenerator(difficulty);  // Generates puzzle by making a complete board and removing cells based on difficulty
        completeBoard = puzzleGenerator.getCompleteBoard(); // Gets the fully complete board
        board = puzzleGenerator.getPuzzleBoard();           // Gets the puzzle board for users to solve
    }
    
    private void loadGame() {
        
        // Loads saved data in a HashMap
        HashMap<String, String> boardData = fileHandler.loadSavedBoard(user);
        
        
        // Initializes all variables with saved data
        this.difficulty = Integer.parseInt(boardData.get("difficulty"));
        this.lives = Integer.parseInt(boardData.get("lives"));
        this.timer.setElapsedTime(Long.parseLong(boardData.get("elapsedTime")));
        this.hint.setHintCount(Integer.parseInt(boardData.get("hints")));
        this.board.loadBoard(boardData.get("puzzleBoard"));
        this.completeBoard.loadBoard(boardData.get("completedBoard"));
    }
    
    public void startGame() {
        setupBoard();
        startTimer();
        gameRunning = true;
    }
    
    public void endGame() {
        stopTimer();
        gameRunning = false;
        updateBestTime();
    }

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
     * Pauses the Sudoku game.
     * 
     * This method pauses the game by pausing the timer used to measure how long it takes a user to solve the puzzle.
     * Users are shown their current time spent on the puzzle and are given the option
     * to resume the game or quit. 
     */
    private void pauseGame() {
        timer.pause();           // Pauses the timer
        this.gameRunning = false;
    }
    
    private void resumeGame() {
        timer.resume();           // Pauses the timer
        this.gameRunning = true;
    }
    
    /**
     * Exits the Sudoku game and gives users a chance to save the current board state.
     * 
     * If the user has already saved a board, the user will have to confirm if they wish to override that save.
     * User data is saved regardless before exiting the game.
     */
    public void exitGame(boolean saveBoard) {
        if (saveBoard && !board.isComplete()) {
            saveGame();
        }
        
        // Saves the user data before exiting
        fileHandler.writeUserData(users);
    }
    
    /**
     * Saves the current board state.
     * 
     * This method calls the file handler to save the current game state, including
     * the user's best time, number of lives, hint count, and the current and completed
     * boards.
     */
    public void saveGame() {
        fileHandler.saveSudokuBoard(
                user, 
                difficulty,
                timer.getMillis(), 
                lives, 
                hint.getHintCount(), 
                board.getGrid(), 
                completeBoard.getGrid());
    }
    
    
    // -- Getters for game properties --

    public User getUser() {
        return user;
    }

    public Board getBoard() {
        return board;
    }
    
    public PuzzleGenerator getPuzzleGenerator() {
        return puzzleGenerator;
    }

    public CompleteBoard getCompleteBoard() {
        return completeBoard;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public Timer getTimer() {
        return timer;
    }
    
    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public HintSystem getHint() {
        return hint;
    }
    
    public void setHints(int hint) {
        this.hint.setHintCount(hint);
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public boolean isGameRunning() {
        return gameRunning;
    }

    public FileHandler getFileHandler() {
        return fileHandler;
    }

    public HashMap<String, User> getUsers() {
        return users;
    }
    
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
    
    
}