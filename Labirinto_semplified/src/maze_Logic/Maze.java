package maze_Logic;

import java.util.*;
import java.util.concurrent.TimeUnit;

import rat_Logic.*;
import main.Display;

public class Maze {
	
    public MazeCell[][] map;
    public MazeCell fromage;
    public Rat rat;

    public Maze(int length){    
        Random r = new Random();
        
        while (true){
            //compone il labirinto
            LinkedList<ArrayList<MazeCell>> maze = build(length);
            
            //rimonta la lista "maze" in MAP	
            map = new MazeCell[length+2][length+2];//margini
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
            
            MazeCell m = map[r.nextInt(map.length)][r.nextInt(map[0].length)];
            //piazza il formaggio
            while(true){
                if (m.contains(CELL_CONTENT.NULL)) break;
                else m = map[r.nextInt(map.length)][r.nextInt(map[0].length)];
            }   
            m.content = CELL_CONTENT.FORMAGGIO;
            fromage = m;

            //piazza il ratto
            while(true){
                if (m.contains(CELL_CONTENT.NULL)) break;
                else m = map[r.nextInt(map.length)][r.nextInt(map[0].length)];
            }   
            m.content = CELL_CONTENT.RATTO;
            rat = new Rat(m, lookAround(m));
            
            if (TryToRun()) break;
        }        
    }   
    
    private boolean TryToRun(){
        try{
            return TryToRun(rat.currentPos, fromage);
        }catch(Exception e){
            return false;
        }
    }
    
    //copia labirinto e ratto e cerca di trovare una soluzione al labirinto
    //ritorna false se ha percorso tutto il labirinto possibile senza trovare una soluzione -> stackOverfloqException
    private boolean TryToRun(MazeCell ratPos, MazeCell cheesePos){              
        
        MazeCell ratCell = lookAround(find(CELL_CONTENT.RATTO))[0];
        Rat r = new Rat(ratCell, lookAround(ratCell));
        
        try{
            if (rat.currentPos == fromage) return true;        
            rat.currentPos.content = CELL_CONTENT.TRACCIA;
            rat.move(aroundTheRat(rat)).content = CELL_CONTENT.RATTO;            
            return TryToRun(ratPos, cheesePos);
        }
        catch (Exception e){
            return false;
        }
        finally{
            //resetta il labirinto
            for (MazeCell[] row : map)
                for (MazeCell mc : row){
                    if (mc.contains(CELL_CONTENT.TRACCIA) || mc.contains(CELL_CONTENT.RATTO) || mc.contains(CELL_CONTENT.FORMAGGIO))
                        mc.content = CELL_CONTENT.NULL;
                    if (mc == ratPos)
                        mc.content = CELL_CONTENT.RATTO;
                    if (mc == cheesePos)
                        mc.content = CELL_CONTENT.FORMAGGIO;                    
                }    
            rat.currentPos = ratPos;            
        }
    }
    
    //disegna finche non trova il formaggio
    public boolean run() throws InterruptedException{
        if (rat.currentPos == fromage) return true;       
        
        rat.currentPos.content = CELL_CONTENT.TRACCIA;
        rat.move(aroundTheRat(rat)).content = CELL_CONTENT.RATTO;
        draw();
        
        try{
            TimeUnit.MILLISECONDS.sleep(500);
        }
        catch(InterruptedException e){}
        
        //ON DEBUG
        //Display.ReadLine();
        //END
        
        return run();
    }   
    
    //Disegna il labirinto riga per riga
    private void draw() throws InterruptedException{
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
    
    //monta la struttura del labirinto
    public static LinkedList<ArrayList<MazeCell>> build(/*parametri utente*/ Integer length){
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
            
            //-----TODO-----svuota (lab intelligente)
            
            //aggiunge arraylist
            mazeStruct.add(row);   
        }
        return mazeStruct;
    }
}
