package Sudoku_JAVA;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class SudokuScan {

    public void scanBoard(int[][] board) {

    }

    private void loopThroughBoard(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                System.out.print(board[row][col]);
            }
            System.out.println();
        }
    }

    private Sudoku textToMatrix() {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = fileChooser.showOpenDialog(null);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        File selectedFile;
        ArrayList<Sudoku> sudokuList = new ArrayList<Sudoku>();

        // int[][] board = new int[9][9];
        int row = 0;
        int rowCounter = 0;
        String word;

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
        } else {
            return null;
        }
        try {
            Scanner input = new Scanner(selectedFile);

            while (input.hasNextLine()) {
                word = input.nextLine();
                int[][] board = new int[9][9];
                if (!word.equals("")) {

                    String line = input.nextLine();
                    String[] number = line.split("");
                    for (int col = 0; col < 9; col++) {
                        board[row][col] = Integer.parseInt(number[col]);
                    }
                    row++;
                    rowCounter++;

                   
                }
                

            }

            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return null;

        }

        return sudokuList.get(0);

    }

    public static void main(String[] args) {
        SudokuScan scan = new SudokuScan();
        scan.textToMatrix();

    }

}
