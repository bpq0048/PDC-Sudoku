/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuGUI;

import Sudoku.Game;
import Sudoku.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameGUI extends JPanel {
    
    private final Game game;                // Instance of the Game model, manages the game logic
    private final CardLayout cardLayout;    // Layout manager for switching between different views (cards)
    private final JFrame frame;             // Main window frame for the GUI
    private GridGUI sudokuGrid;             // Custom component for displaying the Sudoku grid
    private BottomPanel bottomPanel;        // Panel for buttons and controls
    private TitlePanel titlePanel;          // Panel for the game title and control buttons
    private boolean isPaused = false;       // Flag to track if the game is paused
    private JPanel pausePanel;              // Panel displayed when the game is paused
    private JButton hintButton;             // Button for providing hints to the player
    private HomePageGUI homePage;           // Reference to the home page
    
    /**
     * Constructor for initializing GameGUI with difficulty.
     *
     * @param frame The main frame for the GUI.
     * @param cardLayout The CardLayout for switching between panels.
     * @param user The user playing the game.
     * @param difficulty The difficulty level of the game.
     */
    public GameGUI(JFrame frame, CardLayout cardLayout, User user, int difficulty, HomePageGUI homePage) {
        this.cardLayout = cardLayout;
        this.game = new Game(user, difficulty);
        this.frame = frame;
        this.homePage = homePage;
        initialize();
    }

    /**
     * Constructor for initializing GameGUI without specific difficulty.
     *
     * @param frame The main frame for the GUI.
     * @param cardLayout The CardLayout for switching between panels.
     * @param user The user playing the game.
     */
    public GameGUI(JFrame frame, CardLayout cardLayout, User user, HomePageGUI homePage) {
        this.cardLayout = cardLayout;
        this.game = new Game(user);
        this.frame = frame;
        this.homePage = homePage;
        initialize();
    }

    /**
     * Initializes the GameGUI components and layout.
     */
    private void initialize() {
        // Set up frame close operation
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                game.exitGame(true);
                frame.dispose(); // Close the frame
            }
        });

        // Set layout and background color
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Initialize panels
        titlePanel = new TitlePanel(e -> quitGame(), e -> togglePause(), e -> provideHint());
        sudokuGrid = new GridGUI(game);
        bottomPanel = new BottomPanel(game);
        hintButton = titlePanel.getHintButton(); 

        // Add panels to the main panel
        add(titlePanel, BorderLayout.NORTH);
        add(sudokuGrid, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Toggles the pause state of the game.
     */
    private void togglePause() {
        if (isPaused) {
            resumeGame();
            titlePanel.getPauseButton().setText("Pause");
        } else {
            pauseGame();
            titlePanel.getPauseButton().setText("Resume");
        }
        isPaused = !isPaused;
    }

    /**
     * Pauses the game and displays a pause panel.
     */
    private void pauseGame() {
        game.getTimer().pause(); // Pause the timer
        bottomPanel.pauseTimer();
        
        // Hide the grid panel instead of removing it
        for (Component comp : getComponents()) {
            if (comp instanceof JPanel && ((JPanel) comp).getLayout() instanceof GridLayout) {
                comp.setVisible(false); // Hide the grid panel
            }
        }
        
        pausePanel = new JPanel();
        
        pausePanel.setBackground(Color.WHITE);
        pausePanel.setLayout(new GridBagLayout());

        JLabel pauseLabel = new JLabel("Game Paused");
        pauseLabel.setFont(new Font("Arial", Font.BOLD, 30));
        pausePanel.add(pauseLabel);
        
        add(pausePanel, BorderLayout.CENTER); // Add the pause panel
        revalidate(); // Refresh the layout
        repaint(); // Repaint the panel
    }

    /**
     * Resumes the game and hides the pause panel.
     */
    private void resumeGame() {
        game.getTimer().resume(); // Resume the timer
        bottomPanel.resumeTimer();
        
        // Show the grid panel again instead of recreating it
        for (Component comp : getComponents()) {
            if (comp instanceof JPanel && ((JPanel) comp).getLayout() instanceof GridLayout) {
                comp.setVisible(true); // Show the grid panel
            }
        }
        
        remove(pausePanel); // Remove the pause panel
        revalidate(); // Refresh the layout
        repaint(); // Repaint the panel
    }

    /**
     * Quits the current game and navigates back to the home page.
     */
    private void quitGame() {
        // Save the current game state
        game.exitGame(true); // Assuming this saves the game

        // Navigate back to the home page
        cardLayout.show(this.getParent(), "homePage");
        homePage.refreshData();  // Refresh data on the homepage
    }
    
    /**
     * Provides a hint to the user if available.
     */
    private void provideHint() {
        if (game.getHintCount() != 0) {
            sudokuGrid.useHint();
            bottomPanel.updateHintCount();
            
            // Check if there are no hints left and hide the button
            if (game.getHintCount() == 0) {
                hintButton.setVisible(false); // Hide the hint button
            }
            
        } else {
            JOptionPane.showMessageDialog(this, "No hint available for the selected cell.");
        }
    }
    
    /**
     * Checks and updates the number of lives remaining for the user.
     */
    public void checkLives() {
        game.setLives(game.getLives() - 1);
        bottomPanel.updateLivesCount();
        
        if (game.getLives() == 0) {
            // Show popup message
            JOptionPane.showMessageDialog(this, "Uh oh! It seems you've lost.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            game.lostGame();
            
            homePage.refreshData();  // Refresh data on the homepage
            
            // Navigate back to the home page
            cardLayout.show(this.getParent(), "homePage");
        }
    }

    /**
     * Checks if the game board is complete and ends the game if so.
     */
    public void checkGameCompletion() {
        if (game.getBoard().isComplete()) {
            game.endGame();
            
            JOptionPane.showMessageDialog(this, 
                "Congratulations! You completed the game!" + game.getTimer().getTime(), 
                "Game Complete", 
                JOptionPane.INFORMATION_MESSAGE);
            
            homePage.refreshData();  // Refresh data on the homepage
            cardLayout.show(this.getParent(), "homePage");
        }
    }
}
