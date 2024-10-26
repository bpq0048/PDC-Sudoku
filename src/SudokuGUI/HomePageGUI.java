/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuGUI;

import Sudoku.Board;
import Sudoku.CompleteBoard;
import Sudoku.FileHandler;
import Sudoku.Timer;
import Sudoku.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;

public class HomePageGUI extends JPanel {
    
    private User user;
    private DifficultyGUI difficultyGUI; // Use the updated DifficultyGUI class
    private JButton newGameButton;
    private JButton continueGameButton;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JFrame frame;
    private FileHandler fileHandler;

    public HomePageGUI(User user) {
        this.user = user;
        this.fileHandler = new FileHandler();

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

        JLabel homeLabel = new JLabel("PLAY SUDOKU.", JLabel.CENTER);
        homeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        homeLabel.setBorder(new EmptyBorder(20, 10, 20, 10));
        add(homeLabel, BorderLayout.NORTH);

        difficultyGUI = new DifficultyGUI(); // Instantiate the updated DifficultyGUI
        add(difficultyGUI, BorderLayout.CENTER);

        newGameButton = new JButton("New Game");
        newGameButton.setFont(new Font("Arial", Font.BOLD, 20));
        continueGameButton = new JButton("Continue Game");
        continueGameButton.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(newGameButton);
        buttonPanel.add(continueGameButton);
        add(buttonPanel, BorderLayout.SOUTH);

        newGameButton.addActionListener(e -> startNewGame());
        continueGameButton.addActionListener(e -> loadGame());

        // Check for a saved game and set the continueGameButton visibility
        if (fileHandler.checkForSavedBoard(user)) {
            continueGameButton.setVisible(true); // Show button if a saved game exists
        } else {
            continueGameButton.setVisible(false); // Hide if no saved game
        }
    }

    private void startNewGame() {
        int difficultyValue = difficultyGUI.getSelectedDifficultyValue();
        if (difficultyValue != -1) {
            GameGUI gamePage = new GameGUI(frame, cardLayout, user, difficultyValue);
            cardPanel.add(gamePage, "gamePage");
            cardLayout.show(cardPanel, "gamePage");
        }
    }
    
    private void loadGame() {
        // Checks if user has a saved game
        if (!fileHandler.checkForSavedBoard(user)) {
            continueGameButton.setVisible(false); // Hide button if no saved game
            return; // No saved game found
        }

        // Load saved data in a HashMap
        HashMap<String, String> boardData = fileHandler.loadSavedBoard(user);

        // Initialize saved data
        int savedDifficulty = Integer.parseInt(boardData.get("difficulty"));
        int savedLives = Integer.parseInt(boardData.get("lives"));
        Timer savedTimer = new Timer();
        savedTimer.setElapsedTime(Long.parseLong(boardData.get("elapsedTime")));
        int savedHints = Integer.parseInt(boardData.get("hints"));
        
        
        Board board = new Board();
        board.loadBoard(boardData.get("puzzleBoard"));
        
        Board completeBoard = new CompleteBoard();
        completeBoard.loadBoard(boardData.get("completedBoard"));

        // Show the continue game button since a saved game exists
        continueGameButton.setVisible(true);

        // Example dialog to confirm loading the game
        int choice = JOptionPane.showConfirmDialog(frame, "You have a saved game! Do you want to continue?", "Load Game", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
        // Load the game with the saved data
            GameGUI gamePage = new GameGUI(frame, cardLayout, user);
            cardPanel.add(gamePage, "gamePage");
            cardLayout.show(cardPanel, "gamePage");
        } else {
            // Optionally handle "No" choice
        }
    }
}
