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
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileSystemView;

public class SudokuView {
    public static boolean locked = false; // if you want to lock user inputs on suduko
    public static final String BASE_COLOR = "#6f73d2";
    public static final String HIGHLIGHT_COLOR = "#9dacff";
    public static final String BORDER_COLOR = "#2A0154";
    public static final String DARK_BG_COLOR = "#1e1e1e";
    public static final String TEXT_COLOR_DARK = DARK_BG_COLOR;
    public static final String TEXT_COLOR_DARK_HIGHLIGHT = BORDER_COLOR;
    public static final String TEXT_COLOR_LIGHT = "#e1e1e1";

    private JTextFieldLimit[][] textFields = new JTextFieldLimit[9][9];

    private JFrame frame;
    private JPanel sudukoView;

    private Sudoku outputBoard;

    public SudokuView(Sudoku board) {
        outputBoard = board;
        SwingUtilities.invokeLater(() -> createWindow("Suduko", 597, 600));
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
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height)); 
        frame.setMinimumSize(new Dimension(width, height)); 
        frame.setResizable(false);
        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());
        pane.setBackground(Color.darkGray); // Will be changed

        // ------------------------------------------------------------------------
        // START top title row
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.setBackground(Color.decode(DARK_BG_COLOR));
        topPanel.setPreferredSize(new Dimension(200, 120));
        topPanel.setMinimumSize(new Dimension(200, 120));
        topPanel.setMaximumSize(new Dimension(200, 120));
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
        // START center suduko holder panel
        sudukoView = new JPanel();
        sudukoView.setLayout(new GridLayout(0, 1, 0, 3)); // stacks the flowlayout rows
        sudukoView.setBackground(Color.decode(BORDER_COLOR));
        sudukoView.setPreferredSize(new Dimension(500, 300));
        sudukoView.setMinimumSize(new Dimension(500, 300));
        sudukoView.setMaximumSize(new Dimension(500, 300));
        sudukoView.setBorder(BorderFactory.createCompoundBorder(sudukoView.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        pane.add(sudukoView, BorderLayout.CENTER); // input sudoko is left/west

        // the suduko variables
        Insets zeroMargin = new Insets(0, 0, 0, 0); // remove standard margins
        JPanel[] sudukoRow = new JPanel[9]; // create the rows
        Set<Integer> coloredEdge = new HashSet<Integer>(Arrays.asList(0, 1, 2, 6, 7, 8));
        Set<Integer> coloredCenter = new HashSet<Integer>(Arrays.asList(3, 4, 5));

        int[][] boardNumber = outputBoard.getMatrix();

        for (int row = 0; row < 9; row++) {
            sudukoRow[row] = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 0));
            sudukoRow[row].setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            sudukoRow[row].setBackground(Color.decode(BORDER_COLOR));
            sudukoView.add(sudukoRow[row]);

            for (int col = 0; col < 9; col++) {
                textFields[row][col] = new JTextFieldLimit();
                if (boardNumber[row][col]!=0) {
                    textFields[row][col].setText(Integer.toString(boardNumber[row][col]));
                }
                textFields[row][col].setFont(new Font("Verdana", Font.BOLD, 18));
                textFields[row][col].setMargin(zeroMargin);
                textFields[row][col].setBorder(BorderFactory.createCompoundBorder(textFields[row][col].getBorder(), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
                textFields[row][col].setPreferredSize(new Dimension(40, 40));
                textFields[row][col].setMinimumSize(new Dimension(40,40));
                textFields[row][col].setMaximumSize(new Dimension(40,40));

                // color squares background depending on if they are in the set
                if (coloredCenter.contains(row) && coloredCenter.contains(col)
                        || coloredEdge.contains(row) && coloredEdge.contains(col)) {
                    textFields[row][col].setBackground(Color.decode(HIGHLIGHT_COLOR));
                } else {
                    textFields[row][col].setBackground(Color.decode(BASE_COLOR));
                }

                textFields[row][col].setForeground(Color.decode(TEXT_COLOR_DARK));
                 

                sudukoRow[row].add(textFields[row][col]);
            }
        }

        // END center panel
        // ------------------------------------------------------------------------

        // ------------------------------------------------------------------------
        // START side images (left)

        JPanel leftSide = new JPanel();
        leftSide.setPreferredSize(new Dimension(50, 300));
        leftSide.setMinimumSize(new Dimension(50, 300));
        leftSide.setMaximumSize(new Dimension(50, 300));
        leftSide.setBackground(Color.decode(DARK_BG_COLOR));
        pane.add(leftSide, BorderLayout.WEST);

        // (right)
        JPanel rightSide = new JPanel();
        rightSide.setPreferredSize(new Dimension(50, 300));
        rightSide.setMinimumSize(new Dimension(50, 300));
        rightSide.setMaximumSize(new Dimension(50, 300));
        rightSide.setBackground(Color.decode(DARK_BG_COLOR));
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
        menurow.setBackground(Color.decode(DARK_BG_COLOR));
        menurow.setPreferredSize(new Dimension(200, 100));
        menurow.setMinimumSize(new Dimension(200, 100));
        menurow.setMaximumSize(new Dimension(200, 100));
        pane.add(menurow, BorderLayout.SOUTH);

        JPanel menurowCenter = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        menurowCenter.setBorder(BorderFactory.createCompoundBorder(menurowCenter.getBorder(),
                BorderFactory.createEmptyBorder(30, 0, 0, 0)));
        menurowCenter.setBackground(Color.decode(DARK_BG_COLOR));
        menurow.add(menurowCenter);

        JButton loadButton = new JButton("LOAD");
        loadButton.setHorizontalAlignment(SwingConstants.CENTER);
        loadButton.setBackground(Color.decode(DARK_BG_COLOR));
        loadButton.setForeground(Color.decode(TEXT_COLOR_LIGHT));
        loadButton.setFont(new Font("Papyrus", Font.BOLD, 15));
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

        JButton solveButton = new JButton("SOLVE");
        solveButton.setHorizontalAlignment(SwingConstants.CENTER);
        solveButton.setFont(new Font("Papyrus", Font.BOLD, 15));
        solveButton.setBackground(Color.decode(DARK_BG_COLOR));
        solveButton.setForeground(Color.decode(TEXT_COLOR_LIGHT));
        menurowCenter.add(solveButton);
        solveButton.addActionListener((o) -> {
            if (locked){
                return;
            }
            locked = true; //prevent other buttons from working
            sudukoView.setBackground(Color.YELLOW);
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
            outputBoard.solve();
            colorLegends(copyBoard); // colors the numbers that where there before solve
            updateOutputBoard();
            locked = false; // realease buttons
            sudukoView.setBackground(Color.decode(BORDER_COLOR)); 
        });

        JButton clearButton = new JButton("CLEAR");
        clearButton.setHorizontalAlignment(SwingConstants.CENTER);
        clearButton.setFont(new Font("Papyrus", Font.BOLD, 15));
        clearButton.setBackground(Color.decode(DARK_BG_COLOR));
        clearButton.setForeground(Color.decode(TEXT_COLOR_LIGHT));
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
                    textFields[row][col].setFont(new Font("Verdana", Font.BOLD, 18));
                    textFields[row][col].setForeground(Color.decode(TEXT_COLOR_DARK));
                } else { // new number
                    textFields[row][col].setFont(new Font("Verdana", Font.PLAIN, 18));
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
        Sudoku suduko = new Sudoku();
        suduko.setMatrix(board);
        return suduko;
    }
}
