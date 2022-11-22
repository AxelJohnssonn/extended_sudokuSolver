package Sudoku_JAVA;
public class SudokuMain{
    public static void main(String[] args) {
        //Testar att skapa en bräda med 9x9 
        int[][] board = new int[9][9];
        for(int r=0; r<9; r++){
            for(int c=0; c<9; c++){
                board[r][c] = c+r; 
            }
        }
        Sudoku game = new Sudoku(); 
        game.init(board);
        board = game.getBoard();

        for(int r=0; r<9; r++){
            for(int c=0; c<9; c++){
                System.out.print(board[1][c]); 
            }
            System.out.println(board[r][1]);
        }
    }
}