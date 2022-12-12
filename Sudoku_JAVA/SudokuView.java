package Sudoku_JAVA;

import java.awt.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileSystemView;

public class SudokuView {
    public static boolean locked = false; // if you want to lock user inputs on sudoku
    public static final String BASE_COLOR = "#6f73d2";
    public static final String HIGHLIGHT_COLOR = "#9dacff";
    public static final String BORDER_COLOR = "#2A0154";
    public static final String DARK_BG_COLOR = "#1e1e1e";
    public static final String TEXT_COLOR_DARK = DARK_BG_COLOR;
    public static final String TEXT_COLOR_DARK_HIGHLIGHT = BORDER_COLOR;
    public static final String TEXT_COLOR_LIGHT = "#e1e1e1";

    private JTextFieldLimit[][] textFields = new JTextFieldLimit[9][9];

    private JFrame frame;
    private JPanel sudokuView;

    private Sudoku outputBoard;

    public SudokuView(Sudoku board) {
        outputBoard = board;
        SwingUtilities.invokeLater(() -> createWindow("Sudoku", 515, 600));
    }
    /**
     * Method to initializes the GUI
     * @param title The title of the window
     * @param width The width of the window
     * @param height The height of the window
     */
    private void createWindow(String title, int width, int height) {
        // main frame
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSizeOnPanel(frame,width,height);
        frame.setResizable(false);
        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());
        pane.setBackground(Color.darkGray); // Will be changed

        // ------------------------------------------------------------------------
        // START top title row
        JPanel topPanel = new JPanel(new FlowLayout());
        setSizeOnPanel(topPanel,200,120);
        pane.add(topPanel, BorderLayout.NORTH);

        JLabel titleText = new JLabel("SUDOKU  SOLVER");
        titleText.setFont(new Font("Papyrus", Font.PLAIN, 30));
        titleText.setHorizontalAlignment(SwingConstants.CENTER);
        titleText.setForeground(Color.decode("#e2e2e2"));
        titleText.setBorder(BorderFactory.createCompoundBorder(titleText.getBorder(),
                BorderFactory.createEmptyBorder(30, 0, 0, 0)));
        topPanel.add(titleText);
        // END top title row
        // ------------------------------------------------------------------------

        // ------------------------------------------------------------------------
        // START center sudoku holder panel
        sudokuView = new JPanel();
        sudokuView.setLayout(new GridLayout(0, 1, 0, 3)); // stacks the flowlayout rows
        setSizeOnPanel(sudokuView,500,300);
        sudokuView.setBackground(Color.decode(BORDER_COLOR));
        sudokuView.setBorder(BorderFactory.createCompoundBorder(sudokuView.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        pane.add(sudokuView, BorderLayout.CENTER); // input sudoko is left/west

        // the sudoku variables
        JPanel[] sudokuRow = new JPanel[9]; // create the rows
        Set<Integer> coloredEdge = new HashSet<Integer>(Arrays.asList(0, 1, 2, 6, 7, 8));
        Set<Integer> coloredCenter = new HashSet<Integer>(Arrays.asList(3, 4, 5));

        int[][] boardNumber = outputBoard.getMatrix();

        for (int row = 0; row < 9; row++) {
            sudokuRow[row] = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 0));
            sudokuRow[row].setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            sudokuRow[row].setBackground(Color.decode(BORDER_COLOR));
            sudokuView.add(sudokuRow[row]);

            for (int col = 0; col < 9; col++) {
                textFields[row][col] = new JTextFieldLimit();
                setSizeOnPanel(textFields[row][col], 40 , 40);
                if (boardNumber[row][col]!=0) {
                    textFields[row][col].setText(boardNumber[row][col]+"");
                }

                // color squares background depending on if they are in the set
                if (coloredCenter.contains(row) && coloredCenter.contains(col)
                        || coloredEdge.contains(row) && coloredEdge.contains(col)) {
                    textFields[row][col].setBackground(Color.decode(HIGHLIGHT_COLOR));
                } else {
                    textFields[row][col].setBackground(Color.decode(BASE_COLOR));
                }
                 
                sudokuRow[row].add(textFields[row][col]);
            }
        }

        // END center panel
        // ------------------------------------------------------------------------

        // ------------------------------------------------------------------------
        // START side images (left)

        JPanel leftSide = new JPanel();
        setSizeOnPanel(leftSide, 50 , 300);
        pane.add(leftSide, BorderLayout.WEST);

        // (right)
        JPanel rightSide = new JPanel();
        setSizeOnPanel(rightSide, 50 , 300);
        pane.add(rightSide, BorderLayout.EAST);

        // (flames)
        try {
            URL url = new URL("https://media.tenor.com/FKqQdNFwBu8AAAAC/flames-flaming.gif");
            Icon icon = new ImageIcon(url);
            JLabel leftFlames = new JLabel(icon);
            leftSide.add(leftFlames);
            JLabel rightFlames = new JLabel(icon);
            rightSide.add(rightFlames);
        } catch (MalformedURLException e) {
            return;
        }
        // END side images
        // ------------------------------------------------------------------------

