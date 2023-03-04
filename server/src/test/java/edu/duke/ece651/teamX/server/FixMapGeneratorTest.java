package edu.duke.ece651.teamX.server;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class FixMapGeneratorTest {
  @Test
  public void test_generae_simple_gamemap() {
    MapGenerator mapGenerator = new FixMapGenerator();
    Map gameMap = mapGenerator.createMap();
    Player playerA = new TextPlayer("P1");
    Player playerB = new TextPlayer("P2");
    ArrayList<Territory> group = gameMap.assignToPlayer(playerA);
    assertEquals(1, group.size());
    assertEquals("Hogwarts", group.get(0).getName());
    assertThrows(NoSuchElementException.class, ()->gameMap.assignToPlayer(playerB));
  }

}
