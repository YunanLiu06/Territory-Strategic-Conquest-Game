package edu.duke.ece651.teamX.shared;

import java.util.Iterator;

public class PlayerMoveRuleChecker extends RuleChecker {
    @Override
    public boolean checkRule(Territory startTerritory, Territory destTerritory, int moveUnitsCount) {
        if (isValidUnitCount(startTerritory, moveUnitsCount)) {
            return pathCheck(startTerritory, destTerritory);
        }
        return false;
    }

    public boolean pathCheck(Territory startTerritory, Territory destTerritory) {
        if (startTerritory.equals(destTerritory)) {
            return true;
        } else {
            Iterator<Territory> itr = startTerritory.getAdjacentTerritories();
            while (itr.hasNext()) {
                Territory next = itr.next();
                if (pathCheck(next, destTerritory)) {
                    return true;
                }
            }
        }
        return false;
    }

    public PlayerMoveRuleChecker() { }
}
