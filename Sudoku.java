public class Sudoku implements SudokuSolver{
    private int[][] board;

    public Sudoku() {
        //Testar endast med 3x3
        this.board = new int[3][3];
    }

    /**
     * Check if it is legal to place value at row, col.
     * @return true if value can be placed at row, col, false otherwise
     */
    boolean checkIfLegal(int row, int col, int value);

    /**
     * Check if the position at row, col has a start value
     * @return true if the position has no start value, false otherwise
     */
    boolean checkIfEmpty(int row, int col);

    /**
     * Initializes the board with values in the matrix start.
     */
    void init(int[][] start);
    /**
     * Returns the solution.
     * @return int matrix with a valid solution
     */
    int[][] getBoard();
    /**
     * Method to solve the sudoku.
     * @return true if solution was found, false otherwise
     */
    boolean solve(int row, int col); //solve(0, 0)

    /**
     * Adds value value at position row, col.
     */
    void add(int row, int col, int value);

    /**
     * Clears the sudoku.
     */
    void clear();

    /**
     * Removes the value at row, col.
     */
    void remove(int row, int col);

    
}
