/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuGUI;

import Sudoku.Board;
import Sudoku.Game;
import Sudoku.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.event.DocumentListener;

public class GameGUI extends JPanel {
    
    private final Game game;
    private CardLayout cardLayout;
    private JFrame frame;
    
    private LivesGUI livesGUI;
    private HintGUI hintGUI;
    
    private JButton hintButton;
    
    private JLabel timerLabel; // Timer label
    private JTextField[][] cells = new JTextField[9][9]; // Cell text fields

    private PausePanelGUI pausePanel;
    
    private TimerGUI timerGUI;
    
    private boolean isPaused = false;
    
    private int currentRow = -1; // -1 indicates no cell is currently selected
    private int currentCol = -1;

    /**
     * Constructor for initializing the GUI for a game of Sudoku.
     * 
     * @param frame
     * @param cardLayout
     * @param user The user who is playing
     * @param difficulty The difficulty of the game
     */
    public GameGUI(JFrame frame, CardLayout cardLayout, User user, int difficulty) {
        this.cardLayout = cardLayout;
        this.game = new Game(user, difficulty);
        this.frame = frame;

        initialize(); 
    }

    public GameGUI(JFrame frame, CardLayout cardLayout, User user) {
        this.cardLayout = cardLayout;
        this.game = new Game(user);
        this.frame = frame;

        initialize();
    }

