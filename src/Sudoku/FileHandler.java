/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sudoku;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Handles reading and writing to files.
 * 
 * This class manages user data and saved game states. The data is stored in a specified 
 * format in text files located in the resources directory.
 * 
 * @author paige
 */
public class FileHandler {
    
    private static final String USER_FILE = "./resources/users.txt";        // File location for user information
    private static final String SAVED_FILE = "./resources/saved_board.txt"; // File location for saved boards
    
    /**
     * Reads user data from a specified file and returns a HashMap containing user information.
     * 
     * This method opens a file, creates User objects from each line, then stores those objects
     * in a HashMap.
     * 
     * @return A HashMap where each key is a username and each value is a User object representing the user data.
     */
    public HashMap<String, User> readUserData() {
        HashMap<String, User> users = new HashMap<>();
        
        try {
            // Open the file input stream and create a scanner to read the file
            FileInputStream fileInput = new FileInputStream(USER_FILE);
            Scanner fileScanner = new Scanner(fileInput);
            
            // Process each line in the file
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                
                // Check if the line is not empty
                if (!line.trim().isEmpty()) {
                    // Create a user object from the line and add it to the HashMap
                    User user = User.fromString(line);
                    users.put(user.getUsername(), user);
                }
            }
            fileScanner.close();
            
        } catch (FileNotFoundException ex) {
            // Handle the case where file is not found (should not happen)
            System.out.println("File not found: " + ex.getMessage());
        }
        
        return users;
    }
    
    /**
     * Writes user data from a HashMap to a specified file.
     * 
     * This method takes a HashMap and writes each users data to a file.
     * 
     * @param users A HashMap where each key is a username and each value is a User object representing the user data.
     */
    public void writeUserData(HashMap<String, User> users) {
        try (FileOutputStream fileOutput = new FileOutputStream(USER_FILE);
             PrintWriter printWriter = new PrintWriter(fileOutput)) {

            // Write each users data to the file
            for (User user : users.values()) {
                printWriter.println(user.toString());
            }
            
        } catch (FileNotFoundException ex) {
            // Handle the case where file is not found (should not happen)
            System.out.println("File not found: " + ex.getMessage());
        } catch (IOException ex) {
            // Handle other I/O errors
            System.out.println("Unable to read user data: " + ex.getMessage());
        }
    }
    
    /**
     * Checks if user has a Sudoku board saved. 
     * 
     * This method opens a file, then searches for a user to check if they are present in the file. 
     * 
     * @param user The user that will be searched for.
     * @return true if user is found; false otherwise.
     */
    public boolean checkForSavedBoard(User user) {
        try {
            // Open the file input stream and create a scanner to read the file
            FileInputStream fileInput = new FileInputStream(SAVED_FILE);
            Scanner fileScanner = new Scanner(fileInput);
            
            // Process each line in the file
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                
                // Check if the line is not empty
                if (!line.trim().isEmpty() && line.startsWith("username:" + user.getUsername())) {
                    return true; // User was found
                }
            }
            fileScanner.close();
            
        } catch (FileNotFoundException ex) {
            // Handle the case where file is not found (should not happen)
            System.out.println("File not found: " + ex.getMessage());
        }
        
        return false; // User was not found
    }
    
    /**
     * Loads the data for a saved Sudoku board.
     * 
     * This method opens a file and retrieves the saved game state for a specific user,
     * returning it as a HashMap for easy access.
     * 
     * @param user The user that the saved board belong to.
     * @return A HashMap containing the saved board data, or an empty HashMap if no data is found.
     */
    public HashMap<String, String> loadSavedBoard(User user) {
        HashMap<String, String> boardData = new HashMap();
        
        try {
            // Open the file input stream and create a scanner to read the file
            FileInputStream fileInput = new FileInputStream(SAVED_FILE);
            Scanner fileScanner = new Scanner(fileInput);
            
            // Search each line for user
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                
                // Check if the line is not empty
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split(" ");
                    String[] subparts = parts[0].split(":");
                    if (subparts[1].equals(user.getUsername())) {
                        for (int i = 1; i < 7; i++) {
                            subparts = parts[i].split(":");
                            boardData.put(subparts[0].trim(), subparts[1].trim());
                        }
                    }
                }
            }
            fileScanner.close();
            
        } catch (FileNotFoundException ex) {
            // Handle the case where file is not found (should not happen)
            System.out.println("File not found: " + ex.getMessage());
        }
        
        return boardData;
    }
    
    /**
     * Saves the current games state for user to return to.
     * 
     * @param user The user whose game state is to be saved.
     * @param difficulty The difficulty level of the game.
     * @param elapsedTime The elapsed time in the game.
     * @param lives The number of lives remaining.
     * @param hints The number of hints remaining.
     * @param puzzleBoard The current state of the puzzle board.
     * @param completedBoard The completed solution board.
     */
    public void saveSudokuBoard(User user, int difficulty, long elapsedTime, int lives, int hints, Cell[][] puzzleBoard, Cell[][] completedBoard) {
        try (FileOutputStream fileOutput = new FileOutputStream(SAVED_FILE);
             PrintWriter printWriter = new PrintWriter(fileOutput)) {
            
            // Format the information
            printWriter.print("username:" + user.getUsername());
            printWriter.print(" difficulty:" + difficulty);
            printWriter.print(" elapsedTime:" + elapsedTime);
            printWriter.print(" lives:" + lives);
            printWriter.print(" hints:" + hints);
            
            printWriter.print(" puzzleBoard:");
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    printWriter.print(puzzleBoard[i][j].getValue());
                }
            }
            
            printWriter.print(" completedBoard:");
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    printWriter.print(completedBoard[i][j].getValue());
                }
            }
            printWriter.close();
            
        } catch (FileNotFoundException ex) {
            // Handle the case where file is not found (should not happen)
            System.out.println("File not found: " + ex.getMessage());
        } catch (IOException ex) {
            // Handle other I/O errors
            System.out.println("Unable to  data: " + ex.getMessage());
        }
    }
    
    /**
     * Removes the saved Sudoku board for a specific user.
     * 
     * This method finds the user's saved game data in the file and removes it.
     * 
     * @param user The user whose saved game will be removed. 
     */
    public void removeSudokuBoard(User user) {
        List<String> lines = new ArrayList();
        
        try {
            // Open the file input stream and create a scanner to read the file
            FileInputStream fileInput = new FileInputStream(SAVED_FILE);
            Scanner fileScanner = new Scanner(fileInput);
            
            // Flag for if user is found
            boolean isUser = false;
            
            // Search each line for user
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                
                if (line.startsWith("username:" + user.getUsername())) {
                    isUser = true;
                } 
                else if (isUser && line.trim().isEmpty()) {
                    isUser = false;
                    continue;
                }
                
                if (!isUser) {
                    lines.add(line);
                }
            }
            fileScanner.close();
            
        } catch (FileNotFoundException ex) {
            // Handle the case where file is not found (should not happen)
            System.out.println("File not found: " + ex.getMessage());
        }
        
        try (FileOutputStream fileOutput = new FileOutputStream(SAVED_FILE);
             PrintWriter printWriter = new PrintWriter(fileOutput)) {
            
            for (String line : lines) {
                printWriter.println(line);
            }
            printWriter.close();
            
        } catch (FileNotFoundException ex) {
            // Handle the case where file is not found (should not happen)
            System.out.println("File not found: " + ex.getMessage());
        } catch (IOException ex) {
            // Handle other I/O errors
            System.out.println("Unable to  data: " + ex.getMessage());
        }
    }
}
