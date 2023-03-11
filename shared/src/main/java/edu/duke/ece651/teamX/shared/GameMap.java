package edu.duke.ece651.teamX.shared;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;

public class GameMap {

  // undirected connected graph: {Territory: [Adjacent Territories]}
  private LinkedHashMap<Territory, LinkedList<Territory>> territories = new LinkedHashMap<>();

  // {name: territory}
  private HashMap<String, Territory> territoryNameMap = new HashMap<>();

  private LinkedList<ArrayList<Territory>> groups = new LinkedList<>();

  /**
   * Create territories based on the given territory names
   * 
   * @param playerNums     how many players in map
   * @param territoryNames name of each territories, Note: the name should in the
   *                       order of group,
   *                       eg: playerNums=3, A,B,C,D,E,F, then ABC would be one
   *                       group, DEF would be another
   * @param adjacentInfo   adjacent information about the territories
   */
  public GameMap(int playerNums, LinkedList<String> territoryNames, int[][] adjacentInfo) {
    createMap(playerNums, territoryNames, adjacentInfo);
  }

  /**
   * Get game map iterator
   * @return game map iterator
   */
  public Iterator<Map.Entry<Territory, LinkedList<Territory>>> getGameMap(){
    return territories.entrySet().iterator();
  }

  /**
   * Get adjacent territories iterator
   * @param territory target territory
   * @return adjacent territories iterator
   */
  public Iterator<Territory> getAdjacentTerritories(Territory territory){
    return territories.get(territory).iterator();
  }

  /**
   * Assign territories in one group to player
   * @player which player to assign
   * @throws NoSuchElementException
   */
  public void assignToPlayer(Player player) {
    if (!groups.isEmpty()) {
      ArrayList<Territory> group = groups.pop();
      for(Territory territory: group){
        territory.initiateOnwer(player);
        // add adjacent territories
        for(Territory adj: territories.get(territory)){
          territory.addAdjacentTerritory(adj);
        }
      }
    } else {
      throw new NoSuchElementException("No more groups could be assigned");
    }
  }

  /**
   * Get territory by name
   * @throws InvalidParameterException
   * @param name territory name
   * @return territory named as parameter
   */
  public Territory getTerritoryByName(String name){
    if(!territoryNameMap.containsKey(name)){
      throw new InvalidParameterException("There is not territory named " + name);
    }
    return territoryNameMap.get(name);
  }

  /**
   * Judge if can move units from one territory to another
   * @rule 'from' and 'to' belong the to same player and 
   *        there is at least one route from 'from' to 'to' 
   *        consisting only by other territories of that player  
   * @param from which territory to move out
   * @param to which territory to move in
   * @return if can move
   */
  public boolean canMove(Territory from, Territory to){
    HashSet<Territory> route = new HashSet<>();
    return canMove(from, to, route);
  }

  /**
   * Judge if can attack from one territory to another
   * @rule 'attacker' and 'victim' belong to different players and
   *       attacker and victim are adjacent to each other
   * @param attacker which territory to attack
   * @param victim which territory to be attacked
   * @return if can attack
   */
  public boolean canAttack(Territory attacker, Territory victim){
    if(victim.getOwner() == attacker.getOwner()){
      return false;
    }
    for(Territory adj: territories.get(victim)){
      if(adj == attacker){
        return true;
      }
    }
    return false;
  }

  /**
   * Help function for canMove, by DFS
   * @param curr current territory
   * @param target target territory
   * @param route visited territories
   * @return if can move
   */
  private boolean canMove(Territory curr, Territory target, HashSet<Territory> route){
    if(route.contains(curr) || curr.getOwner() != target.getOwner()){
      return false;
    }
    if(curr == target){
      return true;
    }
    route.add(curr);
    for(Territory adj: territories.get(curr)){
      if(adj.getOwner() != curr.getOwner()){
        continue;
      }
      if(canMove(adj, target, route)){
        return true;
      }
    }
    return false;
  }

  /**
   * Get territory by index from groups
   * 
   * @param index  index of territory in 1D
   * @param rowLen how many territory in one row
   * @return territory in the index
   */
  private Territory getTerritoryByIndex(int index, int rowLen) {
    int groupIndex = index / rowLen;
    int territoryIndex = index - groupIndex * rowLen;
    return groups.get(groupIndex).get(territoryIndex);
  }

  /**
   * Create game map
   * 
   * @param playerNums     player numbers
   * @param territoryNames names of each territory
   * @param adjacentInfo   adjancent information
   */
  private void createMap(int playerNums, LinkedList<String> territoryNames, int[][] adjacentInfo) {
    int groupTerrNums = territoryNames.size() / playerNums;
    // create territories
    createTerritories(playerNums, groupTerrNums, territoryNames);

    // no territory is adjancent
    if (adjacentInfo == null) {
      return;
    }
    // connect territories
    for (int[] from_to : adjacentInfo) {
      connectTerritories(groupTerrNums, from_to[0], from_to[1]);
    }
  }

  /**
   * Connect territories: from <-> to
   * 
   * @param rowLen how many territory in one row
   * @param from   territory index
   * @param to     territory index
   */
  private void connectTerritories(int rowLen, int from, int to) {
    Territory fromTerritory = getTerritoryByIndex(from, rowLen);
    Territory toTerritory = getTerritoryByIndex(to, rowLen);
    territories.get(fromTerritory).add(toTerritory);
    territories.get(toTerritory).add(fromTerritory);
  }

  /**
   * Create territories
   * 
   * @param playerNums     number of players
   * @param groupTerrNums  number of territories in one group
   * @param territoryNames territory names
   */
  private void createTerritories(int playerNums, int groupTerrNums, LinkedList<String> territoryNames) {
    for (int m = 0; m < playerNums; m++) {
      ArrayList<Territory> group = new ArrayList<>();
      for (int n = 0; n < groupTerrNums; n++) {
        String name = territoryNames.pop();
        Territory territory = new Territory(name);
        territoryNameMap.put(name, territory); //add to nameMap
        group.add(territory);
        territories.put(territory, new LinkedList<>());
      }
      groups.add(group);
    }
  }
}
