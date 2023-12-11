/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author milim
 */
public class User {
    private BoardResults board;
    
    public User(BoardResults board1) {
        this.board = board1;
    }
    
     File file1;
     
    
    public void createFile(){
        file1 = new File("C:\\Users\\milim\\OneDrive\\Documentos\\laborarorioII\\sudoku_MURUA\\src\\resources\\userData.txt");

            try {
                if (file1.exists()) {
                    System.out.println("El archivo ya existe");
                } else {
                    if (file1.createNewFile()) {
                        System.out.println("Se creo el archivo");
                    }
                }
            } catch (IOException ex) {
            }
        
    }
    
    public void writeFile(String errores,String name, String pistas){
        try{
            FileWriter write = new FileWriter(file1,true);
            write.write(name+ " errores: "+errores +" pistas: "+pistas+"\n");
            write.close();
            //System.out.println("se ha añadido correctamente");
        }catch(IOException ex){
            ex.printStackTrace(System.out);
        }
    }
    
    public String readFile(){
        StringBuilder contenido = new StringBuilder();
        FileReader read = null;
        BufferedReader lectura = null;
        
        try{
            read = new FileReader(file1);
            lectura = new BufferedReader(read);
            String line;
            
            while((line = lectura.readLine()) != null){
                contenido.append(line).append("\n");
                System.out.println(line);
            }
            
            
        }catch(IOException ex){
            ex.printStackTrace(System.out);
            
        }finally{
            try{
               if( null != read){
                  read.close();
               }
               else{
                   lectura.close();
               }
            }catch (Exception e2){
               e2.printStackTrace();
            }
        }
        return contenido.toString();
    }
    
    
    public void deleteFile(){
        if(file1.delete()){
            System.out.println("se ha eleminado el archivo");
        }else{
            System.out.println("error al eliminar");
        }
    
    }

    public BoardResults getBoard() {
        return board;
    }

    public void setBoard(BoardResults board) {
        this.board = board;
    }
    
    
}
