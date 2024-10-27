/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sudoku;

import java.sql.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages all database interactions for the Sudoku game.
 * 
 * This class handles user accounts, information, and game progress.
 * 
 * @author paige
 */
public class DBHandler {

    // Database connection details
    private static final String URL = "jdbc:derby://localhost:1527/Sudoku;create=true";
    private static final String USERNAME = "pdc";   // Database username 
    private static final String PASSWORD = "pdc";   // Database password
    
    private Connection conn;    // Database connection object
    private boolean dbSetUp;    // To track if the DB has been set up

    /**
     * Constructor to initialize the DBHandler and set up the database.
     */
    public DBHandler() {
        setupDB();
    }
    
    /**
     * Sets up the database by creating necessary tables if they do not exist.
     */
    public void setupDB() {
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD); // Establish database connection
            Statement statement = conn.createStatement();
            
            // Create USERS table if it does not exist
            if (!checkTableExists("USERS")) {
                statement.executeUpdate("CREATE TABLE USERS (" +
                    "user_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                    "username VARCHAR(50) UNIQUE NOT NULL, " +
                    "password VARCHAR(50) NOT NULL, " + 
                    "best_time_beginner INT, " + 
                    "best_time_easy INT, " +
                    "best_time_medium INT, " +
                    "best_time_hard INT, " +
                    "best_time_expert INT)");
            }

            // Create SAVEDGAMES table if it does not exist
            if (!checkTableExists("SAVEDGAMES")) {
                statement.executeUpdate("CREATE TABLE SAVEDGAMES (" +
                    "savedgame_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                    "user_id INT, " +
                    "difficulty VARCHAR(50), " +
                    "elapsed_time INT, " +
                    "lives INT, " +
                    "hints INT, " +
                    "board VARCHAR(255), " +
                    "puzzle_board VARCHAR(255), " +
                    "completed_board VARCHAR(255), " +
                    "FOREIGN KEY (user_id) REFERENCES USERS(user_id))");
            }
            
