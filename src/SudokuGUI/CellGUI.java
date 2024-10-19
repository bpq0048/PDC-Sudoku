/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuGUI;

import Sudoku.Cell;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author paige
 */
public class CellGUI extends JPanel {
    
    private Cell cell; 
    
    public CellGUI(Cell cell) {
        this.cell = cell;
        
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        JTextField textField = new JTextField(1);
        textField.setHorizontalAlignment(JTextField.CENTER);
        add(textField, BorderLayout.CENTER);

        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle user input here (e.g., update cell value)
                if (cell.isEditable()) {
                    String input = textField.getText();
                    try {
                        int value = Integer.parseInt(input);
                        if (isValidValue(value)) {
                            cell.setValue(value);
                            textField.setText(String.valueOf(value));
                        } else {
                            // Handle invalid input (e.g., show error message)
                        }
                    } catch (NumberFormatException ex) {
                        // Handle non-numeric input
                    }
                }
            }
        });

        updateAppearance();
    }
    
    private void updateAppearance() {
        JTextField textField = (JTextField) getComponent(0);
        
        if (cell.getValue() != 0) {
            textField.setText(String.valueOf(cell.getValue()));
            textField.setEditable(false); // Make pre-filled cells non-editable
        } else {
            textField.setText("");
            textField.setEditable(true);
        }
    }
    
    private boolean isValidValue(int value) {
        return true;
    }
}
