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
    Player p1 = new TextPlayer("p1");
    Territory t1 = new Territory("TestTerritory1", p1);
    Territory t2 = new Territory("TestTerritory2", p1);
    Territory t3 = new Territory("TestTerritory3", p1);
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
    Territory t4 = new Territory("TestTerritory4", p1);
    Soldier s4 = new Soldier( 10);
    t4.addUnit(s4);
    Territory t5 = new Territory("TestTerritory5", p1);
    Soldier s5 = new Soldier( 10);
    t5.addUnit(s5);
    Territory t6 = new Territory("TestTerritory6", p1);
    Soldier s6 = new Soldier( 10);
    t6.addUnit(s6);
    t2.addAdjacentTerritory(t4);
    t1.addAdjacentTerritory(t5);
    t5.addAdjacentTerritory(t6);
    assertEquals(playerMoveRuleChecker.checkRule(t1, t6, 5), true);
    t4 = new Territory("TestTerritory4", p1);
    t4.addUnit(s4);
    t5 = new Territory("TestTerritory5", p1);
    t5.addUnit(s5);
    t6 = new Territory("TestTerritory6");
    t6.addUnit(s6);
    t2.addAdjacentTerritory(t4);
    t1.addAdjacentTerritory(t5);
    t5.addAdjacentTerritory(t6);
    assertEquals(playerMoveRuleChecker.checkRule(t1, t6, 5), false);
    Player p2 = new TextPlayer("p2");
    Territory t7 = new Territory("TestTerritory1", p2);
    Territory t8 = new Territory("TestTerritory2");
    Territory t9 = new Territory("TestTerritory3", p2);
    t7.addUnit(s1);
    t8.addUnit(s2);
    t9.addUnit(s3);
    t7.addAdjacentTerritory(t8);
    t8.addAdjacentTerritory(t9);
    assertEquals(playerMoveRuleChecker.checkRule(t7, t9, 1), false);
  }

}
