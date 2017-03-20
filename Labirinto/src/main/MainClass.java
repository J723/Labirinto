package main;

import AI_Logic.MOVEMENT;
import java.util.Iterator;
import java.util.Stack;
import maze_Logic.*;

public class MainClass {

    public static void main(String[] args) {    
        Stack<MOVEMENT> steps = new Stack<>();
        steps = Const.defaultMotionsOrder();
        
        for (MOVEMENT m : steps)
            System.out.println(m.toString());
        
        Stack<MOVEMENT> wayBack = new Stack<>();
        for (MOVEMENT m : steps) {
            wayBack.push(m.getOpposite());
        }
        
        for (MOVEMENT m : wayBack)
            System.out.println(m.toString());
    }
}
