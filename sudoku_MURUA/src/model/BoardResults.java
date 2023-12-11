package model;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author milim
 */
public class BoardResults {
    private int[][] board = new int[9][9];
    private boolean generateSudoku = false;
    
    public BoardResults() {
    }
    
    public void generateSudoku() {
        if (!generateSudoku)   sudokuFinished();   
    }

    private boolean sudokuFinished(){
        int[] emptyBoard = searchEmptyPositions();

        //verifica si se lleno el tablero
        if (emptyBoard == null)   return true; 
        

        int row = emptyBoard[0];
        int column = emptyBoard[1];

        ArrayList<Integer> randomNumber = createRandomNumbers();
        
         for (int num : randomNumber) {
            if (validateValues(row, column, num)) {
                board[row][column] = num;

                if (sudokuFinished())  return true;

                board[row][column]= 0; // Si no lleva a una solución, retrocede y prueba otro número
            }
         }

        return false; // No hay solución
    }
    
    private ArrayList<Integer> createRandomNumbers() {
        ArrayList<Integer> number = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            number.add(i);
        }
        Collections.shuffle(number);
        return number;
    }

    
    private int[] searchEmptyPositions() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) return new int[]{i, j};//devuelve las coordenadas de las celdas vacías
                
            }
        }
        return null; // No hay celdas vacías
    }

    private boolean validateValues(int row, int column, int number) {
        // Verifica si el número ya está en la row o column
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == number || board[i][column] == number) return false;
            
        }
       
        //Si el número ya está en el bloque 3x3
        int rowSubMatrix = row - row % 3;
        int columnSubMatrix = column - column % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[rowSubMatrix + i][columnSubMatrix + j] == number) return false;
                
            }
        }
        return true;
    }
    
    public boolean validateInput(Integer fact, int row, int column ){
        if (fact == null)  return false;
        
        try {   
            return fact == board[row][column];
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }
    
    

}
