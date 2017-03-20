package AI_Logic;

import java.util.Stack;
import main.Const;

public abstract class AI {
    Stack<MOVEMENT> AI_Steps;   //Stack di istruzioni di movimento

    public AI(){
        AI_Steps = Const.defaultMotionsOrder();
    }
    
    //Ottiene lo step successivo (pop). A stack terminato -> Aggiunge un nuovo Set di istruzioni
    public MOVEMENT nextStep(){
        if (AI_Steps.empty()) AI_Steps = Const.defaultMotionsOrder();        
        return AI_Steps.pop();
    }
}
