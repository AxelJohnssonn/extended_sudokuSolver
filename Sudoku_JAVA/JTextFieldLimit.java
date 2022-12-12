package Sudoku_JAVA;

import java.awt.Font;
import java.awt.Color;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
public class JTextFieldLimit extends JTextField {
    private int limit;


    public JTextFieldLimit() {
        super();
        this.limit = 1;
        this.setFont(new Font("Verdana", Font.PLAIN, 18));
        this.setMargin(new Insets(0, 0, 0, 0));
        this.setForeground(Color.decode(SudokuView.TEXT_COLOR_DARK));
        this.setBorder(BorderFactory.createCompoundBorder(this.getBorder(), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
    }
    
    @Override
    protected Document createDefaultModel() {
        return new LimitDocument();
    }
    
    

    private class LimitDocument extends PlainDocument {

        @Override
        public void insertString(int offset, String  str, AttributeSet attr) throws BadLocationException {
            if (str == null) return;

            if ((getLength() + str.length()) <= limit && str.matches("[1-9]+")) {
                super.insertString(offset, str, attr);
            }
        }       

    }

}