package AI_Logic;

import java.util.*;

//IL COSTRUTTORE NON INIZIALIZZA - preferisco inizializzare i set di istruzioni solo quando mi servono (metodi: New_[setname]Set)
public class PunchCard {
	
    protected ArrayList<Stack<MotionOption>> motionSet;	//Potrebbe avere differenti set di istruzioni di movimento -> perciò lista

    public MotionOption getMotion_NextStep(Integer setNumber){
        return motionSet.get(setNumber).pop();
    }

    public boolean New_MotionSet(Stack<MotionOption> instructions){
        if (motionSet == null)
            motionSet = new ArrayList<Stack<MotionOption>>();

        motionSet.add(instructions);
        return true;
    }
}
