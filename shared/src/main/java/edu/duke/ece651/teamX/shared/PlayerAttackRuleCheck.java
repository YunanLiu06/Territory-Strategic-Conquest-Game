package edu.duke.ece651.teamX.shared;

import java.util.HashSet;

public class PlayerAttackRuleCheck extends RuleChecker{

    private Territory startTerritory;
    private Territory destTerritory;
    private int moveUnitsCount;
    @Override
    public boolean checkRule() {
        if (isValidUnitCount(startTerritory, moveUnitsCount)) {
            return fireCheck();
        }
        return false;
    }

    public boolean fireCheck() {
        if (startTerritory.getOwner() == destTerritory.getOwner()) {
            return false;
        } else if (!startTerritory.hasAdjacentTerritory(destTerritory)) {
            return false;
        }
        return true;
    }

    public PlayerAttackRuleCheck(Territory startTerritory, Territory destTerritory, int moveUnitsCount) {
        this.startTerritory = startTerritory;
        this.destTerritory = destTerritory;
        this.moveUnitsCount = moveUnitsCount;
    }
}
