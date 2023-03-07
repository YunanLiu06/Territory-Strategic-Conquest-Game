package edu.duke.ece651.teamX.shared;

import java.security.InvalidParameterException;
import java.util.LinkedList;

public class FixMapGenerator implements MapGenerator {

  /**
   * Create game map
   * 
   * @param playerNumber number of players
   * @throws InvalidParameterException
   */
  @Override
  public GameMap createMap(int playerNumber) {
    if (playerNumber == 2) {
      return createTwoPlayerMap();
    } else if (playerNumber == 3) {
      return createThreePlayerMap();
    } else if (playerNumber == 4) {
      return createFourPlayerMap();
    } else {
      throw new InvalidParameterException("Only support player number 2, 3 and 4");
    }
  }

  /**
   * Create two player map:
   * 0, 1, 2, 3, 4, 5
   * 6, 7, 8, 9, 10, 11
   * 0:Narnia, 6:Roshar...
   * 
   * @return created game map
   */
  private GameMap createTwoPlayerMap() {
    String[] names = { 
        "Narnia", "Midkemia", "Oz", "Gondor", "Elantris", "Scadrial",
        "Roshar", "Mordor", "Hogwarts", "Agrabah", "Atlantica", "Brigadoon" 
    };
    int[][] adjacentInfo = { 
        { 0, 1 }, { 0, 6 }, { 1, 2 }, { 1, 7 }, { 2, 3 }, { 2, 8 }, { 3, 4 }, { 3, 9 }, { 4, 5 },
        { 4, 10 }, { 5, 11 }, { 6, 7 }, { 7, 8 }, { 8, 9 }, { 9, 10 }, { 10, 11 } 
    };
    return createPlayerMap(2, names, adjacentInfo);
  }

  /**
   * Create two player map:
   * 0, 1, 2, 3, 4, 5
   * 6, 7, 8, 9, 10, 11
   * 12, 13, 14, 15, 16
   * 0:Narnia, 6:Roshar...
   * 
   * @return created game map
   */
  private GameMap createThreePlayerMap() {
    String[] names = { 
        "Narnia", "Midkemia", "Oz", "Gondor", "Elantris", "Scadrial",
        "Roshar", "Mordor", "Hogwarts", "Agrabah", "Atlantica", "Brigadoon",
        "Dillford", "Grouchland", "Jonathanland", "Karatas", "Loganville", "Metropolis" 
    };
    int[][] adjacentInfo = { 
        { 0, 1 }, { 0, 6 }, { 1, 2 }, { 1, 7 }, { 2, 3 }, { 2, 8 }, { 3, 4 }, { 3, 9 }, { 4, 5 },
        { 4, 10 }, { 5, 11 }, { 6, 7 }, { 6, 12 }, { 7, 8 }, { 7, 13 }, { 8, 9 }, { 8, 14 }, { 9, 10 }, { 9, 15 }, 
        { 10, 11 }, { 10, 16 }, { 11, 17 }, { 12, 13 }, { 13, 14 }, { 14, 15 }, { 15, 16 }, { 16, 17 } 
    };
    return createPlayerMap(3, names, adjacentInfo);
  }

  /**
   * Create two player map:
   * 0, 1, 2, 3, 4, 5
   * 6, 7, 8, 9, 10, 11
   * 12, 13, 14, 15, 16
   * 17, 18, 19, 20, 21
   * 0:Narnia, 6:Roshar...
   * 
   * @return created game map
   */
  private GameMap createFourPlayerMap() {
    String[] names = { 
        "Narnia", "Midkemia", "Oz", "Gondor", "Elantris", "Scadrial",
        "Roshar", "Mordor", "Hogwarts", "Agrabah", "Atlantica", "Brigadoon",
        "Dillford", "Grouchland", "Jonathanland", "Karatas", "Loganville", "Metropolis",
        "Millbrook", "Monstropolis", "Pleasantville", "Portoroso", "Pribrezhny", "Seabrook" 
    };
    int[][] adjacentInfo = { 
        { 0, 1 }, { 0, 6 }, { 1, 2 }, { 1, 7 }, { 2, 3 }, { 2, 8 }, { 3, 4 }, { 3, 9 }, { 4, 5 },
        { 4, 10 }, { 5, 11 }, { 6, 7 }, { 6, 12 }, { 7, 8 }, { 7, 13 }, { 8, 9 }, { 8, 14 }, { 9, 10 }, { 9, 15 }, 
        { 10, 11 }, { 10, 16 }, { 11, 17 }, { 12, 13 }, { 12, 18 }, { 13, 14 }, { 13, 19 }, { 14, 15 }, { 14, 20 }, 
        { 15, 16 }, { 15, 21 }, { 16, 17 }, { 16, 22 }, { 17, 23 }, { 18, 19 }, { 19, 20 }, { 20, 21 }, { 21, 22 }, { 22, 23 } 
    };
    return createPlayerMap(4, names, adjacentInfo);
  }

  /**
   * Create player map base on basic information
   * 
   * @param playerNumber player number
   * @param names        name list
   * @param adjacentInfo adjacent information
   * @return created game map
   */
  private GameMap createPlayerMap(int playerNumber, String[] names, int[][] adjacentInfo) {
    LinkedList<String> territoryNames = new LinkedList<>();
    for (String name : names) {
      territoryNames.add(name);
    }
    return new GameMap(playerNumber, territoryNames, adjacentInfo);
  }
}
