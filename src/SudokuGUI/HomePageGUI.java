/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuGUI;

import Sudoku.DBHandler;
import Sudoku.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Represents the home page of the Sudoku game.
 * 
 * It allows users to start a new game, continue a saved game, and log out.
 * 
 * @author paige
 */
public class HomePageGUI extends JPanel {

    
    private User user;                      // User object representing the currently logged-in user
    private DBHandler dbHandler;            // DBHandler instance for interacting with the database
    private CardLayout cardLayout;          // CardLayout manager for switching between different panels
    private JPanel cardPanel;               // JPanel that holds all the cards (pages) in the layout
    private JButton newGameButton;          // Button to start a new game
    private JButton continueGameButton;     // Button to continue a previously saved game
    private JFrame frame;                   // Main frame of the GUI
    private JPanel bestTimesPanel;          // JPanel to displya best times

    /**
     * Constructs a HomePageGUI instance.
     *
     * @param user The user whose information will be displayed on the home page.
     */
    public HomePageGUI(User user) {
        this.user = user;
        this.dbHandler = new DBHandler();
        
        setupFrame();  // Set up the main frame for the GUI
        cardPanel.add(this, "homePage");  // Add this panel to the card layout
        initializeHomePage();  // Initialize components of the home page
        updateContinueGameButtonVisibility();
    }

    /**
     * Sets up the main frame for the GUI.
     */
    private void setupFrame() {
        // Create and configure the main frame
        frame = new JFrame("PLAY SUDOKU.");
        cardLayout = new CardLayout();
        cardPanel = new JPanel();

        cardPanel.setLayout(cardLayout);  // Set the layout manager for card panel
        frame.add(cardPanel);  // Add card panel to the frame
        frame.pack();  // Pack the frame to preferred sizes
        frame.setSize(600, 700);  // Set initial size of the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Exit on close
        frame.setLocationRelativeTo(null);  // Center the frame on the screen
        frame.setVisible(true);  // Make the frame visible
    }

    /**
     * Initializes the components of the home page.
     */
    private void initializeHomePage() {
        // Set the layout and background for the home page
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Home Label
        JLabel homeLabel = createHomeLabel();
        add(homeLabel, BorderLayout.NORTH);

        // Best Times Panel
        createBestTimesPanel();
        add(bestTimesPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);

        // Update the "Continue Game" button's visibility
        updateContinueGameButtonVisibility();
    }

    /**
     * Creates the home label with styling.
     *
     * @return The JLabel representing the home label.
     */
    private JLabel createHomeLabel() {
        JLabel homeLabel = new JLabel("PLAY SUDOKU.", JLabel.CENTER);
        homeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        homeLabel.setBorder(new EmptyBorder(20, 10, 20, 10));
        return homeLabel;
    }

    /**
     * Creates a panel for buttons at the bottom of the home page.
     *
     * @return The JPanel containing the buttons.
     */
    private JPanel createButtonPanel() {
        // Create a panel for buttons at the bottom of the home page
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(Color.WHITE);

        // Logout Button
        JButton logoutButton = createButton("Log Out", "logout");
        buttonPanel.add(logoutButton);

        // New Game and Continue Game Buttons
        newGameButton = createButton("New Game", "newGame");
        continueGameButton = createButton("Continue Game", "continueGame");

        buttonPanel.add(newGameButton);
        buttonPanel.add(continueGameButton);

        return buttonPanel;
    }

    /**
     * Creates a button with the specified text and action.
     *
     * @param text  The text displayed on the button.
     * @param action The action identifier for the button.
     * @return The JButton created.
     */
    private JButton createButton(String text, String action) {
        // Create a button with the specified text and action
        JButton button = new JButton(text);
        button.setBackground(Color.LIGHT_GRAY);
        button.setForeground(Color.BLACK);
        button.addActionListener(e -> {
            switch (action) {
                case "newGame":
                    showDifficultySelection();
                    break;
                case "continueGame":
                    loadGame();
                    break;
                case "logout":
                    logout();
                    break;
            }
        });
        return button;
    }

