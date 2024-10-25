/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuGUI;

import Sudoku.Board;
import Sudoku.PuzzleGenerator;
import Sudoku.User;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class GameGUI extends JPanel {
    
    private JPanel gridPanel;
    private Board completeBoard;
    private Board board;
    
    public GameGUI(User user) {
        initialize();
    }
    
    private void initialize() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("PLAY SUDOKU.");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titlePanel.add(titleLabel);
        titlePanel.setBackground(Color.WHITE);

        gridPanel = this.sudokuGrid();

        JPanel numberPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        numberPanel.setBackground(Color.WHITE);
        for (int i = 1; i <= 9; i++) {
            JLabel numberLabel = new JLabel(String.valueOf(i));
            numberLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            numberPanel.add(numberLabel);
        }

        add(titlePanel, BorderLayout.NORTH);
        add(gridPanel, BorderLayout.CENTER);
        add(numberPanel, BorderLayout.SOUTH);
    }
    
    private JPanel sudokuGrid() {
        JPanel gridPanel = new JPanel(new GridLayout(9, 9));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        gridPanel.setBackground(Color.WHITE);
        
        PuzzleGenerator puzzleGenerator = new PuzzleGenerator(2);
        completeBoard = puzzleGenerator.getCompleteBoard();
        board = puzzleGenerator.getPuzzleBoard();

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board.getCell(row, col).getValue() == 0) {
                    JTextField cell = new JTextField(1);
                    cell.setHorizontalAlignment(JTextField.CENTER);
                    this.applyCellStyling(cell, row, col);
                    gridPanel.add(cell);
                    
                    int finalRow = row;
                    int finalCol = col;
                    
                    cell.getDocument().addDocumentListener(new DocumentListener() {
                        @Override
                        public void insertUpdate(DocumentEvent e) {
                            checkInput(cell, finalRow, finalCol, completeBoard);
                        }

                        @Override
                        public void removeUpdate(DocumentEvent e) {
                            checkInput(cell, finalRow, finalCol, completeBoard);
                        }

                        @Override
                        public void changedUpdate(DocumentEvent e) {
                            checkInput(cell, finalRow, finalCol, completeBoard);
                        }
                    });
                } else {
                    JLabel cellLabel = new JLabel(String.valueOf(board.getCell(row, col).getValue()));
                    cellLabel.setHorizontalAlignment(JLabel.CENTER);
                    this.applyCellStyling(cellLabel, row, col);
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
                    cell.setEditable(false);
                    cell.setForeground(Color.GREEN);
                } else {
                    cell.setForeground(Color.RED);
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
}