        // ------------------------------------------------------------------------
        // START bottom menu panel holder
        JPanel menurow = new JPanel(new FlowLayout());
        setSizeOnPanel(menurow,200,100);
        pane.add(menurow, BorderLayout.SOUTH);

        JPanel menurowCenter = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        menurowCenter.setBorder(BorderFactory.createCompoundBorder(menurowCenter.getBorder(),
                BorderFactory.createEmptyBorder(30, 0, 0, 0)));
        menurowCenter.setBackground(Color.decode(DARK_BG_COLOR));
        menurow.add(menurowCenter);

        JButton loadButton = new JButtonSudokuMenu("LOAD");
        menurowCenter.add(loadButton);
        loadButton.addActionListener((o) -> {
            if (locked) {
                return;
            }
            Sudoku loadedBoard = getBoardFromFile();
            if (loadedBoard != null) {
                outputBoard = loadedBoard;
                updateOutputBoard();
            } else {
                System.out.print("Could not load");
            }
        });

        JButton solveButton = new JButtonSudokuMenu("SOLVE");
        menurowCenter.add(solveButton);
        solveButton.addActionListener((o) -> {
            if (locked) return;
            locked = true; //prevent other buttons from working
            
            sudokuView.setBackground(Color.YELLOW);
            int[][] copyBoard = new int[9][9]; //save nonempty squares
            for (int row = 0 ; row<9 ; row++) {
                for (int col = 0; col<9 ; col++) {
                    if (textFields[row][col].getText().equals("")) {
                        copyBoard[row][col] = 0;
                    } else {
                        copyBoard[row][col] = Integer.parseInt(textFields[row][col].getText());
                    }
                }
            }

            outputBoard.setMatrix(copyBoard);

            if (!outputBoard.solve()) {
                JOptionPane.showMessageDialog(sudokuView,
                "CRITIAL ERROR: UNSOLVABLE INPUT", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                colorLegends(copyBoard); // colors the numbers that where there before solve
                updateOutputBoard();
            }
            
            locked = false; // realease buttons
            sudokuView.setBackground(Color.decode(BORDER_COLOR)); 
        });

        JButtonSudokuMenu clearButton = new JButtonSudokuMenu("CLEAR");
        menurowCenter.add(clearButton);
        clearButton.addActionListener((o) -> {
            if (locked) {
                return;
            }
            outputBoard.clear();
            updateOutputBoard();
        });
        // END bottom panel menu row
        // ------------------------------------------------------------------------

        // create window
        frame.pack();
        frame.setVisible(true);
    }
    /**
     * Help method to set all sizes of an J-object
     * @param o The J-object
     * @param width Width of the size of an J-object
     * @param height Height of the size of an J-object
     */
    private void setSizeOnPanel(Object o, int width, int height) {
        if (o instanceof JTextFieldLimit) {
            JTextFieldLimit panel = (JTextFieldLimit) o;
            panel.setPreferredSize(new Dimension(width, height));
            panel.setMinimumSize(new Dimension(width, height));
            panel.setMaximumSize(new Dimension(width, height));
            panel.setBackground(Color.decode(DARK_BG_COLOR));
        }
        if (o instanceof JPanel) {
            JPanel panel = (JPanel) o;
            panel.setPreferredSize(new Dimension(width, height));
            panel.setMinimumSize(new Dimension(width, height));
            panel.setMaximumSize(new Dimension(width, height));
            panel.setBackground(Color.decode(DARK_BG_COLOR));
        }
        if (o instanceof JFrame) {
            JFrame panel = (JFrame) o;
            panel.setPreferredSize(new Dimension(width, height));
            panel.setMinimumSize(new Dimension(width, height));
            panel.setMaximumSize(new Dimension(width, height));
            panel.setBackground(Color.decode(DARK_BG_COLOR));
        }
    }
    /**
     * Updates the board
     */
    private void updateOutputBoard() {
        int[][] board = outputBoard.getMatrix();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                textFields[row][col].setText(board[row][col] + "");
            }
        }
    }
    /**
     * Method to highlight changed values of the sudoku-matrix
     * @param copyBoard The matrix before solve
     */
    private void colorLegends(int[][] copyBoard) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (copyBoard[row][col] == outputBoard.getMatrix()[row][col]) { // old number
                    textFields[row][col].setFont(new Font("Verdana", Font.PLAIN, 18));
                    textFields[row][col].setForeground(Color.decode(TEXT_COLOR_DARK));
                } else { // new number
                    textFields[row][col].setFont(new Font("Verdana", Font.BOLD, 18));
                    textFields[row][col].setForeground(Color.decode(TEXT_COLOR_DARK_HIGHLIGHT));
                }

            }
        }
    }
    /**
     * 
     * @return
     */
    private Sudoku getBoardFromFile() {
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
                for (String s : number) {
                    System.out.println(s);
                }
                for (int col = 0; col < 9; col++) {
                    board[row][col] = Integer.parseInt(number[col]);
                }
                row++;
            }
            input.close();
        } catch (FileNotFoundException e) {
            return null;
        }
        Sudoku sudoku = new Sudoku();
        sudoku.setMatrix(board);
        return sudoku;
    }
}
