/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuGUI;

import javax.swing.*;
import java.awt.*;

/**
 * Displays a pause message.
 *
 * @author paige
 */
public class PausePanelGUI extends JPanel {
    
    private JButton resumeButton;

    public PausePanelGUI(Runnable onResume) {
        setBackground(Color.WHITE);
        setLayout(new GridBagLayout());

        JLabel pauseLabel = new JLabel("Game Paused");
        pauseLabel.setFont(new Font("Arial", Font.BOLD, 30));
        add(pauseLabel);
    }
}
