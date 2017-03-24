package rat_Logic;

import java.util.*;
import AI_Logic.*;
import maze_Logic.MazeCell;
import maze_Logic.CELL_CONTENT;

/*
 * EREDITATI:
 * VAR: Stack<Movement> AI_Steps;   //Stack di istruzioni di movimento
 * FUN: public MOVEMENT nextStep()  //Ottiene lo step successivo (pop). A stack terminato -> Aggiunge un nuovo Set di istruzioni
*/
public class Rat extends AI{
    
    public MazeCell currentPos;
    private final MazeCell lastPos;
    private final Stack<MazeCell> toVisit;
    //private Stack<MOVEMENT> wayBack;// <----TODO----Può tornare utile per tornare ad un incrocio

    public Rat(MazeCell spawnPoint, MazeCell[] aroundTheRat){
        currentPos = spawnPoint;
        lastPos = currentPos;
        toVisit = new Stack<>();
        //wayBack = new Stack<>();//----TODO----
    }

    //ritorna la posizione del ratto dopo lo spostamento
    public MazeCell move(MazeCell[] aroundTheRat){        
        MazeCell nextCell;
        
        /*
        //step 0: controllo se sta andando ad un checkpoint (ultimo incrocio non visitato) -> pop da toVisit
        if (wayBack.empty()){
            nextCell = wayBack.pop();
        }          
        else{step 1-2}
        */
        
        //Step 1: cerca la fine del labirinto nelle vicinanze
        for (MazeCell m : aroundTheRat)
            if (m.isExitCell()){
                currentPos = m;
                return m;
            }                       

        //Step 2: sceglie la prossima cella        
        nextCell = calcNextCell(aroundTheRat);
        
        //Step 2.5: Se è uguale alla precedente o ad una traccia -> torna all'ultimo "ToVisit" (pop dallo stack)
        if (nextCell == lastPos || nextCell.contains(CELL_CONTENT.TRACCIA))
            nextCell = toVisit.pop();
            //TODO --- torna all'ultimo checkpoint senza teletrasporto        
        
        //step 3: capisce se è appena passato per un incrocio con celle non ancora visitate e le pusha in toVisit
        isToVisit(nextCell, aroundTheRat);           
        
        //Step 4: sposta fisicamente il ratto
        currentPos = nextCell;
        return currentPos;  
       
        //TODO:
        //Step 5: cerca di prevedere i prossimi vicoli
        /* Se una strada confina con un bivio:
         * se il bivio termina solo in vicoli allora la strada è vicolo
         * altrimenti se una o più strade non sono vicoli e terminano in bivi: ripeti ricorsivamente
         * se una strada non termina con bivio e non è vicolo allora [strada prima] non è vicolo
         */
    }
    
    //ritorna la cella immediatamente più corretta:
    //sceglie dove andare -> in base all'istruzione successiva e alla priorità:
    //1) cella vuota 
    //2) cella tracciata
    //3) cella precedente
    //4) [esclude del tutto i muri]
    private MazeCell calcNextCell(MazeCell[] aroundTheRat){

        int value = countCell(aroundTheRat, CELL_CONTENT.TRACCIA, true),  trCells_Index = (value <= 0) ? -1 : value;
        return calcNextCell_Rec(aroundTheRat, getCellFromStep(nextStep(), aroundTheRat), false, trCells_Index);        
    }
    
    private MazeCell calcNextCell_Rec(MazeCell[] aroundTheRat, MazeCell nextCell, boolean lastStepVer, int trackedCellsToVer){
        /*
        //controlla cella vuota - esclude lo stack
        for (MazeCell m : aroundTheRat)
            if (m.contains(CELL_CONTENT.NULL))
                return m;
        */

        //controllando una cella vuota
        if (nextCell.contains(CELL_CONTENT.NULL)) return nextCell;
        
        //controllando celle tracciate 
        if (nextCell.contains(CELL_CONTENT.TRACCIA)){
            if (trackedCellsToVer > 0) trackedCellsToVer--;
            else if (trackedCellsToVer == 0) return nextCell;
        }
        
        //controllando l'ultima posizione
        if (nextCell == lastPos){
            if (!lastStepVer && trackedCellsToVer == 0) lastStepVer = true;
            else if (lastStepVer && trackedCellsToVer == 0) return lastPos;
        }
        
        //se altro
        nextCell = getCellFromStep(nextStep(), aroundTheRat);
        return calcNextCell_Rec(aroundTheRat, nextCell, lastStepVer, trackedCellsToVer);        
    }
    
    //verifica che la cella da cui si è mosso il ratto avesse delle alternative e le memorizza
    private boolean isToVisit(MazeCell nextCell, MazeCell[] aroundTheRat){
        boolean pushed = false;//indica se ha aggiunto
        int possiblePaths = countCell(aroundTheRat, CELL_CONTENT.MURO, false);//conta le celle diverse da un muro
        if (possiblePaths > 2)
           for (MazeCell m : aroundTheRat)
               if (m != nextCell && m.contains(CELL_CONTENT.NULL)){
                   toVisit.push(m);
                   pushed = true;
               }
        return pushed;            
    }
    
    //conta  gli oggett uguali/disuguali nelle celle circostanti
    private int countCell(MazeCell[] aroundTheRat, CELL_CONTENT filter, boolean equals){
        int r = 0;
        for (MazeCell m : aroundTheRat){
            if (equals){
                if (m.content == filter) r++;
            }
            else if (m.content != filter) r++;
        }
        return r;
    }   
    
    //traduce lo step successivo (MOVEMENT) nella cella relativa, dato l'intorno
    private MazeCell getCellFromStep(MOVEMENT step, MazeCell[] around){
        MazeCell c = currentPos;
        switch (step){  
            
            case UP:   
                c = around[1];
                break;
            
            case DOWN:
                c = around[3];
                break;
                
            case LEFT:
                c = around[2];
                break;
                
            case RIGHT:
                c = around[0];
                break;
        }
        return c;
    }
    
}
