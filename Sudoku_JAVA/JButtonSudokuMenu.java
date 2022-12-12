package Sudoku_JAVA;

import java.awt.Font;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.SwingConstants;

public class JButtonSudokuMenu extends JButton {

    public JButtonSudokuMenu(String name) {
        super(name);
        super.setHorizontalAlignment(SwingConstants.CENTER);
        super.setFont(new Font("Papyrus", Font.BOLD, 15));
        super.setBackground(Color.decode(SudokuView.DARK_BG_COLOR));
        super.setForeground(Color.decode(SudokuView.TEXT_COLOR_LIGHT));
    }
}
