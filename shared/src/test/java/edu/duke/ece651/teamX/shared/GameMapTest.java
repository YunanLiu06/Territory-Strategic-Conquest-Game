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

  @Test
  public void test_get_adjacent_territories(){
    LinkedList<String> territoryNames = new LinkedList<>();
    territoryNames.add("A");
    territoryNames.add("B");
    territoryNames.add("C");
    territoryNames.add("D");
    int[][] adjacentInfo = {{0,1},{2,3},{0,2},{1,2}}; //[[A,B],[C,D]]
    GameMap gameMap = new GameMap(1, territoryNames, adjacentInfo);
    Iterator<Map.Entry<Territory, LinkedList<Territory>>> iter = gameMap.getGameMap();
    Map.Entry<Territory, LinkedList<Territory>>entry = iter.next();
    assertEquals("A", entry.getKey().getName());
    Iterator<Territory> adjIter = gameMap.getAdjacentTerritories(entry.getKey());
    assertEquals("B", adjIter.next().getName());
    assertEquals("C", adjIter.next().getName());
  }

  @Test
  public void test_get_territory_by_name(){
    MapGenerator mapGenerator = new FixMapGenerator();
    GameMap gameMap = mapGenerator.createMap(2);
    Territory narnia = gameMap.getTerritoryByName("Narnia");
    Territory roshar = gameMap.getTerritoryByName("Roshar");
    assertEquals("Narnia", narnia.getName());
    assertEquals("Roshar", roshar.getName());
    assertThrows(IllegalArgumentException.class, ()->gameMap.getTerritoryByName("None"));
    
  }

  private GameMap create_move_attack_map(int[][] adjacentInfo){
    LinkedList<String> territoryNames = new LinkedList<>();
    territoryNames.add("A");
    territoryNames.add("B");
    territoryNames.add("C");
    territoryNames.add("D");
    territoryNames.add("E");
    territoryNames.add("F");
    GameMap gameMap = new GameMap(2, territoryNames, adjacentInfo);
    Player p1 = new TextPlayer("A");
    Player p2 = new TextPlayer("B");
    gameMap.assignToPlayer(p1);
    gameMap.assignToPlayer(p2);
    return gameMap;
  }

  @Test
  public void test_can_move(){
    int[][] adjacentInfo = {{0,1}, {0,4}, {1,3}, {1,5}, {3,2}, {4,5}, {5,2}};
    GameMap gameMap = create_move_attack_map(adjacentInfo);

    Territory a = gameMap.getTerritoryByName("A");
    Territory b = gameMap.getTerritoryByName("B");
    Territory c = gameMap.getTerritoryByName("C");
    Territory d = gameMap.getTerritoryByName("D");
    Territory e = gameMap.getTerritoryByName("E");
    Territory f = gameMap.getTerritoryByName("F");

    assertTrue(gameMap.canMove(a, b));
    assertTrue(gameMap.canMove(b, a));
    assertFalse(gameMap.canMove(a, c));
    assertFalse(gameMap.canMove(b, c));
    assertFalse(gameMap.canMove(c, a));
    assertFalse(gameMap.canMove(c, b));
    assertFalse(gameMap.canMove(d, e));
    assertFalse(gameMap.canMove(b, d));
    assertTrue(gameMap.canMove(e, f));
    assertTrue(gameMap.canMove(f, e));
  }

  @Test
  public void test_can_attack(){
    int[][] adjacentInfo = {{0,1}, {0,2}, {1,3}, {1,4}, {3,5}, {2,4}, {4,5}};
    GameMap gameMap = create_move_attack_map(adjacentInfo);

    Territory a = gameMap.getTerritoryByName("A");
    Territory b = gameMap.getTerritoryByName("B");
    Territory c = gameMap.getTerritoryByName("C");
    Territory d = gameMap.getTerritoryByName("D");
    Territory e = gameMap.getTerritoryByName("E");
    Territory f = gameMap.getTerritoryByName("F");

    assertTrue(gameMap.canAttack(b, d));
    assertTrue(gameMap.canAttack(b, e));
    assertTrue(gameMap.canAttack(c, e));
    assertTrue(gameMap.canAttack(d, b));
    assertTrue(gameMap.canAttack(e, b));
    assertTrue(gameMap.canAttack(e, c));

    assertFalse(gameMap.canAttack(a, b));
    assertFalse(gameMap.canAttack(e, f));
    assertFalse(gameMap.canAttack(a, e));
    assertFalse(gameMap.canAttack(a, d));
    assertFalse(gameMap.canAttack(d, c));
    assertFalse(gameMap.canAttack(d, a));
    assertFalse(gameMap.canAttack(f, b));
  }
}