            this.dbSetUp = true;
            statement.close();

        } catch (Throwable e) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, "Database setup error", e);
        }
    }
    
    /**
     * Return the status of if the database is set up.
     * 
     * @return true if database is set up; false otherwise
     */
    public boolean isDBSetUp() {
        return dbSetUp; // Return the setup status
    }

    /**
     * Checks if a specific table exists in the database.
     * 
     * @param tableName The name of the table to check.
     * @return true if the table exists, false otherwise.
     */
    private boolean checkTableExists(String tableName) {
        try {
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rs = dbmd.getTables(null, null, tableName.toUpperCase(), null);
            return rs.next();
        } catch (SQLException e) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }
    
    /**
     * Adds a new user to the USERS table.
     * 
     * @param username The username of the new user.
     * @param password The password of the new user.
     * @return true if the user was added successfully, false otherwise.
     */
    public boolean addUser(String username, String password) {
        String sql = "INSERT INTO USERS (username, password) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, "Error adding user", e);
            return false;
        }
    }
    
    /**
     * Retrieves a user's information based on their username. 
     * 
     * @param username The username of the user to retrieve.
     * @return a User object containing user information, or null if not found.
     */
    public User getUser(String username) {
        String sql = "SELECT * FROM USERS WHERE username = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                int userId = rs.getInt("user_id");
                String password = rs.getString("password");
            
                // Retrieve best times for each difficulty
                long[] bestTimes = new long[5];
                bestTimes[0] = rs.getLong("best_time_beginner"); 
                bestTimes[1] = rs.getLong("best_time_easy");
                bestTimes[2] = rs.getLong("best_time_medium");
                bestTimes[3] = rs.getLong("best_time_hard");
                bestTimes[4] = rs.getLong("best_time_expert");

                return new User(userId, username, password, bestTimes);
            }
        } catch (SQLException e) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, "Error fetching user data", e);
            return null;
        }
        
        return null;
    }
    
    /**
    * Updates the best time for a specific difficulty in the USERS table.
    * 
    * @param userId The ID of the user whose best time is being updated.
    * @param difficulty The difficulty level for which the best time is being updated.
    * @param bestTime The new best time to set.
    * @return true if the update was successful, false otherwise.
    */
   public boolean updateBestTime(long userId, int difficulty, Long bestTime) {
       String sql = "";

       // Determine the correct SQL based on the difficulty
       switch (difficulty) {
           case 1:
               sql = "UPDATE USERS SET best_time_beginner = ? WHERE user_id = ?";
               break;
           case 2:
               sql = "UPDATE USERS SET best_time_easy = ? WHERE user_id = ?";
               break;
           case 3:
               sql = "UPDATE USERS SET best_time_medium = ? WHERE user_id = ?";
               break;
           case 4:
               sql = "UPDATE USERS SET best_time_hard = ? WHERE user_id = ?";
               break;
           case 5:
               sql = "UPDATE USERS SET best_time_expert = ? WHERE user_id = ?";
               break;
           default:
               throw new IllegalArgumentException("Invalid difficulty level: " + difficulty);
       }

       try (PreparedStatement ps = conn.prepareStatement(sql)) {
           ps.setObject(1, bestTime); // Use setObject to allow null values
           ps.setLong(2, userId);
           int rowsUpdated = ps.executeUpdate();

           return rowsUpdated > 0; // Returns true if at least one row was updated
       } catch (SQLException e) {
           Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, "Error updating best time", e);
           return false;
       }
   }

    /**
     * Saves game progress for a specific user. If a saved game already exists, it will be deleted before saving the new game.
     * 
     * @param userId The ID of the user.
     * @param difficulty The difficulty level of the game.
     * @param elapsedTime The time taken to play the game.
     * @param lives The number of lives remaining.
     * @param hints The number of hints used.
     * @param board The current state of the puzzle board.
     * @param puzzleBoard The original puzzle board.
     * @param completeBoard The completed state of the board.
     * @return true if the game was saved successfully, false otherwise.
     */
    public boolean saveGame(long userId, int difficulty, long elapsedTime, int lives, int hints, String board, String puzzleBoard, String completeBoard) {
        // SQL to delete any existing saved game for the user
        String deleteSql = "DELETE FROM SAVEDGAMES WHERE user_id = ?";
        // SQL to insert a new saved game
        String insertSql = "INSERT INTO SAVEDGAMES (user_id, difficulty, elapsed_time, lives, hints, board, puzzle_board, completed_board) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            // Start a transaction
            conn.setAutoCommit(false);

            // Delete existing saved game
            try (PreparedStatement deletePs = conn.prepareStatement(deleteSql)) {
                deletePs.setLong(1, userId);
                deletePs.executeUpdate();
            }

            // Insert new saved game
            try (PreparedStatement insertPs = conn.prepareStatement(insertSql)) {
                insertPs.setLong(1, userId);
                insertPs.setInt(2, difficulty);
                insertPs.setLong(3, elapsedTime);
                insertPs.setInt(4, lives);
                insertPs.setInt(5, hints);
                insertPs.setString(6, board);
                insertPs.setString(7, puzzleBoard);
                insertPs.setString(8, completeBoard);
                insertPs.executeUpdate();
            }

            // Commit the transaction
            conn.commit();
            return true;
        } catch (SQLException e) {
            try {
                // Roll back if there was an error
                conn.rollback();
            } catch (SQLException rollbackEx) {
                Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, "Rollback failed", rollbackEx);
            }
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, "Error saving game", e);
            return false;
        } finally {
            // Restore auto-commit mode
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, "Error restoring auto-commit mode", e);
            }
        }
    }
    
    /**
     * Checks if the specified user has any saved games.
     * 
     * @param userId The ID of the user to check.
     * @return true if the user has saved games, false otherwise.
     */
    public boolean hasSavedGame(long userId) {
        String sql = "SELECT COUNT(*) FROM SAVEDGAMES WHERE user_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Returns true if there is at least one saved game
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, "Error checking for saved games", e);
        }
        return false;
    }
    
    /**
     * Retrieves a saved game for a specific user.
     * 
     * @param userId The ID of the user.
     * @return a HashMap containing the saved game details, or null if no game found.
     */
    public HashMap<String, String> getSavedGame(long userId) {
        String sql = "SELECT * FROM SAVEDGAMES WHERE user_id = ?";
        HashMap<String, String> savedGameData = new HashMap<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    savedGameData.put("difficulty", rs.getString("difficulty")); // Assumed VARCHAR
                    savedGameData.put("lives", String.valueOf(rs.getInt("lives")));

                    long elapsedTime = rs.getLong("elapsed_time");
                    savedGameData.put("elapsedTime", rs.wasNull() ? "0" : String.valueOf(elapsedTime));

                    savedGameData.put("hints", String.valueOf(rs.getInt("hints")));
                    savedGameData.put("board", rs.getString("board") != null ? rs.getString("board") : "");
                    savedGameData.put("puzzleBoard", rs.getString("puzzle_board") != null ? rs.getString("puzzle_board") : "");
                    savedGameData.put("completedBoard", rs.getString("completed_board") != null ? rs.getString("completed_board") : "");
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, "Error loading saved game", e);
        }

        return savedGameData;
    }
    
    /**
     * Removes a saved game for a specific user.
     * 
     * @param userId The ID of the user.
     */
    public void removeSavedBoard(long userId) {
        String sql = "DELETE FROM SAVEDGAMES WHERE user_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ps.executeUpdate();
            
        } catch (SQLException e) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, "Error removing saved board for user ID: " + userId, e);
        }
    }

    /**
     * Closes the database connection when it is no longer needed.
     */
    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, "Error closing database connection", e);
        }
    }
}
