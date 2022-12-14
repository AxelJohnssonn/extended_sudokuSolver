package Sudoku_JAVA;

import java.util.Arrays;
import java.util.HashSet;

public class Sudoku implements SudokuSolver{
    private int[][] board;

    public Sudoku() {
        this.board = new int[9][9];
    }

    /**
     * Check if it is legal to place digit at row, col.
     * @param digit Value that needs to be checked if legal
     * @param row row position in the matrix
     * @param col column position in the matrix 
     * @return true if value can be placed at row, col, false otherwise
     */
    public boolean legal(int digit, int row, int col){
        
        //Checks values at cols
        for(int r=0; r<9; r++){
           if(row != r && board[r][col] == digit){
            return false;
           }
        }
        //Checks values at rows
        for(int c=0; c<9; c++){
            if(col != c && board[row][c] == digit){
                return false; 
            }
        }
        //Checks values at 3x3
        int x = row - row%3; 
        int y = col - col%3; 
        
       for(int r=x; r < x+3; r++){
        for(int c=y; c < y+3; c++){
            if(board[r][c] == digit && col != c && row != r){
                return false; 
            }
        }
       }

        return true;
    }

    /**
     * Check if the position at row, col has a start value
     * @param row row position in the matrix
     * @param col column position in the matrix 
     * @return true if the position has no start value, false otherwise
     */
    public boolean checkIfEmpty(int row, int col){

        if(board[row][col] == 0){
            return true; 
        }

        return false;
    }

    /**
     * Initializes the board with values in the matrix start.
     * @param matrix matrix board with input values
     * @throws IllegalArgumentException if matrix has the wrong dimension or contains
	 *                                  values outside the range [0..9]
     */
    public void setMatrix(int[][] matrix){
        for(int r=0; r<9; r++){
            for(int c=0; c<9; c++){
                if(matrix[r][c] > 9 || matrix[r][c] < 0 || board.length > 9 || board[0].length > 9){
                    throw new IllegalArgumentException();
                }
            }
        }

        for(int r=0; r<9; r++){
            for(int c=0; c<9; c++){
                board[r][c] = matrix[r][c]; 
            }
        }
    }
    /**
     * Returns the solution.
     * @return int matrix/board with a valid solution
     */
    public int[][] getMatrix(){
        //board.solve() 
        int[][] matrix = new int[9][9];
        for(int r=0; r<9; r++){
            for(int c=0; c<9; c++){
                matrix[r][c] = board[r][c]; 
            }
        }
        return matrix; 
    }
    /**
     * Method to solve the sudoku.
     * @return true if solution was found, false otherwise
     */
    public boolean solve() { 
            for(int r = 0; r < 9; r++){
                for(int c = 0; c < 9; c++){
                    if(board[r][c] != 0){
                        if(!legal(board[r][c], r, c)){
                            return false;
                        }
                    }
                }
            }
            return solve(0, 0);
        }                        

    /**
     * Recursive method to solve current sudoku game. 
     * @param row row position of the matrix
     * @param col column position of the matrix
     * @return  true if solution was found, false otherwise 
     */ 
    private boolean solve(int row, int col){ 
        if(col == 9){
            col = 0; 
            row++;
        }
        if(row == 9){
            return true; 
        }
        if(board[row][col] != 0){
            return solve(row, col +1);
        }   

        for(int i = 1; i <= 9; i++){
            if(legal(i, row, col)){ 
                board[row][col] = i;
                
                if(solve(row, col+1)){ 
                    return true; 
                }
            }
            board[row][col] = 0; 
        }

        return false; 
    } 

    /**
     * Set the digit at row, col
     * @param value value to set at position
     * @param row row position of the matrix
     * @param col column position of the matrix
     */
    public void set(int digit, int row, int col) {
        HashSet<Integer> set = new HashSet<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        if(set.contains(digit)){
            board[row][col] = digit;  
        }          
    }

    /**
     * Clears the sudoku.
     */
    public void clear(){
        for(int r=0; r<9; r++){
            for(int c=0; c<9; c++){
                board[r][c] = 0; 
            }
        }
    }

    /**
     * Removes the digit at row, col.
     * @param row row position of the matrix
     * @param col column position of the matrix
     */
    public void remove(int row, int col){
        board[row][col] = 0; 
    }

    
}
