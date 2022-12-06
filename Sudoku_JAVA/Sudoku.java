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

        //checkar columner
        for(int r=0; r<9; r++){
           if(row != r && board[r][col] == value){
            return false;
           }
        }
        //checkar rader
        for(int c=0; c<9; c++){
            if(col != c && board[row][c] == value){
                return false; 
            }
        }

        //checkar inom varje 3x3. Finns en känd metod att använda modulu. Ska kika på denna! //Axel 
        for(int i=0; i<9; i++){
            for(int r=0; r<3; r++){
                for(int c=0; c<3; c++){
                    if(row != r && col != c && board[r+3*i/3][c+3*i/3] == value){
                        return false;
                    }
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
    public void init(int[][] start){
        for(int r=0; r<9; r++){
            for(int c=0; c<9; c++){
                board[r][c] = start[r][c]; 
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
    public boolean solve(int row, int col) { 
            for(int r = 0; r < 9; r++){
                for(int c = 0; c < 9; c++){
                    if(board[r][c] != 0){
                        if(!checkIfLegal(r, c, board[r][c])){
                            return false;
                        }
                    }
                }
            }
            return rSolve(0, 0);
        } //solve(0,0)       

    /**
     * Recursive method to solve current sudoku game. 
     * @return  true if solution was found, false otherwise 
     */ 
    private boolean rSolve(int row, int col){ //rekursiv metod som först kollar om värdet går att sätta i platsen, om det är tillåtet gör den det. Annars returnar false.
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

        for(int i = 0; i < 9; i++){
            if(checkIfLegal(row, col, i)){ //kollar om värdet går att tillsätta i platsen
                board[row][col] = i;
                
                if(rSolve(row, col+1)){ //om det gick att tillsätta nästa plats, fortsätter denna metod.
                    return true; 
                }
            }
            board[row][col] = 0; 
        }

        return false; //Fanns ingen lösning. Nollställer platsen ovan. 
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
     * Removes the value at row, col.
     */
    public void remove(int row, int col){
        board[row][col] = 0; 
    }

    
}
