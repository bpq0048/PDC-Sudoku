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
public final class Game {

    private User user;                       // The current user playing the game
    private Board board;                     // The current puzzle board
    private PuzzleGenerator puzzleGenerator; // Generates the Sudoku puzzle
    private CompleteBoard completeBoard;     // The fully solved board
    private int difficulty;                  // Difficulty level of the board
    private Timer timer;                     // Timer to track the time spent on the game
    private HintSystem hint;                 // System to manage hint functionality
    private int lives;                       // Number of lives remaining in the game
    private boolean gameRunning;             // A flag indicating if game is running
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

    public Game(User user) {
        initializeGame(user);
        this.useSavedGame = true;
        this.setupBoard();
        this.startGame();
    }

    // Initialize common variables
    private void initializeGame(User user) {
        this.user = user;
        this.board = new Board();
        this.completeBoard = new CompleteBoard();
        this.gameRunning = false;
        this.timer = new Timer();
        this.hint = new HintSystem();
        this.dbHandler = new DBHandler();
    }
    
    public boolean validInput(int row, int col, int value) {
        return this.completeBoard.getCell(row, col).getValue() == value;
    }

    // Setup the board for the game
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
        board = puzzleGenerator.getPuzzleBoard();
    }

    private void loadGame() {
        HashMap<String, String> boardData = dbHandler.getSavedGame(user.getUserId());

        // Initialize variables with saved data
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
        dbHandler.removeSavedBoard(user.getUserId());
        updateBestTime();
    }

    protected void startTimer() {
        timer.start();
    }

    protected void stopTimer() {
        timer.stop();
    }

    protected void resetTimer() {
        timer.reset();
    }

    protected void updateBestTime() {
        if (user.getBestTime(difficulty) == 0 || timer.getMillis() < user.getBestTime(difficulty)) {
            dbHandler.updateBestTime(user.getUserId(), difficulty, timer.getMillis());
        }
    }

    private void pauseGame() {
        timer.pause();
        this.gameRunning = false;
    }

    private void resumeGame() {
        timer.resume();
        this.gameRunning = true;
    }

    public void exitGame(boolean saveBoard) {
        if (saveBoard && !board.isComplete()) {
            saveGame();
        }
    }

    public void saveGame() {
        dbHandler.saveGame(
                user.getUserId(),
                difficulty,
                timer.getMillis(),
                lives,
                hint.getHintCount(),
                board.toString(),
                completeBoard.toString());
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public PuzzleGenerator getPuzzleGenerator() {
        return puzzleGenerator;
    }

    public void setPuzzleGenerator(PuzzleGenerator puzzleGenerator) {
        this.puzzleGenerator = puzzleGenerator;
    }

    public CompleteBoard getCompleteBoard() {
        return completeBoard;
    }

    public void setCompleteBoard(CompleteBoard completeBoard) {
        this.completeBoard = completeBoard;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public int getHintCount() {
        return hint.getHintCount();
    }
    
    public int useHint(int row, int col) {
        return hint.getHint(row, col, completeBoard);
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
}
