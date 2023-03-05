package edu.duke.ece651.teamX.shared;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Map {

  // undirected connected graph: {Territory: [Adjacent Territories]}
  private LinkedHashMap<Territory, LinkedList<Territory>> territories;

  private LinkedList<ArrayList<Territory>> groups;

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
  public Map(int playerNums, LinkedList<String> territoryNames, int[][] adjacentInfo) {
    territories = new LinkedHashMap<>();
    groups = new LinkedList<>();
    createMap(playerNums, territoryNames, adjacentInfo);
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
      }
    } else {
      throw new NoSuchElementException("No more groups could be assigned");
    }
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
        Territory territory = new Territory(territoryNames.pop());
        group.add(territory);
        territories.put(territory, new LinkedList<>());
      }
      groups.add(group);
    }
  }
}
