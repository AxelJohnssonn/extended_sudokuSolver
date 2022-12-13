package Sudoku_JAVA;  
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;


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
                {0, 5, 0, 0, 0, 0, 6, 0, 0},
                {6, 0, 0, 0, 0, 0, 0, 2, 8},
                {4, 1, 0, 6, 0, 8, 0, 0, 0},
                {8, 6, 0, 0, 3, 0, 1, 0, 0},
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
        
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                System.out.println(sudoku.getMatrix()[i][j]);
                Assertions.assertEquals(gridSolved[i][j], sudoku.getMatrix()[i][j], "The board is fuuucked");
            }
        }
    }
    

    
      
      

    @Test
    @Timeout(3)
    public void testImpossibleBoard() throws InterruptedException {
        Assertions.assertTimeout(Duration.ofSeconds(10),()->{
            getValue();
        });
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
    String getValue() throws InterruptedException{
        Thread.sleep(10000);
        return "oops";
    }
    
    @Test
    public void testSetSquare(){
        sudoku.set(9,5,5);
        assertEquals(9, sudoku.getMatrix()[5][5],"Wrong value when adding digit into matrix");
    }

    @Test
    public void testRemoveSquare(){
        sudoku.set(9, 5, 5);
        assertEquals(9, sudoku.getMatrix()[5][5],"Wrong value when adding digit into matrix");
        sudoku.remove(5, 5);
        assertEquals(0, sudoku.getMatrix()[5][5], "Did not remove the digit");
    }

    @Test
    public void testClearGrid(){
        sudoku.set(9, 5, 5);
        assertEquals(9, sudoku.getMatrix()[5][5],"Wrong value when adding digit into matrix");
        sudoku.clear();
        assertEquals(0, sudoku.getMatrix()[5][5], "Did not clear the digit");
    }
    @Test 
    public void checkIfEmptySquare(){
        sudoku.set(1, 0, 0);
        assertEquals(1, sudoku.getMatrix()[0][0], "The board is empty");
        sudoku.clear();
        assertTrue(sudoku.checkIfEmpty(0, 0), "The board is not empty");
    }
    @Test
    public void checkLegalSquare(){
        sudoku.set(1,0,0);
        assertTrue(sudoku.legal(1,0,0),"The board is not legal");
        assertFalse(sudoku.legal(1,0,1),"The board is legal");
        sudoku.clear();
        assertTrue(sudoku.legal(1,0,0),"The board is not legal");
    }

    @Test
    public void testAddWrongNumberToSquare(){
        sudoku.set(10, 0, 0);
        assertNotEquals(10, sudoku.getMatrix()[0][0], "The board is not legal");

    }


  


    
}