    /**
     * Updates the visibility of the "Continue Game" button based on saved game presence.
     */
    private void updateContinueGameButtonVisibility() {
        // Show or hide the continue game button based on saved game presence
        continueGameButton.setVisible(dbHandler.hasSavedGame(user.getUserId()));
    }

    /**
     * Creates a panel displaying the best times for each difficulty.
     *
     * @return The JPanel containing the best times.
     */
    private void createBestTimesPanel() {
        // Create a panel displaying the best times for each difficulty
        bestTimesPanel = new JPanel();
        bestTimesPanel.setLayout(new BoxLayout(bestTimesPanel, BoxLayout.Y_AXIS));
        bestTimesPanel.setBackground(Color.WHITE);
        bestTimesPanel.setPreferredSize(new Dimension(200, 200));
        bestTimesPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Best Times"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        String[] bestTimes = user.getBestTimesAsString();
        String[] difficultyLabels = {"Beginner", "Easy", "Medium", "Hard", "Expert"};

        for (int i = 0; i < bestTimes.length; i++) {
            JLabel bestTimeLabel = new JLabel(difficultyLabels[i] + ": " + bestTimes[i]);
            bestTimeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            bestTimesPanel.add(bestTimeLabel);
        }
    }
    
    /**
    * Updates the best times displayed in the panel.
    */
    public void updateBestTimesPanel() {
        // Clear existing labels from the panel
        bestTimesPanel.removeAll();

        String[] bestTimes = user.getBestTimesAsString();
        String[] difficultyLabels = {"Beginner", "Easy", "Medium", "Hard", "Expert"};

        // Add the updated best time labels to the panel
        for (int i = 0; i < bestTimes.length; i++) {
            JLabel bestTimeLabel = new JLabel(difficultyLabels[i] + ": " + bestTimes[i]);
            bestTimeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            bestTimesPanel.add(bestTimeLabel);
        }

        // Revalidate and repaint the panel to reflect changes
        bestTimesPanel.revalidate();
        bestTimesPanel.repaint();
    }

    /**
     * Displays a dialog to select the game difficulty.
     */
    private void showDifficultySelection() {
        // Display a dialog to select the game difficulty
        DifficultyGUI difficultyGUI = new DifficultyGUI();
        
        int option = JOptionPane.showConfirmDialog(
                frame,
                difficultyGUI,
                "Select Difficulty",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option == JOptionPane.OK_OPTION) {
            int difficultyValue = difficultyGUI.getSelectedDifficultyValue();
            if (difficultyValue != -1) {
                startNewGame(difficultyValue);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a difficulty level.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Starts a new game with the selected difficulty.
     *
     * @param difficultyValue The value representing the selected difficulty level.
     */
    private void startNewGame(int difficultyValue) {
        // Start a new game with the selected difficulty
        GameGUI gamePage = new GameGUI(frame, cardLayout, user, difficultyValue, this);
        cardPanel.add(gamePage, "gamePage");
        cardLayout.show(cardPanel, "gamePage");
    }

    /**
     * Loads a saved game if available.
     */
    private void loadGame() {
        // Load a saved game if available
        if (dbHandler.hasSavedGame(user.getUserId())) {
            loadSavedGame();
        } else {
            JOptionPane.showMessageDialog(frame, "No saved game found.", "Load Game", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Loads the saved game.
     */
    private void loadSavedGame() {
        // Load the saved game
        GameGUI gamePage = new GameGUI(frame, cardLayout, user, this);
        cardPanel.add(gamePage, "gamePage");
        cardLayout.show(cardPanel, "gamePage");
    }

    /**
     * Logs out the user and returns to the main Sudoku GUI.
     */
    private void logout() {
        // Logout and return to the main Sudoku GUI
        frame.dispose();
        new SudokuGUI();
    }
    
    public void refreshData() {
        this.user = dbHandler.getUser(user.getUsername());
        updateContinueGameButtonVisibility();
        updateBestTimesPanel();
    }
}
