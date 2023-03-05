package edu.duke.ece651.teamX.shared;

import java.util.LinkedList;

public class FixMapGenerator implements MapGenerator{

    @Override
    public GameMap createMap() {
        LinkedList<String> territoryNames = new LinkedList<>();
        territoryNames.add("Hogwarts");
        GameMap gameMap = new GameMap(1, territoryNames, null);
        return gameMap;
    }
    
}
