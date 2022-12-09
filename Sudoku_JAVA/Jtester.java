package Sudoku_JAVA;  
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


 public class Jtester {
    private Sudoku sudoku;
    private Sudoku sudoku2;

    @BeforeEach
    public void setup() {
        sudoku = new Sudoku();
        sudoku2 =new Sudoku();
       
    }
    @AfterEach
    public void tearDown() {
        sudoku = null;
        sudoku2 = null;
    }

    @Test
    public void testSolveEmptyBoard() {
        sudoku = new Sudoku();
        int[][] grid = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        
        sudoku.setMatrix(grid);
        Assertions.assertTrue(sudoku.solve(), "The board is not solved");
    }

    @Test
   public void testSolveBoardFromFig1() {
   Sudoku sudoku = new Sudoku();
   Sudoku sudoku2 = new Sudoku();
        int[][] grid = new int[][]{
                {0, 0, 8, 0, 0, 9, 0, 6, 2},
                {0, 0, 0, 0, 0, 0, 0, 0, 5},
                {1, 0, 2, 5, 0, 0, 0, 0, 0},
                {0, 0, 0, 2, 1, 0, 0, 9, 0},
                {0, 0, 0, 0, 0, 0, 6, 0, 0},
                {6, 0, 0, 0, 0, 0, 0, 2, 8},
                {4, 1, 0, 6, 0, 8, 0, 0, 0},
                {8, 0, 0, 0, 3, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 4, 0, 0}
        };
        int[][] gridSolved = new int[][]{
                {5, 4, 8, 1, 7, 9, 3, 6, 2},
                {3, 7, 6, 8, 2, 4, 9, 1, 5},
                {1, 9, 2, 5, 6, 3, 8, 7, 4},
                {7, 8, 4, 2, 1, 6, 5, 9, 3},
                {2, 5, 9, 3, 8, 7, 6, 4, 1},
                {6, 3, 1, 9, 4, 5, 7, 2, 8},
                {4, 1, 5, 6, 9, 8, 2, 3, 7},
                {8, 6, 7, 4, 3, 2, 1, 5, 9},
                {9, 2, 3, 7, 5, 1, 4, 8, 6}
        };

    
        sudoku.setMatrix(grid);
        sudoku2.setMatrix(gridSolved);
        sudoku.solve();
        
        Assertions.assertArrayEquals(sudoku.getMatrix(), gridSolved, "The board is not solved correctly");
    }

    @Test
    public void testImpossibleBoard(){
        int[][] grid = new int[][]{
                {1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 5},
                {0, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        sudoku.setMatrix(grid);
        sudoku.solve();
        assertFalse(sudoku.solve(), "The board is broken af");
    }



  


    
}
