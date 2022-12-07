package Sudoku_JAVA;
public interface SudokuSolver{
    
    /**
     * Check if it is legal to place value at row, col.
     * @return true if value can be placed at row, col, false otherwise
     */
    boolean legal(int digit, int row, int col);

    /**
     * Check if the position at row, col has a start value
     * @return true if the position has no start value, false otherwise
     */
    boolean checkIfEmpty(int row, int col);

      /**
	 * Fills the grid with the digits in m. The digit 0 represents an empty box.
	 * 
	 * @param m the matrix with the digits to insert
	 * @throws IllegalArgumentException if m has the wrong dimension or contains
	 *                                  values outside the range [0..9]
	 */
    void setMatrix(int[][] matrix);
    /**
     * Returns the solution.
     * @return int matrix with a valid solution
     */
    int[][] getMatrix();
    /**
     * Method to solve the sudoku.
     * @return true if solution was found, false otherwise
     */
    boolean solve(int row, int col); //solve(0, 0)

    /**
     * Adds value value at position row, col.
     */
    void set(int digit, int row, int col);

    /**
     * Clears the sudoku.
     */
    void clear();

    /**
     * Removes the value at row, col.
     */
    void remove(int row, int col);

}