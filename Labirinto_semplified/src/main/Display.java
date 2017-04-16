/*
 * Questa classe serve nel caso si decida di utilizzare altri metodi di output
 */

package main;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Scanner;

public final class Display {
    
    private Display(){}//static class

    public static boolean write(Object text){
        try{
            System.out.print(text);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }  
    
    public static boolean writeLine(Object text){
        try{
            System.out.println(text);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
    
    public static String ReadLine(){
        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }
    
    public static boolean clear() throws InterruptedException {
        try{            
            Robot r = new Robot();
            
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_L);
            r.keyRelease(KeyEvent.VK_L);
            r.keyRelease(KeyEvent.VK_CONTROL);
            Thread.sleep(5);//serve per non incasinare
            return true;
        }   
        catch(AWTException e){
            return false;
        }
    }
}
