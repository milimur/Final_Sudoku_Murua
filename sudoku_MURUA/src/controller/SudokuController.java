package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import model.BoardResults;
import model.User;
import view.Difficulty;
import view.EndGame;
import view.VistaSudoku;

/**
 *
 * @author milim
 */
public class SudokuController implements ActionListener,MouseListener {
    private BoardResults modelBoard;
    private User modelUser;
    private VistaSudoku viewBoard;
    private Difficulty viewDifficulty;
    private EndGame viewEndGame;
    private ArrayList<int[]> completedCells = new ArrayList<>(); 
    private int[][] boardResult;
    private int counter = 0;
    private HashSet<JToggleButton> numbersButton = new HashSet<>();
    private int totalCells = 0;
    private int quantityTracks = 0;
    private JToggleButton jTBEasy = new JToggleButton();
    private JToggleButton jTBHalf = new JToggleButton();
    private JToggleButton jTBDifficult = new JToggleButton();
    
    

    public SudokuController(){
        this.modelBoard = new BoardResults();
        this.viewBoard = new VistaSudoku();
        this.viewDifficulty = new Difficulty();
        this.viewEndGame = new EndGame();
        this.modelUser = new User(this.modelBoard);
        
        initComponents();
        
        this.viewBoard.getjTBoardGame().addMouseListener(this);
        
        
        this.viewDifficulty.setVisible(true);
        this.viewBoard.setVisible(false);
        this.viewEndGame.setVisible(false);
        this.modelUser.createFile();
    }
    
    
    public void initComponents(){
        this.viewDifficulty.getjTFName().addActionListener(this);
        this.viewDifficulty.getjTBEasy().addActionListener(this);
        this.viewDifficulty.getjTBHalf().addActionListener(this);
        this.viewDifficulty.getjTBDifficult().addActionListener(this);
        this.viewBoard.getjTBoardGame().setEnabled(true);
        this.viewBoard.getjTBClue().addActionListener(this);
        this.viewBoard.getjTFMistakes().addActionListener(this);
        this.viewEndGame.getjTBContinueGame().addActionListener(this);
        this.viewEndGame.getjTBEndGame().addActionListener(this);
        this.viewBoard.getjTBoardGame().addMouseListener(null);
        this.viewBoard.getjTB1().addActionListener(this);
        this.viewBoard.getjTB2().addActionListener(this);
        this.viewBoard.getjTB3().addActionListener(this);
        this.viewBoard.getjTB4().addActionListener(this);
        this.viewBoard.getjTB5().addActionListener(this);
        this.viewBoard.getjTB6().addActionListener(this);
        this.viewBoard.getjTB7().addActionListener(this);
        this.viewBoard.getjTB8().addActionListener(this);
        this.viewBoard.getjTB9().addActionListener(this);
        numbersButton.add(viewBoard.getjTB1());
        numbersButton.add(viewBoard.getjTB2());
        numbersButton.add(viewBoard.getjTB3());
        numbersButton.add(viewBoard.getjTB4());
        numbersButton.add(viewBoard.getjTB5());
        numbersButton.add(viewBoard.getjTB6());
        numbersButton.add(viewBoard.getjTB7());
        numbersButton.add(viewBoard.getjTB8());
        numbersButton.add(viewBoard.getjTB9());
    }
    
     
    @Override
    public void mouseClicked(MouseEvent event) {
        JTable chart = (JTable) event.getSource();
        int row = viewBoard.getjTBoardGame().getSelectedRow();
        int column = viewBoard.getjTBoardGame().getSelectedColumn();
        
        storeCompletedCells(row,column);
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        
        if (event.getSource() == this.viewDifficulty.getjTFName()) {
            this.viewDifficulty.getjTFName().getText();
            this.viewDifficulty.setFocusableWindowState(true);
            return;
        }
        
        if(viewDifficulty.getFocusableWindowState()){
            viewDifficulty.setFocusableWindowState(false);
            modelUser.getBoard().generateSudoku();
            viewDifficulty.setVisible(false);
            viewBoard.setFocusableWindowState(true);
            viewBoard.setVisible(true);

            boardResult = this.modelUser.getBoard().getBoard();
            int initialTracks = 0;
            
            if(event.getSource() == this.viewDifficulty.getjTBEasy()){
                initialTracks = 45;
                loadBoard(initialTracks);
                this.viewDifficulty.getjTBEasy().setSelected(false);
            }
            else if(event.getSource() == this.viewDifficulty.getjTBHalf()){
                initialTracks = 40;
                loadBoard(initialTracks);
                this.viewDifficulty.getjTBHalf().setSelected(false);
            }else if(event.getSource() == this.viewDifficulty.getjTBDifficult()){
                initialTracks = 35;
                loadBoard(initialTracks);
                this.viewDifficulty.getjTBDifficult().setSelected(false);
            }
        }else if(viewBoard.getFocusableWindowState()){
            numberButtonClicked(event);
            showClue(event);
            
            if(totalCells >= 81){ 
                this.modelUser.writeFile(String.valueOf(counter), this.viewDifficulty.getjTFName().getText(), String.valueOf(quantityTracks));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    return;
                }
                
                viewBoard.setFocusableWindowState(false);
                viewBoard.setVisible(false);
                viewEndGame.setFocusableWindowState(true);
                viewEndGame.setVisible(true);
                this.viewEndGame.getjTPResults().setText(modelUser.readFile());
            }  
        }else{
            if(event.getSource() == this.viewEndGame.getjTBContinueGame()){     
                viewDifficulty.setFocusableWindowState(true);
                viewDifficulty.setVisible(true);
                viewEndGame.setFocusableWindowState(false);
                viewEndGame.setVisible(false);
                this.viewEndGame.getjTBContinueGame().setSelected(false);
                modelBoard = new BoardResults();  // Crear una nueva instancia del modelBoard
                counter = 0;  // Reiniciar el contador de errores
                totalCells = 0;
                quantityTracks = 0;
                viewDifficulty.getjTFName().setText(null);
                restartBoardView();  // Método para reiniciar la vista del tablero si es necesario
            }
            if(event.getSource() == this.viewEndGame.getjTBEndGame()){
                this.modelUser.deleteFile();
                System.exit(0);
            }
            
        }   

    }
    
    private void loadBoard(int initialTracks){
        int counter = 0;     
        
        do{
            int row = (int)(Math.random()*9+0);
            int column = (int)(Math.random()*9+0);
            
            
            if(validateCell(row,column)){
                viewBoard.getjTBoardGame().setValueAt(boardResult[row][column], row, column);
                storeCompletedCells(row,column);
                counter++;
            }
            
        }while(counter < initialTracks);
          
    }
    
    private void numberButtonClicked(ActionEvent event){
        Integer chosenNumber;
        try{
            chosenNumber = Integer.valueOf(getActiveButton().getActionCommand());
            loadNumber(chosenNumber);
        }catch(NotNumberSelectedException e){
            return;
        }
    }
    
    private JToggleButton getActiveButton() throws NotNumberSelectedException{
        for(JToggleButton number : numbersButton){
            if(number.isSelected()) return number;
        } 
        throw new NotNumberSelectedException("No se ha seleccionado un numero para colocar");
    }
    
    private void showClue(ActionEvent event){
        int row;
        int column;
        
        if(event.getSource() == this.viewBoard.getjTBClue()){
            do{
                row = (int)(Math.random()*9+0);
                column = (int)(Math.random()*9+0);
            }while(!validateCell(row,column));
            
            viewBoard.getjTBoardGame().setValueAt(boardResult[row][column], row, column);
            storeCompletedCells(row,column);
            quantityTracks++;
        }
    }
    
    private void loadNumber(Integer number) throws NotNumberSelectedException{
        int row = this.viewBoard.getjTBoardGame().getSelectedRow();//se obtiene la fila seleccionada
        int column = this.viewBoard.getjTBoardGame().getSelectedColumn();//se obtiene la columna seleccionada
        
        if(validateCell(row, column)){
            if(this.modelUser.getBoard().validateInput(number,row,column)){
                this.viewBoard.getjTBoardGame().setValueAt(number, row, column);
                storeCompletedCells(row,column);
            }else{
                counter++;
                this.viewBoard.getjTFMistakes().setText(String.valueOf(counter));
                this.viewBoard.getjTBoardGame().setValueAt(null, row, column);
            }
        }  
        getActiveButton().setSelected(false); 
    }
    
    
    private boolean validateCell(int row, int column){
        for(int[] celda1 : completedCells){
            if(row == celda1[0] && column == celda1[1] ) return false; 
        }
       
        return true;
    }
    
    private void storeCompletedCells(int row, int column){
        int[] cell = {row,column};
        Object inputValue = this.viewBoard.getjTBoardGame().getValueAt(row, column);
        if (inputValue != null && validateCell(row,column)) {   
            completedCells.add(cell);
            totalCells++;
            System.out.println(totalCells);    
        }
    }

   
    private void restartBoardView() {
        for (int row = 0; row < viewBoard.getjTBoardGame().getRowCount(); row++) {
            for (int column = 0; column < viewBoard.getjTBoardGame().getColumnCount(); column++) {
                viewBoard.getjTBoardGame().setValueAt(null, row, column); // Eliminar el contenido de todas las celdas
            }
        }

        viewBoard.getjTFMistakes().setText("0"); // Reiniciar jTextFile a 0
        cleanCompletedCells();
    }

    private void cleanCompletedCells() {
        completedCells.clear();
    }

    
    //metodos del mouseListener que no uso pero los tengo que importar
    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
   
}
