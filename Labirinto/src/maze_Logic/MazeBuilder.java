/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze_Logic;

import java.util.*;
import java.util.Random;
/**
 *
 * @author Linguistico
 */
public class MazeBuilder {
    
            //quadrato
    public static Maze build(/*parametri utente*/ Integer length){
 
    
    Random ra = new Random();
    
    
        LinkedList<ArrayList<MazeCell>> mazeStruct = new LinkedList<>();
        
        for(int j = 0; j < length; j++){            
            //creo un arraylist
            ArrayList<MazeCell> row = new ArrayList<>();
            
            //riempio una row
            for(int i = 0; i < length; i++){    
                int r =ra.nextInt(100);
                int[] coordinate = new int[2];//x-y
                CELL_CONTENT contenuto;//= MazeCell_content.NULL;
                if (r>0 && r<30)                                           
                    contenuto=CELL_CONTENT.MURO;
                    
                else
                    contenuto=CELL_CONTENT.NULL;
                
                coordinate[0] = i+1;//x
                coordinate[1] = j+1;//y
                //coordinate + contenuto
                MazeCell c = new MazeCell(coordinate, contenuto);  
                row.add(c);
            }
            
            //svuota 
            //if
            
            //aggiunge arraylist
            mazeStruct.add(row);
            
                
        }
        return new Maze(mazeStruct);
    }
}
