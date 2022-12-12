package Sudoku_JAVA;
public class SudokuMain{
    public static void main(String[] args) {
        Sudoku game = new Sudoku(); 
        game.setMatrix(new int[9][9]);
        
        SudokuView b1 = new SudokuView(game);
    }
}