    /**
     * Initializes the GUI.
     */
    private void initialize() {

        // Ensure this code runs in the constructor or initialization block of GameGUI
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                game.exitGame(true);
                frame.dispose(); // Finally, close the window
            }
        });
        
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel titlePanel = initializeTitlePanel();
        JPanel bottomPanel = initializeBottomPanel();
        
        

        // Add the Sudoku grid panel
        JPanel gridPanel = createSudokuGrid();
        
        // Add components to the main panel
        add(titlePanel, BorderLayout.NORTH);
        add(gridPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        
        pausePanel = new PausePanelGUI(this::resumeGame);
    }
    
    private JPanel initializeTitlePanel() {
        

        createHintButton();
        
        // Create and set up the Quit Game button
        JButton quitButton = new JButton("Quit Game");
        quitButton.setBackground(Color.LIGHT_GRAY);
        quitButton.setForeground(Color.BLACK);
        quitButton.addActionListener(e -> quitGame());
        
        // Create and set up the Pause Game button
        JButton pauseButton = new JButton("Pause");
        pauseButton.setBackground(Color.LIGHT_GRAY);
        pauseButton.setForeground(Color.BLACK);
        pauseButton.addActionListener(e -> {
            if (isPaused) {
                resumeGame(); // Custom method to handle resuming
                pauseButton.setText("Pause"); // Change button text to "Pause"
            } else {
                pauseGame(); // Existing method to handle pausing
                pauseButton.setText("Resume"); // Change button text to "Resume"
            }
            isPaused = !isPaused; // Toggle the pause state
        });
        
        // Title Panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        
        titlePanel.add(quitButton, BorderLayout.WEST);
        titlePanel.add(pauseButton, BorderLayout.EAST);
        titlePanel.add(hintButton, BorderLayout.CENTER);
        
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        return titlePanel;
    }
    
    private void createHintButton() {
        hintButton = new JButton("USE HINT");
        hintButton.setBackground(Color.LIGHT_GRAY);
        hintButton.setForeground(Color.BLACK);
        hintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                provideHint();
            }
        });
    }
    
    private void provideHint() {
        int hintValue = game.useHint(currentRow, currentCol); // Assuming this method takes row and col as parameters
        if (hintValue != -1) { // Assuming -1 indicates no hint available
            cells[currentRow][currentCol].setText(String.valueOf(hintValue));
            // Optionally, you can show a dialog or update hintGUI with the hint
            hintGUI.updateHints(); // Update your hint GUI
            
            // Check if there are no hints left and hide the button
            if (game.getHintCount() == 0) {
                hintButton.setVisible(false); // Hide the hint button
            }
            
        } else {
            JOptionPane.showMessageDialog(this, "No hint available for the selected cell.");
        }
    }
    
    private JPanel initializeBottomPanel() {
        
        JPanel bottomPanel = new JPanel(new BorderLayout());

        this.timerGUI = new TimerGUI(game.getTimer());
        timerGUI.start();
        
        // Set up the timer label
        timerLabel = timerGUI.getTimeLabel();
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Lives Panel
        this.livesGUI = new LivesGUI(game);
        
        this.hintGUI = new HintGUI(game);
        
        bottomPanel.add(livesGUI, BorderLayout.WEST);
        bottomPanel.add(hintGUI, BorderLayout.EAST);
        bottomPanel.add(timerLabel, BorderLayout.CENTER);
        
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        return bottomPanel;
    }

    private JPanel createSudokuGrid() {
        JPanel gridPanel = new JPanel(new GridLayout(9, 9));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around the grid
        gridPanel.setBackground(Color.WHITE);

        // Add text fields for each cell in the Sudoku grid
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (game.getBoard().getCell(row, col).getValue() == 0) {
                    JTextField cell = new JTextField(1);
                    cell.setHorizontalAlignment(JTextField.CENTER);
                    applyCellStyling(cell, row, col);
                    gridPanel.add(cell);
                    cells[row][col] = cell; // Store the reference to the cell

                    // Action Listener to detect user input
                    int finalRow = row; // Required to use row inside lambda
                    int finalCol = col; // Required to use col inside lambda
                    
                     cell.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            currentRow = finalRow; // Set the current row when the cell is clicked
                            currentCol = finalCol; // Set the current column when the cell is clicked
                        }
                    });
                    
                    cell.getDocument().addDocumentListener((DocumentListener) new javax.swing.event.DocumentListener() {
                        @Override
                        public void insertUpdate(javax.swing.event.DocumentEvent e) {
                            checkInput(cell, finalRow, finalCol, game.getCompleteBoard());
                        }

                        @Override
                        public void removeUpdate(javax.swing.event.DocumentEvent e) {
                            checkInput(cell, finalRow, finalCol, game.getCompleteBoard());
                        }

                        @Override
                        public void changedUpdate(javax.swing.event.DocumentEvent e) {
                            checkInput(cell, finalRow, finalCol, game.getCompleteBoard());
                        }
                    });
                } else {
                    JLabel cellLabel = new JLabel(String.valueOf(game.getBoard().getCell(row, col).getValue()));
                    cellLabel.setHorizontalAlignment(JLabel.CENTER);
                    applyCellStyling(cellLabel, row, col);
                    gridPanel.add(cellLabel);
                }
            }
        }

        return gridPanel;
    }

    // Method to check the input and update the cell if correct
    private void checkInput(JTextField cell, int row, int col, Board completeBoard) {
        String text = cell.getText();
        if (!text.isEmpty()) {
            try {
                int value = Integer.parseInt(text);
                if (value == completeBoard.getCell(row, col).getValue()) {
                    // If the input is correct, make the cell non-editable
                    cell.setEditable(false);
                    cell.setForeground(Color.BLUE); // Optional: Change text color to indicate correctness
                    cell.setBackground(Color.WHITE);
                } else {
                    cell.setForeground(Color.RED); // Optional: Indicate incorrect input
                    cell.setBackground(Color.PINK);
                    
                    checkLives();
                }
            } catch (NumberFormatException ex) {
                // Handle non-numeric input
                cell.setForeground(Color.RED); // Optional: Indicate incorrect input
            }
        }
    }
    
    private void checkLives() {
        game.setLives(game.getLives() - 1);
        livesGUI.updateLives();
        
        if (game.getLives() == 0) {
            
            // Navigate back to the home page
            cardLayout.show(this.getParent(), "homePage");
        }
    }

    private void applyCellStyling(JComponent cell, int row, int col) {
        // Set font size and style
        Font font = new Font("Arial", Font.BOLD, 22); // Adjust font name, style, and size as needed
        cell.setFont(font);
        // Create borders for the cells
        int top = 1, left = 1, bottom = 1, right = 1;
        // Thicker border for 3x3 grid boundaries
        if (row % 3 == 0) top = 3;
        if (col % 3 == 0) left = 3;
        if (row == 8) bottom = 3; // Bottom border for the last row
        if (col == 8) right = 3;  // Right border for the last column
        cell.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));
    }

    private void pauseGame() {
        game.getTimer().pause(); // Pause the timer
        // Hide the grid panel instead of removing it
        for (Component comp : getComponents()) {
            if (comp instanceof JPanel && ((JPanel) comp).getLayout() instanceof GridLayout) {
                comp.setVisible(false); // Hide the grid panel
            }
        }
        add(pausePanel, BorderLayout.CENTER); // Add the pause panel
        revalidate(); // Refresh the layout
        repaint(); // Repaint the panel
    }

    private void resumeGame() {
        game.getTimer().resume(); // Resume the timer
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

    private void quitGame() {
        // Save the current game state
        game.exitGame(true); // Assuming this saves the game

        // Navigate back to the home page
        cardLayout.show(this.getParent(), "homePage");
    }

    /**
     * Checks if the board is complete and ends the game with a message if so.
     */
    public void checkGameCompletion() {
        if (game.getBoard().isComplete()) {
            game.endGame();
            
            JOptionPane.showMessageDialog(this, 
                "Congratulations! You completed the game!" + game.getTimer().getTime(), 
                "Game Complete", 
                JOptionPane.INFORMATION_MESSAGE);
            
            cardLayout.show(this.getParent(), "homePage");
        }
    }
}
