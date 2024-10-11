/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Sudoku;

import java.util.HashMap;
import java.util.Scanner;

/**
 * The Main class of the Sudoku game. 
 * 
 * This class handles user interaction, the game menu, and user verification. 
 * 
 * @author paige
 */
public class Main {

    private Scanner scan;                // Scanner to read user input
    private FileHandler fileHandler;     // Handler for reading and writing user data to files
    private HashMap<String, User> users; // Map of users for storing and accessing user data
    
    /**
     * Constructor for initializes Main.
     */
    public Main() {
        this.scan = new Scanner(System.in);
        this.fileHandler = new FileHandler();
        this.users = fileHandler.readUserData();
    }
    
    /**
     * Main method to start the game.
     */
    public static void main(String[] args) {
        Main sudoku = new Main();
        System.out.println("Welcome! Play Sudoku!");
        User user = sudoku.login(); // Handles login or account creation
        controlTips(); // Displays game control tips
        sudoku.startMenu(user); // Starts the main menu after account is logged in
        SudokuGame sudokuGame = new SudokuGame(user); // Begins real game
    }
    
    /**
     * Displays control tips for the game, formatted in a box.
     */
    public static void controlTips() {
        String[] controls = {
            "Controls",
            " -> Type 'X' to exit the game from any point",
            " -> Type 'P' to pause the game",
            " -> Type 'H' to get a hint"
        };

        // Find the length of the longest string for the box width
        int maxLength = 0;
        for (String line : controls) {
            if (line.length() > maxLength) {
                maxLength = line.length();
            }
        }

        int boxWidth = maxLength + 4; // Adding padding for the box borders

        // Print the top border of the box
        System.out.print("\n    ");
        for (int i = 0; i < boxWidth; i++) {
            System.out.print("-");
        }
        System.out.println();

        // Print each line of controls within the box
        for (String line : controls) {
            System.out.println("    | " + line + " ".repeat(boxWidth - line.length() - 3) + "|");
        }

        // Print the bottom border of the box
        System.out.print("    ");
        for (int i = 0; i < boxWidth; i++) {
            System.out.print("-");
        }
        System.out.println();
    }
    
    /**
     * Handles user login and account creation.
     * 
     * If the user exists it allows users to login, otherwise users can create a new account.
     * 
     * @return The logged in User object. 
     */
    public User login() {
        User user = null;
        String password = " ";
        boolean loggedIn = false; // A flag indicating if user is logged in

        while(!loggedIn) {
            System.out.print("\nPlease enter your username: ");
            String username = scan.nextLine().trim();
            
            // Checks if username contains any spaces
            if (username.contains(" ") || username.isEmpty()) {
                System.out.println("Username cannot contain spaces or be empty.");
                continue;
            }
            
            // Check if user exists
            if (this.users.containsKey(username)) {
                user = this.users.get(username);

                // Prompt for password and verify
                System.out.print("Please enter your password: ");
                password = scan.nextLine().trim();

                while (!user.getPassword().equals(password)){
                    System.out.print("Incorrect, please try again: ");
                    password = scan.nextLine().trim();
                }

                // Successful login
                System.out.println("Login successful!");
                System.out.println("\nWelcome back " + user.getUsername());
                
                // Display all of users best times
                System.out.println("\nYour best times are: ");
                user.printAllTimes();
                
                loggedIn = true; // Account is logged in
            } 
            else {
                // Prompt for account creation
                System.out.println("No user exists, would you like to create and account?");
                System.out.println("  (1) Yes");
                System.out.println("  (2) No");
                int input = getValidInput(1, 2);
                if (input == 2) continue; // Restart loop if answer is no

                boolean validPassword = false;
                while (!validPassword) {
                    // Prompt for password
                    System.out.print("Please enter a password: ");
                    password = scan.nextLine().trim();

                    // Checks if password contains any spaces
                    if (password.contains(" ") || password.isEmpty()) {
                        System.out.println("Password cannot contain spaces or be empty.\n");
                        continue;
                    }
                    validPassword = true;
                }

                // Create new User object
                user = new User(username, password);
                this.users.put(username, user);
                fileHandler.writeUserData(users);
                
                // Successful account creation
                System.out.println("\nWelcome " + user.getUsername() + "!");
                
                loggedIn = true; // Account is logged in
            }
        }
        
        return user;
    }
    
    /**
     * Displays the main menu and handles game selection.
     * 
     * Allows for user to pick between tutorial, normal game, or quitting.
     * 
     * @param user The current logged in user.
     */
    private void startMenu(User user) {
        System.out.println("\nWhat would you like to do today?");
        System.out.println("  (1) Tutorial (recommended for new users)");
        System.out.println("  (2) A normal game of Sudoku");
        System.out.println("  (3) Quit game");
        int input = getValidInput(1, 3);
        
        if (input == 1) {
            // Run the tutorial if input is 1
            TutorialGame tutorialGame = new TutorialGame(user);
        }
        if (input == 3) {
            System.out.println("\nExiting Sudoku game. See you next time!");
            scan.close(); // Close scanner
            System.exit(0); // Exit program
        }
    }
    
    /**
     * Prompts the user for an integer input within a specified range and validates the input.
     * 
     * This method will continuously prompt the user until a valid integer input is provided or 
     * the user chooses to exit the input loop by entering 'X'.
     * 
     * @param lowerRange The lower bound of the valid input range (inclusive).
     * @param upperRange The upper bound of the valid input range (inclusive).
     * @return A valid integer input within the specified range.
     */
    private int getValidInput(int lowerRange, int upperRange) {
        final String EXIT_COMMAND = "X";

        while (true) {
            String input = scan.nextLine().trim();

            switch (input.toUpperCase()) {
                // Exits the game if 'X' is pressed
                case EXIT_COMMAND:
                    System.out.println("\nExiting Sudoku game. See you next time!");
                    scan.close(); // Close scanner
                    System.exit(0); // Exit program
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
}
