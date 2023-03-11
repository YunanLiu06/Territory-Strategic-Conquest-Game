package edu.duke.ece651.teamX.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlayerMoveRuleCheckerTest {
  @Test
  public void test_isValidUnitCount() {
    Territory t1 = new Territory("TestTerritory1");
    Territory t2 = new Territory("TestTerritory2");
    Unit s1 = new Soldier( 10);
    Unit s2 = new Soldier( 20);
    t1.addUnit(s1);
    t2.addUnit(s2);
    t1.addAdjacentTerritory(t2);
    RuleChecker playerMoveRuleChecker = new PlayerMoveRuleChecker();
    assertEquals(playerMoveRuleChecker.isValidUnitCount(t1, 5), true);
    assertEquals(playerMoveRuleChecker.isValidUnitCount(t2, 100), false);
  }

  @Test
  public void test_checkRule() {
    Territory t1 = new Territory("TestTerritory1");
    Territory t2 = new Territory("TestTerritory2");
    Territory t3 = new Territory("TestTerritory3");
    Soldier s1 = new Soldier( 10);
    Soldier s2 = new Soldier( 20);
    Soldier s3 = new Soldier( 3);
    t1.addUnit(s1);
    t2.addUnit(s2);
    t3.addUnit(s3);
    t1.addAdjacentTerritory(t2);
    RuleChecker playerMoveRuleChecker = new PlayerMoveRuleChecker();
    assertEquals(playerMoveRuleChecker.checkRule(t1, t2, 5), true);
    assertEquals(playerMoveRuleChecker.checkRule(t1, t2, 15), false);
    assertEquals(playerMoveRuleChecker.checkRule(t1, t3, 5), false);
    Territory t4 = new Territory("TestTerritory4");
    Soldier s4 = new Soldier( 10);
    Territory t5 = new Territory("TestTerritory5");
    Soldier s5 = new Soldier( 10);
    Territory t6 = new Territory("TestTerritory6");
    Soldier s6 = new Soldier( 10);
    t2.addAdjacentTerritory(t4);
    t1.addAdjacentTerritory(t5);
    t5.addAdjacentTerritory(t6);
    assertEquals(playerMoveRuleChecker.checkRule(t1, t6, 5), true);
  }

}
