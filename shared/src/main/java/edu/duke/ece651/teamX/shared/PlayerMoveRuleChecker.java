package edu.duke.ece651.teamX.shared;

import java.util.HashSet;
import java.util.Iterator;

public class PlayerMoveRuleChecker extends RuleChecker {

    private Territory startTerritory;
    private Territory destTerritory;
    private int moveUnitsCount;
    @Override
    public boolean checkRule() {
        if (isValidUnitCount(startTerritory, moveUnitsCount)) {
            return pathCheck(startTerritory, destTerritory, new HashSet<>());
        }
        return false;
    }

    public boolean pathCheck(Territory startTerritory, Territory destTerritory, HashSet<Territory> visited) {
        if (startTerritory.equals(destTerritory) && startTerritory.getOwner() == destTerritory.getOwner()) {
            return true;
        } else {
            Iterator<Territory> itr = startTerritory.getAdjacentTerritories();
            while (itr.hasNext()) {
                Territory next = itr.next();
                if (visited.contains(next)) {
                    continue;
                }
                visited.add(next);
                if (next.getOwner() == startTerritory.getOwner()) {
                    if (pathCheck(next, destTerritory, visited)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public PlayerMoveRuleChecker(Territory startTerritory, Territory destTerritory, int moveUnitsCount) {
        this.startTerritory = startTerritory;
        this.destTerritory = destTerritory;
        this.moveUnitsCount = moveUnitsCount;
    }
}
