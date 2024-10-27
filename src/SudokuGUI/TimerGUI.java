/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuGUI;

import Sudoku.Timer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class responsible for displaying the game timer. 
 * 
 * @author paige
 */
public class TimerGUI {
    
    private final Timer timer;              // Reference to your Timer class
    private final JLabel timeLabel;         // Label to display elapsed time
    private javax.swing.Timer gameTimer;    // Swing Timer for GUI updates

    /**
     * Constructor for timer GUI.
     * 
     * @param timer The timer instance used for tracking elapsed time. 
     */
    public TimerGUI(Timer timer) {
        this.timer = timer;
        this.timeLabel = new JLabel("0:00");
        
        setupTimer(); // Set up the Swing Timer
    }

    /**
     * Sets up the Swing Timer to trigger every second.
     */
    private void setupTimer() {
        // Create a Swing timer to update every second (1000 milliseconds)
        gameTimer = new javax.swing.Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // If the timer is running, update the displayed time
                if (timer.isRunning()) {
                    updateElapsedTime();
                }
            }
        });
    }

    /**
     * Updates the time label with the formatted elapsed time.
     */
    private void updateElapsedTime() {
        String formattedTime = timer.getTime();
        timeLabel.setText(formattedTime);
    }

    /**
     * Returns the JLabel displaying the elapsed time. 
     * 
     * @return JLabel representing the elapsed time.
     */
    public JLabel getTimeLabel() {
        return timeLabel;
    }

    /** 
     * Starts the Swing Timer for updating time display.
     */
    public void start() {
        gameTimer.start();
    }

    /**
     * Stops the Swing Timer for updating the time display.
     */
    public void stop() {
        gameTimer.stop(); 
    }
}
