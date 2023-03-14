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
        if (startTerritory.equals(destTerritory) && startTerritory.getOwner() == destTerritory.getOwner()) {
            return true;
        } else {
            Iterator<Territory> itr = startTerritory.getAdjacentTerritories();
            while (itr.hasNext()) {
                Territory next = itr.next();
                if (next.getOwner() == startTerritory.getOwner()) {
                    if (pathCheck(next, destTerritory)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public PlayerMoveRuleChecker() { }
}
