package maze_Logic;

import main.Const;

public class MazeCell {
	
	//coordinate
	Integer x;
	Integer y;
	
	public CELL_CONTENT content;
        
        //indice alle celle circostanti
        /*
        public MazeCell cellLeft;
        public MazeCell cellRight;
        public MazeCell cellUp;
        public MazeCell cellDown;
        */
	
	public MazeCell(int[] coordinates, CELL_CONTENT content){
		this.x = coordinates[0];
		this.y = coordinates[1];
		this.content = content;
	}
        
        public boolean equals(MazeCell cellToCompare){            
            return x == cellToCompare.x && cellToCompare.y == y;
        }
	
	public boolean isExitCell(){
            return content == CELL_CONTENT.FORMAGGIO;
	}
	
	public boolean isEmpty(){
	    return content == CELL_CONTENT.NULL;
	}
        
        public boolean contains(CELL_CONTENT obj){
            return content == obj;
        }
	
	/*
	 * QUELLO CHE VI DICEVO IERI SULL'ADATTABILITA' DEL CODICE ALLE MODIFICHE:
	 * 
	public boolean itExplode(){
		if (content == CELL_CONTENT.BOMBAAAAAAAA)
			return true;
		else
			return false;
	}
	*/
}
