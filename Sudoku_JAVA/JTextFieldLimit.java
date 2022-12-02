package Sudoku_JAVA;

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