package edu.duke.ece651.teamX.shared;

import java.util.Iterator;

public abstract class RuleChecker {

    private RuleChecker next;

    /**
     * Check if the current territory has enough units to move
     *
     * @param territory initial territory
     * @param moveUnitsCount units the user want to move
     * @return true if there is enough units, false if not.
     */
    public boolean isValidUnitCount(Territory territory, int moveUnitsCount) {
        int count = 0;
        Iterator<Unit> itr = territory.getUnits();
        while (itr.hasNext()) {
            Unit curr = itr.next();
            count+= curr.getAmount();
        }
        return count >= moveUnitsCount;
    }

    /**
     * Get the rule identifier string, used by getRules(String)
     *
     * @param startTerritory initial territory
     * @param destTerritory destination territory
     * @param moveUnitsCount amount of units user want to move
     * @return true if meets all rule, false if not
     */
    public abstract boolean checkRule(Territory startTerritory, Territory destTerritory, int moveUnitsCount);
}
