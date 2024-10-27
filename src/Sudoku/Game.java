/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sudoku;

import java.util.HashMap;

/**
 * Class representing a game of Sudoku.
 * 
 * This class manages the core functionality of a Sudoku game, including user interactions,
 * game setup, timing, hint management, and game state saving and loading.
 * 
 * @author paige
 */
public class Game {

    private User user;                       // The current user playing the game
    private PuzzleGenerator puzzleGenerator; // Generates the Sudoku puzzle
    private Board board;                     // The current board
    private Board puzzleBoard;               // The puzzle board
    private CompleteBoard completeBoard;     // The fully solved board
    private int difficulty;                  // Difficulty level of the board
    private Timer timer;                     // Timer to track the time spent on the game
    private HintSystem hint;                 // System to manage hint functionality
    private int lives;                       // Number of lives remaining in the game
    private boolean useSavedGame;            // Flag indicating if a saved game is being used
    private DBHandler dbHandler;             // Database handler for saving/loading user data

    /**
     * Constructor for initializing a new game of Sudoku.
     * 
     * Initializes the game with a given user, sets up the game components including the timer, hint system,
     * and then sets up the board and starts the game.
     * 
     * @param user The user who is playing the game.
     * @param difficulty The difficulty level of the Sudoku puzzle.
     */
    public Game(User user, int difficulty) {
        initializeGame(user);
        this.difficulty = difficulty;
        this.setupBoard();
        this.startGame();
    }

    /**
     * Constructor for loading a preexisting game of Sudoku. 
     * 
     * Loads an existing game for a given user, sets up the game components including the timer, hint system,
     * and then sets up the board and starts the game.
     * 
     * @param user The user who is playing the game.
     */
    public Game(User user) {
        initializeGame(user);
        this.useSavedGame = true;
        this.setupBoard();
        this.startGame();
    }

    /**
     * Initializes common variables for the game.
     * 
     * @param user The user playing the game.
     */
    private void initializeGame(User user) {
        this.user = user;
        this.board = new Board();
        this.puzzleBoard = new Board();
        this.completeBoard = new CompleteBoard();
        this.timer = new Timer();
        this.hint = new HintSystem();
        this.dbHandler = new DBHandler();
    }

    /**
     * Validates the input value against the complete board.
     * 
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @param value The value to validate.
     * @return true if the value is valid; false otherwise.
     */
    public boolean validInput(int row, int col, int value) {
        return this.completeBoard.getCell(row, col).getValue() == value;
    }

    /**
     * Sets up the board for the game, generating a new puzzle or loading a saved game.
     */
    public void setupBoard() {
        if (useSavedGame) {
            loadGame();
            return;
        }

        // Initialize lives and hints
        this.lives = 3;
        this.hint.setHintCount(3);

        // Generate the Sudoku board
        puzzleGenerator = new PuzzleGenerator(difficulty);
        completeBoard = puzzleGenerator.getCompleteBoard();
        puzzleBoard = puzzleGenerator.getPuzzleBoard();
        
        board = new Board(puzzleBoard);
    }

    /**
     * Loads a saved game from the database for the current user.
     */
    private void loadGame() {
        HashMap<String, String> boardData = dbHandler.getSavedGame(user.getUserId());

        // Initialize variables with saved data
        this.difficulty = Integer.parseInt(boardData.get("difficulty"));
        this.lives = Integer.parseInt(boardData.get("lives"));
        this.timer.setElapsedTime(Long.parseLong(boardData.get("elapsedTime")));
        this.hint.setHintCount(Integer.parseInt(boardData.get("hints")));
        this.board.loadBoard(boardData.get("board"));
        this.puzzleBoard.loadBoard(boardData.get("puzzleBoard"));
        this.completeBoard.loadBoard(boardData.get("completedBoard"));
    }

    /**
     * Starts the game by setting up the board, saving the game, and starting the timer.
     */
    public void startGame() {
        setupBoard();
        saveGame();
        startTimer();
    }

    /**
     * Ends the game, stopping the timer and updating the user's best time if applicable.
     */
    public void endGame() {
        stopTimer();
        dbHandler.removeSavedBoard(user.getUserId());
        updateBestTime();
    }

    /**
     * Ends the game after a lost, stopping the timer.
     */
    public void lostGame() {
        stopTimer();
        dbHandler.removeSavedBoard(user.getUserId());
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
     * Updates the user's best time if the current time is less than the previously recorded time.
     */
    protected void updateBestTime() {
        if (user.getBestTime(difficulty) == 0 || timer.getMillis() < user.getBestTime(difficulty)) {
            dbHandler.updateBestTime(user.getUserId(), difficulty, timer.getMillis());
        }
    }

    /**
     * Exits the game, optionally saving the board state if it is incomplete.
     * 
     * @param saveBoard Indicates whether to save the board state.
     */
    public void exitGame(boolean saveBoard) {
        if (saveBoard && !board.isComplete()) {
            saveGame();
        }
    }

    /**
     * Saves the current game state to the database.
     */
    public void saveGame() {
        dbHandler.saveGame(
                user.getUserId(),
                difficulty,
                timer.getMillis(),
                lives,
                hint.getHintCount(),
                board.toString(),
                puzzleBoard.toString(),
                completeBoard.toString());
    }

    /**
     * Gets the current game board.
     * 
     * @return The current board object.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Gets the puzzle board.
     * 
     * @return The puzzle board object.
     */
    public Board getPuzzleBoard() {
        return puzzleBoard;
    }

    /**
     * Gets the complete board.
     * 
     * @return The complete board object.
     */
    public CompleteBoard getCompleteBoard() {
        return completeBoard;
    }

    /**
     * Gets the game timer.
     * 
     * @return The timer object for the game.
     */
    public Timer getTimer() {
        return timer;
    }

    /**
     * Gets the number of hints available to the user.
     * 
     * @return The number of hints left.
     */
    public int getHintCount() {
        return hint.getHintCount();
    }
    
    /**
     * Uses a hint for a specified cell.
     * 
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @return The hint value for the specified cell.
     */
    public int useHint(int row, int col) {
        return hint.getHint(row, col, completeBoard);
    }

    /**
     * Gets the current number of lives remaining for the user.
     * 
     * @return The number of lives.
     */
    public int getLives() {
        return lives;
    }

    /**
     * Sets the number of lives for the user.
     * 
     * @param lives The number of lives to set.
     */
    public void setLives(int lives) {
        this.lives = lives;
    }
    
    
    // Getters for testing
    public User getUser() {
        return this.user;
    }
    
    public int getDifficulty() {
        return this.difficulty;
    }
    
    public DBHandler getDBHandler() {
        return this.dbHandler;
    }
}
