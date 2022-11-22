package Sudoku_JAVA;
public class Sudoku implements SudokuSolver{
    private int[][] board;

    public Sudoku() {
        this.board = new int[9][9];
    }

    /**
     * Check if it is legal to place value at row, col.
     * @return true if value can be placed at row, col, false otherwise
     */
    public boolean checkIfLegal(int row, int col, int value){
        return true;
    }

    /**
     * Check if the position at row, col has a start value
     * @return true if the position has no start value, false otherwise
     */
    public boolean checkIfEmpty(int row, int col){
        return true;
    }

    /**
     * Initializes the board with values in the matrix start.
     */
    public void init(int[][] start){
        for(int r=0; r<9; r++){
            for(int c=0; c<9; c++){
                board[r][c] =start[r][c]; 
            }
        }
    }
    /**
     * Returns the solution.
     * @return int matrix with a valid solution
     */
    public int[][] getBoard(){
        return board; 
    }
    /**
     * Method to solve the sudoku.
     * @return true if solution was found, false otherwise
     */
    public boolean solve(int row, int col){
        return true;
    } //solve(0, 0)

    /**
     * Adds value value at position row, col.
     */
    public void add(int row, int col, int value){

    }

    /**
     * Clears the sudoku.
     */
    public void clear(){

    }

    /**
     * Removes the value at row, col.
     */
    public void remove(int row, int col){
        
    }

    
}
