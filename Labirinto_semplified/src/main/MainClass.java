package main;

import maze_Logic.*;

public class MainClass {

    public static void main(String[] args) throws InterruptedException {    
        int length;
        while(true){
            Display.writeLine("inserire la lunghezza laterale del labirinto\n");
            try {
                length = Integer.parseInt(Display.ReadLine());
                break;
            }
            catch(NumberFormatException e){
                Display.clear();
                Display.writeLine("Non è un numero!");
            }
        }

        Display.clear();
        Maze g = new Maze(length);
        String s = g.run() ? "BRAVO'!":"nope :/";
        Display.writeLine(s);

        Display.ReadLine();        
        //Display.clear();            
        
        main(args);//(:[pigrizia]:)
    }
}
