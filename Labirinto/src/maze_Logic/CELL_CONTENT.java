package maze_Logic;

import main.Const;

public enum CELL_CONTENT {
    RATTO,
    FORMAGGIO,
    MURO,
    NULL,
    VICOLO,
    TRACCIA;
    
    private char shape;
    
    static{
        RATTO.shape = Const.Shape_Rat;
        FORMAGGIO.shape = Const.Shape_Formaggio;
        
        MURO.shape = Const.Shape_Wall;
        NULL.shape = Const.Shape_Empty;
        VICOLO.shape = Const.Shape_Empty;
        TRACCIA.shape = Const.Shape_Track;
    }
    
    public char getShape(){
        return shape;
    } 
}
