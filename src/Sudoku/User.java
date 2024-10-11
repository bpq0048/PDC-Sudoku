/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sudoku;

/**
 * Represents a user in the Sudoku game with attributes for username, password and best completion times.
 * 
 * This class provides methods to get and set user attributes, format the best completion
 * times and print them in a readable format. It also includes a method for converting User
 * information into a String and converting a User object back from a String.
 * 
 * @author paige
 */
public class User {
    
    private final String username;          // The username of the user
    private final String password;          // The password of the user
    private long[] bestTimes = new long[5]; // The best completion time of the user in milliseconds

    /**
     * Constructor to initialize a User object with a username and password.
     * 
     * Initializes all best times to 0, indicating that there are no recorded times.
     * 
     * @param username The username of the user.
     * @param password The password of the user.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        for (int i = 0; i < bestTimes.length; i++) {
            this.bestTimes[i] = 0;
        }
    }

    /**
     * Constructor to initialize a User object with a username, password, and best times.
     * 
     * @param username The username of the user.
     * @param password The password of the user.
     * @param bestTimes The best times of the user for each difficulty.
     */
    public User(String username, String password, long[] bestTimes) {
        this.username = username;
        this.password = password;
        this.bestTimes = bestTimes;
    }

    /**
     * Retrieves the users username.
     * 
     * @return The users username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Retrieves the users password.
     * 
     * @return The users password.
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Retrieves the best time for a specific difficulty level.
     * 
     * @param difficulty The difficulty level (1-5).
     * @return The best time in milliseconds for a specific difficulty level.
     */
    public long getBestTime(int difficulty) {
        return bestTimes[difficulty-1];
    }

    /**
     * Retrieves all of the best times for the user.
     * 
     * @return An array of all the bestTimes.
     */
    public long[] getAllTimes() {
        return bestTimes;
    }

    /**
     * Updates users best time for a given difficulty level.
     * 
     * @param bestTime The new best time of the user.
     * @param difficulty The difficulty level (1-5) the user is playing.
     */
    public void setBestTimes(long bestTime, int difficulty) {
        this.bestTimes[difficulty-1] = bestTime;
    }
    
    /**
     * Formats the best time into a "minutes:seconds" string.
     * 
     * @param difficulty The difficulty level (1-5) the user is playing.
     * @return a string representation of the best time in "minutes:seconds" format.
     */
    public String printBestTime(int difficulty) {
        long elapsedSeconds = bestTimes[difficulty] / 1000; // Convert milliseconds to seconds
        long minutes = elapsedSeconds / 60; // Calculate minutes
        long seconds = elapsedSeconds % 60; // Calculate remaining seconds

        // Format and return time as string
        return String.format("%d:%02d", minutes, seconds);
    }
    
    /**
     * Prints all of the best times.
     * 
     * If a best time is 0 (unrecorded), "N/A" is printed instead.
     */
    public void printAllTimes() {
        System.out.println("  Beginner: " + ((bestTimes[0] == 0) ? "N/A" : printBestTime(0)));
        System.out.println("  Easy: " + ((bestTimes[1] == 0) ? "N/A" : printBestTime(1)));
        System.out.println("  Medium: " + ((bestTimes[2] == 0) ? "N/A" : printBestTime(2)));
        System.out.println("  Hard: " + ((bestTimes[3] == 0) ? "N/A" : printBestTime(3)));
        System.out.println("  Expert: " + ((bestTimes[4] == 0) ? "N/A" : printBestTime(4)));
    }
    
    /**
     * Creates a User object from a string representation.
     * 
     * @param data A string containing the username, password and best time.
     * @return A User object with the parsed username, password and best time.
     */
    public static User fromString(String data) {
        String[] parts = data.split(" ");
        String username = parts[0];
        String password = parts[1];
        
        long bestTimes[] = new long[5];
        for (int i = 0; i < 5; i++) {
            bestTimes[i] = Long.parseLong(parts[(i+2)]);
        }
        
        return new User(username, password, bestTimes);
    }
    
    /**
     * Converts the User object to a String.
     * 
     * @return A String representation of the User object.
     */
    @Override
    public String toString() {
        String str = username + " " + password;
        for (int i = 0; i < 5; i++) {
            str += " " + bestTimes[i];
        }
        return str;
    }
}
