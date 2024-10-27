/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sudoku;

/**
 * Timer class to measure elapsed time.
 * 
 * This class manages the measure of elapsed time. It provides methods to start, stop, 
 * pause, resume, and restart the timer. The class also includes a method to retrieve
 * the elapsed time in a "minute:seconds" format. 
 * 
 * @author paige
 */
public class Timer {

    private long startTime;       // A record of when the timer was started
    private long endTime;         // A record of when the timer was ended
    private long pausedStart;     // A record of when the timer was paused
    private long pausedEnd;       // A record of when the timer was resumed
    private long pausedTime = 0;  // A record of the overall amount of time the timer was paused
    private long elapsedTime = 0; // The overall amount of time elapsed
    private boolean running;      // A flag indicating if the timer is running
    private boolean paused;       // A flag indicating if the timer is paused

    /**
     * Starts the timer.
     * 
     * Throws an exception if the timer is already running to prevent multiple starts. 
     */
    public void start() {
        if (running) {
            throw new IllegalStateException("Timer is already running");
        }
        startTime = System.currentTimeMillis(); // Record start time
        running = true; // Mark the timer as running
    }

    /**
     * Stops the timer. 
     * 
     * Throws an exception if the timer is not running to prevent multiple stops. 
     */
    public void stop() {
        if (!running) {
            throw new IllegalStateException("Timer is not running");
        }
        endTime = System.currentTimeMillis(); // Record end time
        running = false; // Mark the timer as not running
    }
    
    /**
     * Pauses the timer.
     * 
     * Throws an exception if the timer is already paused to prevent multiple pauses. 
     */
    public void pause() {
        if (paused) {
            throw new IllegalStateException("Timer is already paused");
        }
        pausedStart = System.currentTimeMillis(); // Record pause start time
        paused = true; // Mark the timer as paused
    }
    
    /**
     * Resumes the timer by getting the time when the timer resumes.
     * 
     * Throws an exception if the timer is not paused to prevent multiple resumes. 
     */
    public void resume() {
        if (!paused) {
            throw new IllegalStateException("Timer is not paused");
        }
        pausedEnd = System.currentTimeMillis(); // Record pause end time
        pausedTime += (pausedEnd - pausedStart); // Record the overall time paused
        paused = false; // Mark the timer as unpaused
    }
    
    /**
    * Resets the timer to its initial state by clearing all time records and flags.
    */
    public void reset() {
        startTime = 0;
        endTime = 0;
        pausedStart = 0;
        pausedEnd = 0;
        pausedTime = 0;
        elapsedTime = 0;
        running = false;
        paused = false;
    }
    
    /**
     * Sets elapsed time for the timer.
     * 
     * Allows for elapsed time to be set manually, primarily when loading a 
     * previous game. 
     * 
     * @param elapsedTime The amount of time elapsed.
     */
    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
    
    /**
     * Returns the elapsed time as a formatted string in "minutes:seconds" format.
     * 
     * @return A string representing the elapsed time in "minutes:seconds" format.
     */
    public String getTime() {
        long elapsedSeconds = this.getMillis() / 1000; // Convert milliseconds to seconds
        long minutes = elapsedSeconds / 60; // Calculate minutes
        long seconds = elapsedSeconds % 60; // Calculate remaining seconds

        // Format and return time as string
        return String.format("%d:%02d", minutes, seconds);
    }
    
    /**
     * Retrieves the amount of milliseconds surpassed.
     * 
     * This method calculates the total time the timer has been running, accounting for
     * pauses and any manually set elapsed time.
     * 
     * @return The elapsed time in milliseconds.
     */
    public long getMillis() {
        long totalElapsed = 0;

        if (running) {
            totalElapsed = System.currentTimeMillis() - startTime;
        } else if (endTime > startTime) {
            totalElapsed = endTime - startTime;
        }

        return totalElapsed - pausedTime + elapsedTime;
    }

    /**
     * Checks if the timer is running. 
     * 
     * @return true if the timer is running; false otherwise.
     */
    public boolean isRunning() {
        return running;
    }
}
