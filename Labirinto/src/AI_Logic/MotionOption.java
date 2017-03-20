package AI_Logic;

public enum MotionOption {
    LEFT,
    RIGHT,
    UP,
    DOWN;
    
    private MotionOption opposite;

    static {
        LEFT.opposite = RIGHT;
        RIGHT.opposite = LEFT;
        UP.opposite = DOWN;
        DOWN.opposite = UP;
    }
    
    public MotionOption getOpposite(){
        return opposite;
    }
}
