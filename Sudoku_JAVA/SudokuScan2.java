package Sudoku_JAVA;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class SudokuScan2{
   
    private List<Sudoku> textToMatrix() {
        // Create a file chooser that allows the user to select a file
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    
        // Show the file chooser and check if the user selected a file
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue != JFileChooser.APPROVE_OPTION) {
            return null;
        }
    
        File selectedFile = fileChooser.getSelectedFile();
        System.out.println(selectedFile.getAbsolutePath());
    
        // Create a list to store the Sudoku puzzles
       ArrayList<Sudoku> sudokuList = new ArrayList<Sudoku>();
    
        try {
            Scanner input = new Scanner(selectedFile);
    
            // Read the file line by line and parse the Sudoku puzzles
            int[][] board= new int[9][9];
            int row=0;
            while (input.hasNextLine()) {
                String line = input.nextLine();

                if (line.equals(" ")) {
                    Sudoku sudoku= new Sudoku();
                    // If the line is empty, it signals the end of the current puzzle
                    sudoku.setMatrix(board);
                    sudokuList.add(sudoku);
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sudokuList;
    }
    public static void main(String[] args) {
        SudokuScan2 scan = new SudokuScan2();
       for(Sudoku s: scan.textToMatrix()){
        int[][] board = s.getMatrix();
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                System.out.print(board[i][j]);
            }
            System.out.println( "--");
        }
    

       }
        
    
        
    }
    
}
