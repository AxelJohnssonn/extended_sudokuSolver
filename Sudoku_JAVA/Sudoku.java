package Sudoku_JAVA;

import javax.swing.JOptionPane;

public class Sudoku implements SudokuSolver{
    private int[][] board;

    public Sudoku() {
        this.board = new int[9][9];
    }

    /**
     * Check if it is legal to place digit at row, col.
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
     */
    public void setMatrix(int[][] matrix){
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
        return board; 
    }
    /**
     * Method to solve the sudoku.
     * @return true if solution was found, false otherwise
     */
    public boolean solve() { 
            for(int r = 0; r < 9; r++){
                for(int c = 0; c < 9; c++){
                    if(board[r][c] != 0){
                        System.out.print(r+" "+c);
                        if(!legal(board[r][c], r, c)){
                            JOptionPane.showMessageDialog(null, "CRITIAL ERROR: UNSOLVABLE INPUT", "ERROR", JOptionPane.ERROR_MESSAGE);
                            return false;
                        }
                    }
                }
            }
            return rSolve(0, 0);
        }        

    /**
     * Recursive method to solve current sudoku game. 
     * @return  true if solution was found, false otherwise 
     */ 
    private boolean rSolve(int row, int col){ 
        if(col == 9){
            col = 0; 
            row++;
        }
        if(row == 9){
            return true; 
        }
        if(board[row][col] != 0){
            return rSolve(row, col +1);
        }   
            
        for(int i = 1; i <= 9; i++){
            if(legal(i, row, col)){ 
                board[row][col] = i;
                
                if(rSolve(row, col+1)){ 
                    return true; 
                }
            }
            board[row][col] = 0; 
        }

        return false; 
    } 

    /**
     * Adds value value at position row, col.
     */
    public void add(int row, int col, int value){
        board[row][col] = value; 
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
     * @param col col position of the matrix
     */
    public void remove(int row, int col){
        board[row][col] = 0; 
    }
    /**
     * Set the digit at row, col
     * @param row row position of the matrix
     * @param col col position of the matrix
     */

    @Override
    public void set(int digit, int row, int col) {
        board[row][col] = digit;     
    }

    
}
