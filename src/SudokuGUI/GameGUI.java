/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuGUI;

import Sudoku.Board;
import Sudoku.FileHandler;
import Sudoku.Game;
import Sudoku.User;
import Sudoku.Timer;
import Sudoku.HintSystem;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * GameGUI class visualizing a game of Sudoku.
 * 
 * @author paige
 */
public class GameGUI extends JPanel {
    
    private JPanel gridPanel;
    private final Game game;
    private CardLayout cardLayout;
    private FileHandler fileHandler;     // Handler for reading and writing user data to files
    private HashMap<String, User> users; // Map of users for storing and accessing user data
    private JFrame frame;
    
    private LivesGUI livesGUI;
    private HintGUI hintGUI;
    
    /**
     * Constructor for initializing the GUI for a game of Sudoku.
     * 
     * @param user The user who is playing
     * @param difficulty The difficulty of the game
     */
    public GameGUI(JFrame frame, CardLayout cardLayout, User user, int difficulty) {
        this.cardLayout = cardLayout;
        this.game = new Game(user, difficulty);
        this.frame = frame;
        
        this.hintGUI = new HintGUI(game.getHint(), game);
        
        // Ensure this code runs in the constructor or initialization block of GameGUI
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Show confirmation dialog to save the game before quitting
                int response = JOptionPane.showConfirmDialog(frame,
                        "Do you want to save the game before quitting?", "Confirm Exit",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (response == JOptionPane.YES_OPTION) {
                    game.exitGame(true);
                }
                frame.dispose(); // Finally, close the window
            }
        });
        
        initialize(); 
    }
    
    public GameGUI(JFrame frame, CardLayout cardLayout, User user) {
        this.cardLayout = cardLayout;
        this.game = new Game(user);
        this.frame = frame;
        
        this.hintGUI = new HintGUI(game.getHint(), game);

        // Ensure this code runs in the constructor or initialization block of GameGUI
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Show confirmation dialog to save the game before quitting
                int response = JOptionPane.showConfirmDialog(frame,
                        "Do you want to save the game before quitting?", "Confirm Exit",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (response == JOptionPane.YES_OPTION) {
                    game.exitGame(true);
                }
                frame.dispose(); // Finally, close the window
            }
        });

        initialize();
    }
    
    /**
     * Initializes the GUI.
     */
    private void initialize() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Title Panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("PLAY SUDOKU.");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titlePanel.add(titleLabel);
        titlePanel.setBackground(Color.WHITE);
        
        // Lives Panel
        this.livesGUI = new LivesGUI(game);

        // Grid and number panels
        gridPanel = this.sudokuGrid();


        // Add components to the pain panel
        add(titlePanel, BorderLayout.NORTH);
        add(livesGUI, BorderLayout.SOUTH);
        add(gridPanel, BorderLayout.CENTER);
    }
    
    /**
     * Creates the game grid GUI with editable and static cells. 
     * 
     * @return 
     */
    private JPanel sudokuGrid() {
        JPanel gridPanel = new JPanel(new GridLayout(9, 9));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        gridPanel.setBackground(Color.WHITE);
        
        Board board = game.getBoard();
        Board completeBoard = game.getCompleteBoard();

        // Populate grid with cells based on the board state
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board.getCell(row, col).getValue() == 0) {
                    JTextField cell = createEditableCell(row, col, completeBoard);
                    gridPanel.add(cell);
                } else {
                    JLabel cellLabel = createStaticCell(board.getCell(row, col).getValue());
                    gridPanel.add(cellLabel);
                }
            }
        }
        
        return gridPanel;
    }
    
    private JTextField createEditableCell(int row, int col, Board completeBoard) {
        JTextField cell = new JTextField(1);
        cell.setHorizontalAlignment(JTextField.CENTER);
        applyCellStyling(cell, row, col);

        // Apply single-digit filter to limit input and replace existing input if a new digit is typed
        ((AbstractDocument) cell.getDocument()).setDocumentFilter(new SingleDigitFilter());

        // Add document listener to check the input and update the cell if correct
        cell.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkInput(cell, row, col, completeBoard);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkInput(cell, row, col, completeBoard);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkInput(cell, row, col, completeBoard);
            }
        });
        return cell;
    }
    
    private JLabel createStaticCell(int value) {
        JLabel cellLabel = new JLabel(String.valueOf(value));
        cellLabel.setHorizontalAlignment(JLabel.CENTER);
        applyCellStyling(cellLabel, 0, 0);
        return cellLabel;
    }

    // Method to check the input and update the cell if correct
    private void checkInput(JTextField cell, int row, int col, Board completeBoard) {
        String text = cell.getText();
        if (!text.isEmpty()) {
            try {
                int value = Integer.parseInt(text);
                if (value == completeBoard.getCell(row, col).getValue()) {
                    cell.setEditable(false);
                    cell.setForeground(Color.BLUE);
                    game.getBoard().updateCell(row, col, value);
                    this.checkGameCompletion();
                } else {
                    cell.setForeground(Color.RED);
                    this.checkLives();
                }
            } catch (NumberFormatException ex) {
                cell.setForeground(Color.RED);
            }
        }
    }

    private void applyCellStyling(JComponent cell, int row, int col) {
        Font font = new Font("Arial", Font.BOLD, 22);
        cell.setFont(font);

        int top = 1, left = 1, bottom = 1, right = 1;

        if (row % 3 == 0) top = 3;
        if (col % 3 == 0) left = 3;
        if (row == 8) bottom = 3;
        if (col == 8) right = 3;

        cell.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));
    }

    // Call updateLivesPanel in checkLives() after updating the lives
    private void checkLives() {
        game.setLives(game.getLives() - 1);
        livesGUI.updateLives(); // Update the lives panel

        if (game.getLives() == 0) {
            game.endGame();

            JOptionPane.showMessageDialog(this, 
                "Uh oh! You lost", 
                "Game Complete", 
                JOptionPane.INFORMATION_MESSAGE);

            cardLayout.show(this.getParent(), "homePage");
        }
}
    
    /**
     * Checks if the board is complete and ends the game with a message if so.
     */
    private void checkGameCompletion() {
        if (game.getBoard().isComplete()) {
            game.endGame();
            
            JOptionPane.showMessageDialog(this, 
                "Congratulations! You completed the game!" + game.getTimer().getTime(), 
                "Game Complete", 
                JOptionPane.INFORMATION_MESSAGE);
            
            cardLayout.show(this.getParent(), "homePage");
        }
    }

    // DocumentFilter to restrict cell input to a single digit
    class SingleDigitFilter extends DocumentFilter {
        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text.matches("\\d") && fb.getDocument().getLength() == 1) {
                fb.remove(0, 1); // Replace existing digit if present
            }
            if (text.matches("\\d") && fb.getDocument().getLength() == 0) {
                fb.insertString(0, text, attrs); // Allow single digit only
            }
        }
    }
}
