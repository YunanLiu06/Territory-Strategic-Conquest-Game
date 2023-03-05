package edu.duke.ece651.teamX.shared;

import java.util.LinkedList;

public class FixMapGenerator implements MapGenerator{

    @Override
    public Map createMap() {
        LinkedList<String> territoryNames = new LinkedList<>();
        territoryNames.add("Hogwarts");
        Map gameMap = new Map(1, territoryNames, null);
        return gameMap;
    }
    
}
