package maze_Logic;

import java.util.*;
import main.Const;

import rat_Logic.*;
import main.Display;

public class Maze {
	
    public MazeCell[][] map;
    public MazeCell fromage;
    public Rat rat;

    public Maze(LinkedList<ArrayList<MazeCell>> maze){
        //rimonta la lista "maze" in MAP		
        //TODO 
        int length = map.length;
        map = new MazeCell[length+2][length+2];
        ListIterator<ArrayList<MazeCell>> iter = maze.listIterator();
        for (int i = 0; i < length+2; i++) {
            for (int j = 0; j < length+2; j++) {
                int[] coordinate = new int[2];//x-y
                coordinate[0] =i;
                coordinate[1] =j;
                map[i][j]= new MazeCell(coordinate, CELL_CONTENT.MURO);
            }
        }
        while(iter.hasNext())
        {
            Iterator<MazeCell> itInternal = iter.next().iterator();
            while(itInternal.hasNext())
            {
                MazeCell mc = itInternal.next();
                map[mc.x][mc.y]= mc;
            }
        }
        fromage = find(CELL_CONTENT.FORMAGGIO);
    }

    //disegna finche non trova il formaggio
    public boolean run(){
        if (rat.currentPos == fromage) return true;           
        rat.currentPos.content = CELL_CONTENT.TRACCIA;
        rat.move(aroundTheRat(rat)).content = CELL_CONTENT.RATTO;
        draw();
        return run();
    }   
    
    //Disegna il labirinto riga per riga
    private void draw(){
        Display.clear();
        for (MazeCell[] row : map) 
        {
            String r = "";
            for (MazeCell c : row) r += c.content.getShape();
            Display.writeLine(r);
        }
    }
    
    //ritorna l'intorno del ratto
    private MazeCell[] aroundTheRat(Rat r){
    return lookAround(r.currentPos);
    }
    
    //FUNZIONI GENERICHE
    //
    //Ritorna le quattro celle a diretto contatto col parametro [far puntare ad ogni cella quelle intorno mi sembrava pesante]
    private MazeCell[] lookAround(MazeCell c){
        MazeCell[] around = new MazeCell[]{
            
        map[c.x + 1][c.y], //Dx
        map[c.x - 1][c.y], //Sx
        map[c.x][c.y - 1], //Up
        map[c.x][c.y + 1] //Dw
                
        };
        return around;
    }

    //Ritorna la prima cella contenente un elemento nel labirinto con contenuto == element
    private MazeCell find(CELL_CONTENT element){
        for (MazeCell[] mr : map){
            for (MazeCell mc : mr)
                if (mc.contains(element))
                    return mc;
        }           
        return null; //impossibile   
    }
}
