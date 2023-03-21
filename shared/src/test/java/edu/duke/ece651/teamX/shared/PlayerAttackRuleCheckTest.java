package edu.duke.ece651.teamX.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlayerAttackRuleCheckTest {
  @Test
  public void test_fireCheck() {
    Player p1 = new TextPlayer("p1");
    Player p2 = new TextPlayer("p2");
    Territory t1 = new Territory("TestTerritory1", p1);
    Territory t2 = new Territory("TestTerritory2", p2);
    Territory t3 = new Territory("TestTerritory3", p2);
    Territory t4 = new Territory("TestTerritory4", p1);
    Soldier s1 = new Soldier( 10);
    Soldier s2 = new Soldier( 20);
    Soldier s3 = new Soldier( 3);
    Soldier s4 = new Soldier(5);
    t1.addUnit(s1);
    t2.addUnit(s2);
    t3.addUnit(s3);
    t4.addUnit(s4);
    t1.addAdjacentTerritory(t2);
    t2.addAdjacentTerritory(t3);
    t1.addAdjacentTerritory(t4);
    RuleChecker ruleChecker = new PlayerAttackRuleCheck(t1,t2,5);
    assertEquals(ruleChecker.checkRule(), true);
    ruleChecker = new PlayerAttackRuleCheck(t1, t2, 15);
    assertEquals(ruleChecker.checkRule(), false);
    ruleChecker = new PlayerAttackRuleCheck(t1, t3, 5);
    assertEquals(ruleChecker.checkRule(), false);
    ruleChecker = new PlayerAttackRuleCheck(t1, t4, 5);
    assertEquals(ruleChecker.checkRule(), false);
  }

}
