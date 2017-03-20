package rat_Logic;

import java.util.*;
import maze_Logic.MazeCell;
import AI_Logic.MOVEMENT;

public class RatPath {
		
    public boolean blindAlley; //vicolo cieco
    Stack<MOVEMENT> steps;
    LinkedList<MazeCell> cells;
    public LinkedList<RatPath> crossroad;

    public RatPath(MazeCell start, MOVEMENT firstStep){        
        steps = new Stack<>(); 
        cells = new LinkedList<>();
        cells.add(start);
        steps.push(firstStep);
        blindAlley = false;//all'inizio ogni strada non è vicolo cieco
    }
    
    public void addStep(MazeCell nextCell, MOVEMENT nextStep){
        //esclude due celle uguali
        if (cells.contains(nextCell)) return;
            
        cells.add(nextCell);
        steps.push(nextStep);
    }
    
    public void setAsBlindAlley(){
        blindAlley = true;
    }
    
    public boolean contain(MazeCell m){
        return cells.contains(m);
    }

    //converte ogni step in un suo opposto e restituisce la strada di ritorno
    public Stack<MOVEMENT> goBack(){
        
        Stack<MOVEMENT> wayBack = new Stack<>();
        /*PIU EFFICIENTE
        steps.stream().forEach((step) -> {
            wayBack.add(step.getOpposite());
        });
        */    
        
        //PIU COMPRENSIBILE
        for (MOVEMENT m : steps) wayBack.push(m);        
        return wayBack;
    } 
}
