/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuGUI;

import Sudoku.Board;
import Sudoku.CompleteBoard;
import Sudoku.DBHandler;
import Sudoku.Game;
import Sudoku.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;

public class HomePageGUI extends JPanel {

    private User user;
    private DifficultyGUI difficultyGUI;
    private JButton newGameButton;
    private JButton continueGameButton;
    private JButton logoutButton;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JFrame frame;
    private DBHandler dbHandler;
    

    public HomePageGUI(User user) {
        this.user = user;
        this.dbHandler = new DBHandler();
        initializeFrame();
    }

    private void initializeFrame() {
        this.frame = new JFrame("PLAY SUDOKU.");
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
        cardPanel.add(this, "homePage");

        initializeHomePageComponents();

        frame.add(cardPanel);
        frame.pack();
        frame.setSize(600, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        cardLayout.show(cardPanel, "homePage");
    }

    private void initializeHomePageComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel homeLabel = createHomeLabel();
        add(homeLabel, BorderLayout.NORTH);

        difficultyGUI = new DifficultyGUI();
        add(difficultyGUI, BorderLayout.CENTER);

        createButtons();
        add(createBestTimesPanel(), BorderLayout.CENTER); // Center the best times panel
    }

    private JLabel createHomeLabel() {
        JLabel homeLabel = new JLabel("PLAY SUDOKU.", JLabel.CENTER);
        homeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        homeLabel.setBorder(new EmptyBorder(20, 10, 20, 10));
        return homeLabel;
    }

    private void createButtons() {
        logoutButton = createButton("Log Out", "logout", frame); // Create the logout button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Align buttons to the left
        buttonPanel.setBackground(Color.WHITE); // Set background for the button panel
        buttonPanel.add(logoutButton); // Add logout button to the panel
        
        // Create the new game and continue game buttons
        newGameButton = createButton("New Game", "newGame", frame);
        continueGameButton = createButton("Continue Game", "continueGame", frame);
        
        // Add the buttons to the button panel
        buttonPanel.add(newGameButton);
        buttonPanel.add(continueGameButton);
        add(buttonPanel, BorderLayout.SOUTH);

        newGameButton.addActionListener(e -> startNewGame());
        continueGameButton.addActionListener(e -> loadGame());

        updateContinueGameButtonVisibility();
    }

    private JButton createButton(String text, String action, JFrame frame) {
        JButton button = new JButton(text);
        button.setBackground(Color.LIGHT_GRAY);
        button.setForeground(Color.BLACK);
        button.addActionListener(e -> {
            if ("newGame".equals(action)) {
                startNewGame();
            } else if ("continueGame".equals(action)) {
                loadGame();
            } else if ("logout".equals(action)) {
                logout();
            }
        });
        return button;
    }
    
    private JPanel createBestTimesPanel() {
        JPanel bestTimesPanel = new JPanel();
        bestTimesPanel.setLayout(new BoxLayout(bestTimesPanel, BoxLayout.Y_AXIS));
        bestTimesPanel.setBorder(BorderFactory.createTitledBorder("Best Times"));
        bestTimesPanel.setBackground(Color.WHITE); // Set background color

        // Set preferred size (width, height) - adjust height as needed
        bestTimesPanel.setPreferredSize(new Dimension(200, 200)); // Set height to 200 pixels

        // Add empty border around the panel
        bestTimesPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Best Times"), // Title border
                BorderFactory.createEmptyBorder(10, 10, 10, 10) // Empty border: top, left, bottom, right
        ));

        String[] bestTimes = user.getBestTimesAsString();
        String[] difficultyLabels = {"Beginner", "Easy", "Medium", "Hard", "Expert"};

        for (int i = 0; i < bestTimes.length; i++) {
            JLabel bestTimeLabel = new JLabel(difficultyLabels[i] + ": " + bestTimes[i]);
            bestTimeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            bestTimesPanel.add(bestTimeLabel);
        }
        return bestTimesPanel;
    }

    private void updateContinueGameButtonVisibility() {
        continueGameButton.setVisible(dbHandler.hasSavedGame(user.getUserId()));
    }

    private void startNewGame() {
        DifficultyGUI difficultyGUI = new DifficultyGUI(); // Create a new instance of DifficultyGUI

        // Show the DifficultyGUI in a dialog
        int option = JOptionPane.showConfirmDialog(frame, difficultyGUI, 
                "Select Difficulty", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // If the user clicks OK, retrieve the selected difficulty value
        if (option == JOptionPane.OK_OPTION) {
            int difficultyValue = difficultyGUI.getSelectedDifficultyValue();
            if (difficultyValue != -1) {
                GameGUI gamePage = new GameGUI(frame, cardLayout, user, difficultyValue);
                cardPanel.add(gamePage, "gamePage");
                cardLayout.show(cardPanel, "gamePage");
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a difficulty level.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadGame() {
        HashMap<String, String> boardData = dbHandler.getSavedGame(user.getUserId());

        if (boardData != null) {
            loadSavedGame(boardData);
        } else {
            JOptionPane.showMessageDialog(frame, "No saved game found.", "Load Game", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void loadSavedGame(HashMap<String, String> boardData) {
        Board board = new Board();
        board.loadBoard(boardData.get("puzzleBoard"));
        
        Board completeBoard = new CompleteBoard();
        completeBoard.loadBoard(boardData.get("completedBoard"));

        GameGUI gamePage = new GameGUI(frame, cardLayout, user);
        cardPanel.add(gamePage, "gamePage");
        cardLayout.show(cardPanel, "gamePage");
    }

    private void logout() {
        frame.dispose(); // Close the current frame
        new SudokuGUI(); // Reopen SudokuGUI
    }
}
