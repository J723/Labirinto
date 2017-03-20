package rat_Logic;

import java.util.*;
//import main.Const;
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
    private MazeCell lastPos;
    private LinkedList<RatPath> ratMap;//contiene le singole strade
    private Stack<MOVEMENT> wayBack;

    public Rat(MazeCell spawnPoint, MazeCell[] aroundTheRat){
        currentPos = spawnPoint;
        lastPos = currentPos;
        ratMap = new LinkedList<>();
        wayBack = new Stack<>();
        firstMap(aroundTheRat);
    }
    
    //setta la prima strada (prevede i vicoli)
    private void firstMap(MazeCell[] aroundTheRat){
        //stabilisce il primo movimento in base alla prima cella adiacente libera |LE POSIZIONI SONO STABILITE IN MAZE.lookAround()|
        MOVEMENT m = MOVEMENT.RIGHT;
        for (int i = 0; i < aroundTheRat.length; i++){
            if (aroundTheRat[i].isEmpty())
                switch(i){
                    case 0:
                        m = MOVEMENT.RIGHT;
                        break;
                    case 1:
                        m = MOVEMENT.LEFT;
                        break;
                    case 2:
                        m = MOVEMENT.UP;
                        break;
                    case 3:
                        m = MOVEMENT.DOWN;
                        break;
                }
        }
        ratMap.add(new RatPath(currentPos, m));
    }

    //date le celle attorno al ratto estrae un istruzione di movimento e verifica che sia svolgibile -> poi compie un passo e lo "logga" sulla strada
    //ritorna la posizione del ratto dopo lo spostamento
    public MazeCell move(MazeCell[] aroundTheRat){
        MOVEMENT step;  //successivo
        MazeCell nextCell; //cella di destinazione relativa allo step
        int possiblePaths; //strade possibili dalla posizione corrente (dove non c'è muro) -> per il calcolo della destinazione
        
        //step 0: controllo se sta tornando indietro
        if (!wayBack.empty()){
            currentPos = getCellFromNextStep(wayBack.pop(), aroundTheRat);
            return currentPos;
        }           
        
        //Step 1: cerca la fine del labirinto nelle vicinanze
        for (MazeCell m : aroundTheRat)
            if (m.isExitCell()){
                currentPos = m;
                return m;
            }    
        
        //Step 2: determina la cella di destinazione adiacente più adatta
        possiblePaths = countThatCells(aroundTheRat, CELL_CONTENT.MURO, false);
        step = nextStep();
        nextCell = getCellFromNextStep(step, aroundTheRat);

        switch (possiblePaths) {
            case 1: //torna indietro
                //prima strada -> nuova strada (verrà marcata come vicolo dal controllo finale)
                if (getCurrPath() == null) ratMap.add(new RatPath(currentPos, step));
                else wayBack = getCurrPath().goBack();
                return move(aroundTheRat);
                
            case 2: //va avanti
                //cicla per un passo avanti
                while (nextCell.content == CELL_CONTENT.MURO || nextCell == lastPos){
                    step = nextStep();
                    nextCell = getCellFromNextStep(step, aroundTheRat);
                }   
                break;
                
            default: //è in un bivio|incrocio -> esclude anche vicoli ciechi
                //cicla evitando: di tornare inietro, vicoli ciechi o muri e se possibile di ripassare sulle proprie traccie
                int v = countThatCells(aroundTheRat, CELL_CONTENT.TRACCIA, true),  trackIndex = (v <= 0) ? -1 : v;                
                for (boolean lastStepVerified = false; !nextCell.isEmpty() || nextCell == lastPos || getPath(nextCell).blindAlley;){
                    //considera i vicoli ciechi solo dopo che tutte le strade marcate sono state prese in considerazione
                    if (nextCell == lastPos && trackIndex < 0 && !getPath(nextCell).blindAlley){
                        if (!lastStepVerified) lastStepVerified = true;
                        else {
                            wayBack = getCurrPath().goBack();
                            return move(aroundTheRat);
                        }
                    }
                    else if (nextCell.content == CELL_CONTENT.TRACCIA && !getPath(nextCell).blindAlley){
                        if (trackIndex > 0) trackIndex--;
                        else break;
                    }
                    step = nextStep();
                    nextCell = getCellFromNextStep(step, aroundTheRat);
                }
                break; 
        }        
        
        //Step 3: Logga la nuova cella di destinazione e sposta fisicamente il ratto
        if (possiblePaths == 2) getCurrPath().addStep(nextCell, step);
        else if (getPath(nextCell) == null) 
            ratMap.add(new RatPath(nextCell, step)); //aggiunge una nuova strada se si è ad un bivio e nextCell !€ a nessuna strada
        //spostamento
        currentPos = nextCell;
        return currentPos;  
        
        //Step 4: cerca di prevedere i prossimi vicoli
        //TODO:
        /* Se una strada confina con un bivio:
         * se il bivio termina solo in vicoli allora la strada è vicolo
         * altrimenti se una o più strade non sono vicoli e terminano in bivi: ripeti ricorsivamente
         * se una strada non termina con bivio e non è vicolo allora [strada prima] non è vicolo
         */
    }    
    
    //conta  gli oggett uguali/disuguali nelle celle circostanti
    private int countThatCells(MazeCell[] aroundTheRat, CELL_CONTENT filter, boolean equals){
        int r = 0;
        for (MazeCell m : aroundTheRat){
            if (equals)
                if (m.content == filter) r++;
            else
                if (m.content != filter) r++;
        }
        return r;
    }    
    
    //ottiene la strada che contiene quella cella
    private RatPath getPath(MazeCell c){
        for (RatPath p : ratMap)
            if (p.contain(c)) return p;
        return null;
    }
    
    //ottiene la strada corrente
    private RatPath getCurrPath(){
        return getPath(currentPos);
    }
    
    //traduce lo step successivo (MOVEMENT) nella cella relativa, dato l'intorno
    private MazeCell getCellFromNextStep(MOVEMENT step, MazeCell[] around){
        MazeCell c = currentPos;
        switch (step){  
            
            case UP:   
                c = around[2];
                break;
            
            case DOWN:
                c = around[3];
                break;
                
            case LEFT:
                c = around[1];
                break;
                
            case RIGHT:
                c = around[0];
                break;
        }
        return c;
    }
    
}
