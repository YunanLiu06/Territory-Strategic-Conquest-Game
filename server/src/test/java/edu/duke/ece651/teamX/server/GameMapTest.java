package edu.duke.ece651.teamX.server;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class GameMapTest {

  @Test
  public void test_simple_gamemap(){
    LinkedList<String> territoryNames = new LinkedList<>();
    territoryNames.add("Hogwarts");
    Map gameMap = new Map(1, territoryNames, null);
    Player playerA = new TextPlayer("A");
    Player playerB = new TextPlayer("B");
    ArrayList<Territory> group = gameMap.assignToPlayer(playerA);
    assertEquals(1, group.size());
    assertEquals("Hogwarts", group.get(0).getName());
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
    Map gameMap = new Map(1, territoryNames, adjacentInfo);
    Player playerA = new TextPlayer("A");
    ArrayList<Territory> group = gameMap.assignToPlayer(playerA);
    assertEquals(4, group.size());
    assertEquals("A", group.get(0).getName());
    assertEquals("B", group.get(1).getName());
    assertEquals("C", group.get(2).getName());
    assertEquals("D", group.get(3).getName());
  }

  @Test
  public void test_two_player_gamemap(){
    LinkedList<String> territoryNames = new LinkedList<>();
    territoryNames.add("A");
    territoryNames.add("B");
    territoryNames.add("C");
    territoryNames.add("D");
    int[][] adjacentInfo = {{0,1},{2,3},{0,2},{1,2}}; //[[A,B],[C,D]]
    Map gameMap = new Map(2, territoryNames, adjacentInfo);
    Player playerA = new TextPlayer("A");
    Player playerB = new TextPlayer("B");
    Player playerC = new TextPlayer("C");
    ArrayList<Territory> groupA = gameMap.assignToPlayer(playerA);
    ArrayList<Territory> groupB = gameMap.assignToPlayer(playerB);
    assertThrows(NoSuchElementException.class, ()->gameMap.assignToPlayer(playerC));
    assertEquals(2, groupA.size());
    assertEquals(2, groupB.size());
    assertEquals("A", groupA.get(0).getName());
    assertEquals("B", groupA.get(1).getName());
    assertEquals("C", groupB.get(0).getName());
    assertEquals("D", groupB.get(1).getName());
  }

}
