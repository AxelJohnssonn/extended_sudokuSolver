package Sudoku_JAVA;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
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
import javax.swing.filechooser.FileSystemView;

public class SudokuView {
    public static boolean locked = false;  //if you want to lock user inputs on suduko
    public static final String BASE_COLOR = "#6f73d2";
    public static final String HIGHLIGHT_COLOR = "#9dacff";
    public static final String BORDER_COLOR = "#2A0154";
    public static final String DARK_BG_COLOR = "#1e1e1e";
    public static final String TEXT_COLOR = DARK_BG_COLOR;
    public static final String TEXT_COLOR_LIGHT = "#e2e2e2";

    private JButton[][] rightButtons = new JButton[9][9];
    private JFrame frame;

    private Sudoku inputBoard; //save the board to update from actionlistener
    private Sudoku outputBoard;

    public SudokuView(Sudoku board) {
        inputBoard=board;
        outputBoard = new Sudoku();
        outputBoard.init(inputBoard.getBoard());
        SwingUtilities.invokeLater(() -> createWindow("Suduko", 1000, 350));
    }

    private void createWindow(String title, int width, int height) {
        //main frame
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(width, height));  //UNSURE IF NEEDED
        frame.setMaximumSize(new Dimension(width, height));  //UNSURE IF NEEDED
        frame.setMinimumSize(new Dimension(width, height));  //UNSURE IF NEEDED
        frame.setResizable(false);
        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());
        pane.setBackground(Color.darkGray); //Will be changed

        //top row
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.setBackground(Color.decode(TEXT_COLOR));
        pane.add(topPanel, BorderLayout.NORTH);
        
        

        JLabel panelText1 = new JLabel("- Input -");
        panelText1.setFont(new Font("Serif", Font.PLAIN, 16));
        panelText1.setHorizontalAlignment(SwingConstants.LEFT);
        panelText1.setForeground(Color.decode(TEXT_COLOR_LIGHT));
        topPanel.add(panelText1);
        
        JLabel panelText2 = new JLabel("");
        panelText2.setFont(new Font("Serif", Font.PLAIN, 16));
        panelText2.setHorizontalAlignment(SwingConstants.CENTER);
        panelText2.setForeground(Color.decode(TEXT_COLOR_LIGHT));
        panelText2.setBorder(BorderFactory.createCompoundBorder(panelText2.getBorder(), BorderFactory.createEmptyBorder(0, 270, 0, 305)));
        topPanel.add(panelText2);

        JLabel panelText3 = new JLabel("- Output -");
        panelText3.setFont(new Font("Serif", Font.PLAIN, 16));
        panelText3.setHorizontalAlignment(SwingConstants.RIGHT);
        panelText3.setForeground(Color.decode(TEXT_COLOR_LIGHT));
        topPanel.add(panelText3);


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

        //the suduko variables
        Insets buttonMargin = new Insets(0,0,0,0); //remove standard margins
        JPanel[] leftSudukoRow = new JPanel[9]; //create the rows
        JPanel[] rightSudukoRow = new JPanel[9]; //create the rows
        Set<Integer> coloredEdge = new HashSet<Integer>(Arrays.asList(0,1,2,6,7,8));
        Set<Integer> coloredCenter = new HashSet<Integer>(Arrays.asList(3,4,5));

        int[][] inputBoardNumber = inputBoard.getBoard();

        for(int row = 0; row<9; row++){
            leftSudukoRow[row]= new JPanel(new FlowLayout(FlowLayout.LEFT,3,0));
            leftSudukoRow[row].setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
            leftSudukoRow[row].setBackground(Color.darkGray);
            inputSuduko.add(leftSudukoRow[row]);
            for (int col = 0; col<9; col++){
                JButton square = new JButton();
                square.setText(Integer.toString(inputBoardNumber[row][col]));
                square.setMargin(buttonMargin);
                square.setBorderPainted(false);
                square.setFocusPainted(false);
                square.setBorder(BorderFactory.createCompoundBorder(square.getBorder(), BorderFactory.createEmptyBorder(5, 10, 5, 10)));

                //color squares depending on if they are in the set
                if (coloredCenter.contains(row)&&coloredCenter.contains(col)||coloredEdge.contains(row)&&coloredEdge.contains(col)) {
                    square.setBackground(Color.decode(HIGHLIGHT_COLOR));
                } else {
                    square.setBackground(Color.decode(BASE_COLOR));
                }

                // if 0, hide square
                if (inputBoardNumber[row][col]==0){
                    square.setForeground(square.getBackground());
                } else {
                    square.setForeground(Color.decode(TEXT_COLOR));
                }

                leftSudukoRow[row].add(square);

                //toggle number up/down
                square.addActionListener((o) -> {
                    if (locked) return; //TODO replace this function to update suduko board
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

        int[][] outputBoardNumber = outputBoard.getBoard();

        for(int row = 0; row<9; row++){
            rightSudukoRow[row]= new JPanel(new FlowLayout(FlowLayout.LEFT,3,0));
            rightSudukoRow[row].setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
            rightSudukoRow[row].setBackground(Color.darkGray);
            outputSuduko.add(rightSudukoRow[row]);
            for (int col = 0; col<9; col++){
                rightButtons[row][col] = new JButton();
                rightButtons[row][col].setText(outputBoardNumber[row][col]+"");
                rightButtons[row][col].setMargin(buttonMargin);
                rightButtons[row][col].setBorderPainted(false);
                rightButtons[row][col].setFocusPainted(false);
                rightButtons[row][col].setBorder(BorderFactory.createCompoundBorder(rightButtons[row][col].getBorder(), BorderFactory.createEmptyBorder(5, 10, 5, 10)));

                //color squares depending on if they are in the set
                if (coloredCenter.contains(row)&&coloredCenter.contains(col)||coloredEdge.contains(row)&&coloredEdge.contains(col)) {
                    rightButtons[row][col].setBackground(Color.decode(HIGHLIGHT_COLOR));
                } else {
                    rightButtons[row][col].setBackground(Color.decode(BASE_COLOR));
                }

                rightButtons[row][col].setForeground(rightButtons[row][col].getBackground());

                rightSudukoRow[row].add(rightButtons[row][col]);
            }
        }

        //center menu panel holder
        JPanel menurow = new JPanel(new GridLayout(0,1));
        menurow.setBackground(Color.decode(TEXT_COLOR));
        menurow.setPreferredSize(new Dimension(200,200));
        menurow.setMinimumSize(new Dimension(200,200));
        menurow.setMaximumSize(new Dimension(200,200));
        pane.add(menurow);

        //center title
        JLabel titleText = new JLabel("SUDUKO SOLVER");
        titleText.setFont(new Font("Serif", Font.PLAIN, 28));
        titleText.setHorizontalAlignment(SwingConstants.CENTER);
        titleText.setForeground(Color.decode("#e2e2e2"));
        menurow.add(titleText);

        //center line 2 title
        JLabel titleText2 = new JLabel("- PREMIUM EDITION -");
        titleText2.setFont(new Font("Serif", Font.PLAIN, 16));
        titleText2.setHorizontalAlignment(SwingConstants.CENTER);
        titleText2.setForeground(Color.decode("#e2e2e2"));
        menurow.add(titleText2);

        JButton transferButton = new JButton(">>>>       TRANSFER       >>>>");
        transferButton.setHorizontalAlignment(SwingConstants.CENTER);
        menurow.add(transferButton);
        transferButton.addActionListener((o) -> {
            outputBoard = new Sudoku();
            outputBoard.init(inputBoard.getBoard()); //output copy input board
            updateOutputBoard();
        });

        JButton loadButton = new JButton("LOAD FROM FILE");
        loadButton.setHorizontalAlignment(SwingConstants.CENTER);
        menurow.add(loadButton);
        loadButton.addActionListener((o) -> {
            try {
                outputBoard = getBoardFromFile();
                updateOutputBoard(); 
            } catch (NullPointerException e){
                e.printStackTrace();
                return;
            }


            
        });

        JButton solveButton = new JButton("SOLVE");
        solveButton.setHorizontalAlignment(SwingConstants.CENTER);
        menurow.add(solveButton);
        solveButton.addActionListener((o) -> {
            
        });

        JButton clearButton = new JButton("CLEAR");
        clearButton.setHorizontalAlignment(SwingConstants.CENTER);
        menurow.add(clearButton);
        clearButton.addActionListener((o) -> {
            outputBoard = new Sudoku();
            outputBoard.init(new int[9][9]); //output become an empty board
            updateOutputBoard();
        });
        
        //create window
        frame.pack();
        frame.setVisible(true);
    }

    private void updateOutputBoard(){
        int[][] board = outputBoard.getBoard();
            for(int row = 0; row<9; row++){
                for (int col = 0; col<9; col++){
                    rightButtons[row][col].setText(board[row][col]+"");
                    if (board[row][col]==0){
                        rightButtons[row][col].setForeground(rightButtons[row][col].getBackground());
                    } else {
                        rightButtons[row][col].setForeground(Color.decode(TEXT_COLOR));
                    }
                    
                }
            }
            outputBoard = new Sudoku();
            outputBoard.init(board);
    }

    private Sudoku getBoardFromFile(){
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = jfc.showOpenDialog(null);
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        File selectedFile;
        int[][] board = new int[9][9];
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = jfc.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
        } else {
            return null;
        }
        try {
            Scanner input = new Scanner(selectedFile);
            int row = 0;
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] number = line.split("");
                for (String s: number){
                    System.out.println(s); 
                }
                for (int col = 0; col<9; col++){
                    board[row][col] = Integer.parseInt(number[col]);
                }
                row++;
            }
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        Sudoku suduko = new Sudoku();
        suduko.init(board);
        return suduko;
    }

    public Sudoku getInputBoard(){
        return inputBoard;
    }

    public Sudoku getOutputBoard(){
        return outputBoard;
    }

    public void setOutputBoard(int[][] newBoard){
        outputBoard = new Sudoku();
        outputBoard.init(newBoard);
    }
}