package edu.duke.ece651.teamX.shared;
import java.util.Random;
public class DiceGame implements Determinant {
    Random rand = new Random(); 
    @Override
    public boolean determine(Unit a, Unit b) {
        int aValue = rand.nextInt(19);
        int bValue = rand.nextInt(19);
        return aValue>bValue;
    }
    
}
