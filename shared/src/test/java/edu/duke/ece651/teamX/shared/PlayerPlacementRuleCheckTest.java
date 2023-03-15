package edu.duke.ece651.teamX.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlayerPlacementRuleCheckTest {
  @Test
  public void test_checkRule() {
    Player p1 = new TextPlayer("p1");
    Territory t1 = new Territory("t1");
    Unit unit = new Soldier(10);
    p1.addTerritory(t1);
    p1.place(t1,unit);
    RuleChecker playerPlacementRuleCheck = new PlayerPlacementRuleCheck(p1, 5);
    assertFalse(playerPlacementRuleCheck.checkRule());
  }

}
