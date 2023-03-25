package edu.duke.ece651.teamX.shared;

public class PlayerPlacementRuleCheck extends RuleChecker{
    private Player player;
    private int totalUnits;

    public PlayerPlacementRuleCheck(Player player, int totalUnits) {
        this.player = player;
        this.totalUnits = totalUnits;
    }

    @Override
    public boolean checkRule() {
        return player.getNumOfAlreadyPlaced() <= totalUnits;
    }


}
