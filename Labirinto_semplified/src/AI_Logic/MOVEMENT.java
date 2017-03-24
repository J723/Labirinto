package AI_Logic;

public enum MOVEMENT {
    LEFT,
    RIGHT,
    UP,
    DOWN;
    
    private MOVEMENT opposite;

    static {
        LEFT.opposite = RIGHT;
        RIGHT.opposite = LEFT;
        UP.opposite = DOWN;
        DOWN.opposite = UP;
    }
    
    public MOVEMENT getOpposite(){
        return opposite;
    }
}
