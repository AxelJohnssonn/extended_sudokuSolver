package Sudoku_JAVA;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class SudokuView {
    public static boolean locked = false;  //if you want to lock user inputs on suduko
    public static final String BASE_COLOR = "#6f73d2";
    public static final String HIGHLIGHT_COLOR = "#9dacff";
    public static final String BORDER_COLOR = "#2A0154";
    public static final String TEXT_COLOR = "#1e1e1e";

    private int[][] board; //save the board to update from actionlistener

    public SudokuView(int[][] board) {
        this.board=board;
        SwingUtilities.invokeLater(() -> createWindow("Suduko", 1000, 350));
    }

    private void createWindow(String title, int width, int height) {

        //main frame
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(width, height));  //UNSURE IF NEEDED
        frame.setMaximumSize(new Dimension(width, height));  //UNSURE IF NEEDED
        frame.setMinimumSize(new Dimension(width, height));  //UNSURE IF NEEDED
        frame.setResizable(false);
        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());
        pane.setBackground(Color.darkGray); //Will be changed

        //left suduko holder panel
        JPanel inputSuduko = new JPanel();
        inputSuduko.setLayout(new GridLayout(0,1,0,2));  //stacks the flowlayout rows
        inputSuduko.setBackground(Color.decode(BORDER_COLOR));
        inputSuduko.setBorder(BorderFactory.createCompoundBorder(inputSuduko.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        pane.add(inputSuduko, BorderLayout.WEST); //input sudoko is left/west

        //right suduko holder panel
        JPanel outputSuduko = new JPanel();
        outputSuduko.setLayout(new GridLayout(0,1,0,2));  //stacks the flowlayout rows
        outputSuduko.setBackground(Color.decode(BORDER_COLOR));
        outputSuduko.setBorder(BorderFactory.createCompoundBorder(inputSuduko.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        pane.add(outputSuduko, BorderLayout.EAST); //input sudoko is left/west

        //create the suduko (left)
        Insets buttonMargin = new Insets(0,0,0,0); //remove standard margins
        JPanel[] leftSudukoRow = new JPanel[9]; //create the rows
        JPanel[] rightSudukoRow = new JPanel[9]; //create the rows
        Set<Integer> coloredEdge = new HashSet<Integer>(Arrays.asList(0,1,2,6,7,8));
        Set<Integer> coloredCenter = new HashSet<Integer>(Arrays.asList(3,4,5));

        for(int row = 0; row<board.length; row++){
            leftSudukoRow[row]= new JPanel(new FlowLayout(FlowLayout.LEFT,3,0));
            leftSudukoRow[row].setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
            leftSudukoRow[row].setBackground(Color.darkGray);
            inputSuduko.add(leftSudukoRow[row]);
            for (int col = 0; col<board.length; col++){
                JButton square = new JButton();
                square.setText(Integer.toString(board[row][col]));
                square.setMargin(buttonMargin);
                square.setForeground(Color.decode(TEXT_COLOR));
                square.setBorderPainted(false);
                square.setFocusPainted(false);
                square.setBorder(BorderFactory.createCompoundBorder(square.getBorder(), BorderFactory.createEmptyBorder(5, 10, 5, 10)));

                //color squares depending on if they are in the set
                if (coloredCenter.contains(row)&&coloredCenter.contains(col)||coloredEdge.contains(row)&&coloredEdge.contains(col)) {
                    square.setBackground(Color.decode(HIGHLIGHT_COLOR));
                } else {
                    square.setBackground(Color.decode(BASE_COLOR));
                }

                leftSudukoRow[row].add(square);

                //toggle number up/down
                square.addActionListener((o) -> {
                    if (locked) return;
                    try {
                        int nbr = Integer.parseInt(square.getText());
                        if ( nbr >= 9) {
                            square.setText("0");
                            square.setForeground(square.getBackground());
                        } else {
                            square.setText(nbr+1+"");
                            square.setForeground(Color.decode(TEXT_COLOR));
                        }
                        
                    } catch (Exception e) {
                        square.setText("0");
                        square.setForeground(square.getBackground());
                    }
                    
                }); 
            }
        }

        for(int row = 0; row<board.length; row++){
            rightSudukoRow[row]= new JPanel(new FlowLayout(FlowLayout.LEFT,3,0));
            rightSudukoRow[row].setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
            rightSudukoRow[row].setBackground(Color.darkGray);
            outputSuduko.add(rightSudukoRow[row]);
            for (int col = 0; col<board.length; col++){
                JButton square = new JButton();
                square.setText(Integer.toString(board[row][col]));
                square.setMargin(buttonMargin);
                square.setForeground(Color.decode(TEXT_COLOR));
                square.setBorderPainted(false);
                square.setFocusPainted(false);
                square.setBorder(BorderFactory.createCompoundBorder(square.getBorder(), BorderFactory.createEmptyBorder(5, 10, 5, 10)));

                //color squares depending on if they are in the set
                if (coloredCenter.contains(row)&&coloredCenter.contains(col)||coloredEdge.contains(row)&&coloredEdge.contains(col)) {
                    square.setBackground(Color.decode(HIGHLIGHT_COLOR));
                } else {
                    square.setBackground(Color.decode(BASE_COLOR));
                }

                rightSudukoRow[row].add(square);
            }
        }

        //center menu panel holder
        JPanel menurow = new JPanel(new GridLayout(0,1));
        menurow.setBackground(Color.decode(TEXT_COLOR));
        menurow.setPreferredSize(new Dimension(200,200));
        menurow.setMinimumSize(new Dimension(200,200));
        menurow.setMaximumSize(new Dimension(200,200));
        pane.add(menurow);

        JLabel titleText = new JLabel("SUDUKO SOLVER");
        titleText.setFont(new Font("Serif", Font.PLAIN, 28));
        titleText.setHorizontalAlignment(SwingConstants.CENTER);
        titleText.setForeground(Color.decode("#e2e2e2"));
        menurow.add(titleText);

        JLabel titleText2 = new JLabel("- PREMIUM EDITION -");
        titleText2.setFont(new Font("Serif", Font.PLAIN, 16));
        titleText2.setHorizontalAlignment(SwingConstants.CENTER);
        titleText2.setForeground(Color.decode("#e2e2e2"));
        menurow.add(titleText2);

        JLabel titleText3 = new JLabel("- PREMIUM EDITION -");
        titleText3.setFont(new Font("Serif", Font.PLAIN, 16));
        titleText3.setHorizontalAlignment(SwingConstants.CENTER);
        titleText3.setForeground(Color.decode(TEXT_COLOR));
        menurow.add(titleText3);

        JButton loadButton = new JButton("LOAD");
        loadButton.setHorizontalAlignment(SwingConstants.CENTER);
        menurow.add(loadButton);

        JButton solveButton = new JButton("SOLVE");
        solveButton.setHorizontalAlignment(SwingConstants.CENTER);
        menurow.add(solveButton);

        JButton clearButton = new JButton("CLEAR");
        clearButton.setHorizontalAlignment(SwingConstants.CENTER);
        menurow.add(clearButton);
        

        

        //Default button setup
        //JRootPane jf = frame.getRootPane();
        //jf.setDefaultButton(searchbtn);

        //create window
        frame.pack();
        frame.setVisible(true);
    }
}