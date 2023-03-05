package edu.duke.ece651.teamX.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class GameMapTest {

  @Test
  public void test_simple_gamemap(){
    LinkedList<String> territoryNames = new LinkedList<>();
    territoryNames.add("Hogwarts");
    GameMap gameMap = new GameMap(1, territoryNames, null);
    Player playerA = new TextPlayer("A");
    Player playerB = new TextPlayer("B");
    gameMap.assignToPlayer(playerA);
    assertThrows(NoSuchElementException.class, ()->gameMap.assignToPlayer(playerB));
  }

  @Test
  public void test_single_player_gamemap(){
    LinkedList<String> territoryNames = new LinkedList<>();
    territoryNames.add("A");
    territoryNames.add("B");
    territoryNames.add("C");
    territoryNames.add("D");
    int[][] adjacentInfo = {{0,1},{2,3},{0,2},{1,2}}; //[[A,B],[C,D]]
    GameMap gameMap = new GameMap(1, territoryNames, adjacentInfo);
    Player playerA = new TextPlayer("A");
    gameMap.assignToPlayer(playerA);
  }

  @Test
  public void test_two_player_gamemap(){
    LinkedList<String> territoryNames = new LinkedList<>();
    territoryNames.add("A");
    territoryNames.add("B");
    territoryNames.add("C");
    territoryNames.add("D");
    int[][] adjacentInfo = {{0,1},{2,3},{0,2},{1,3}}; //[[A,B],[C,D]]
    GameMap gameMap = new GameMap(2, territoryNames, adjacentInfo);

    Player playerA = new TextPlayer("A");
    Player playerB = new TextPlayer("B");
    Player playerC = new TextPlayer("C");
    gameMap.assignToPlayer(playerA);
    gameMap.assignToPlayer(playerB);
    assertThrows(NoSuchElementException.class, ()->gameMap.assignToPlayer(playerC));
  }

  @Test
  public void test_get_gamemap(){
    LinkedList<String> territoryNames = new LinkedList<>();
    territoryNames.add("A");
    territoryNames.add("B");
    territoryNames.add("C");
    territoryNames.add("D");
    int[][] adjacentInfo = {{0,1},{2,3},{0,2},{1,3}}; //[[A,B],[C,D]]
    GameMap gameMap = new GameMap(2, territoryNames, adjacentInfo);
    Iterator<Map.Entry<Territory, LinkedList<Territory>>> iter = gameMap.getGameMap();
    assertEquals(true, iter.hasNext());

    Map.Entry<Territory, LinkedList<Territory>> ta = iter.next();
    assertEquals("A",ta.getKey().getName());
    assertEquals(2, ta.getValue().size());

    Map.Entry<Territory, LinkedList<Territory>> tb = iter.next();
    assertEquals("B",tb.getKey().getName());
    assertEquals(2, tb.getValue().size());

    Map.Entry<Territory, LinkedList<Territory>> tc = iter.next();
    assertEquals("C",tc.getKey().getName());
    assertEquals(2, tc.getValue().size());

    Map.Entry<Territory, LinkedList<Territory>> td = iter.next();
    assertEquals("D",td.getKey().getName());
    assertEquals(2, td.getValue().size());

    assertEquals(false, iter.hasNext());
  }

}
