package edu.duke.ece651.teamX.shared;
import java.util.Random;
public class DiceGame implements Determinant {
    Random rand = new Random();
    /**
     * determine which player has larger dice count
     * @param attackerDice number of sides of the attacker dice sides
     * @param defenderDice number of sides of the defender dice sides
     * @return boolean showing if the attacker has larger dice count
     */
    @Override
    public boolean determine(Unit attacker, Unit defender, int attackerDice, int defenderDice) {
        return getDiceCount(attackerDice) > getDiceCount(defenderDice);
    }

    /**
     * get the random dice count as dice game result
     * @param diceSideNumber number of sides of a dice
     * @return integer of the number on one side of the dice
     */
    public int getDiceCount(int diceSideNumber) {
        return rand.nextInt(diceSideNumber);
    }
    
}
