package main;

import java.util.Stack;
import AI_Logic.*;

public final class Const {

	private Const(){}
        
	//Shapes:
        //
	public static char Shape_Rat = 'y';
	public static char Shape_Formaggio = 'm';
	
	public static char Shape_Wall = '█';
	public static char Shape_Path = ' ';
        public static char Shape_Empty = ' ';
        public static char Shape_Track = '*';
        
	//Rat Logic:
        //
	public static Stack<MOVEMENT> defaultMotionsOrder(){
		
		Stack<MOVEMENT> motions = new Stack<>();
		
		motions.push(MOVEMENT.UP);
		motions.push(MOVEMENT.DOWN);
		motions.push(MOVEMENT.LEFT);
		motions.push(MOVEMENT.RIGHT);
		
		return motions;
	}       
        
}
