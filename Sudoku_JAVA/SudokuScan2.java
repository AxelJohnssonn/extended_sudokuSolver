package Sudoku_JAVA;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class SudokuScan2{
    private List<Sudoku> savedBoards;
    private int nextIndex;

    public SudokuScan2(){
        savedBoards = new ArrayList<Sudoku>();
        nextIndex = 0;
    }

    public Boolean loadBoards(){
        if (savedBoards.size()>0) return true;
        return textToMatrix();
    }

    public Sudoku getNextBoard(){
        if (++nextIndex>=savedBoards.size()) nextIndex=0;
        Sudoku sudoku = new Sudoku();
        sudoku.setMatrix(savedBoards.get(nextIndex).getMatrix());
        return sudoku;
    }
   
    private Boolean textToMatrix() {
        // Create a file chooser that allows the user to select a file
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    
        // Show the file chooser and check if the user selected a file
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue != JFileChooser.APPROVE_OPTION) {
            return false;
        }
    
        File selectedFile = fileChooser.getSelectedFile();
        System.out.println(selectedFile.getAbsolutePath());
    
        try {
            Scanner input = new Scanner(selectedFile);
            if (!input.hasNextLine()) return false;
    
            // Read the file line by line and parse the Sudoku puzzles
            int[][] board= new int[9][9];
            int row=0;
            while (input.hasNextLine()) {
                String line = input.nextLine();

                if (line.length() <3) {
                    Sudoku sudoku= new Sudoku();
                    // If the line is empty, it signals the end of the current puzzle
                    sudoku.setMatrix(board);
                    savedBoards.add(sudoku);
                    board = new int[9][9];
                    row = 0;
                } else{
                    // Parse the current line as a row of the Sudoku puzzle
                    String[] numbers = line.split("");
                
                    for (int col = 0; col < 9; col++) {
                        board[row][col] = Integer.parseInt(numbers[col]);
                    }
                    row++;
                }
    
            }
            input.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        
        return true;
    }
}
