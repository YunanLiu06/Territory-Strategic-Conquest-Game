package edu.duke.ece651.teamX.shared;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class TextMapView implements MapView<String>{

    /**
     * Print map in text view
     * @param gameMap map to be printed
     * @return string map
     */
    @Override
    public String printMap(GameMap gameMap) {
        String body = getMapBody(gameMap);
        String title = getMapTitle(body.split("\n")[0].length());
        
        return title + "\n" + body + "\n";
    }

    /**
     * Print territories with units in text view
     * @param gameMap map to be printed
     * @return string territories
     */
    @Override
    public String printTerritories(GameMap gameMap){
        HashSet<Player> players = gameMap.collectPlayers();//players still owning territories
        String stringTerritories = new String();
        for(Player player: players){
            stringTerritories += getPlayerTerritories(player) + "\n";
            stringTerritories += "\n";
        }
        return stringTerritories;
    }

    /**
     * Get map title in string
     * @param len title length
     * @return string title
     */
    private String getMapTitle(int len){
        String title = new String();
        for(int m=0; m<len/2; m++){
            title += " ";
        }
        title += "Map";
        for(int m=0; m<len/2; m++){
            title += " ";
        }
        title += "\n";
        for(int m=0; m< len; m++){
            title += "-";
        }
        return title;
    }

    /**
     * Get map string body
     * @param gameMap map to be printed
     * @return string body
     */
    private String getMapBody(GameMap gameMap){
        int limitCount = 6;
        String mapNames = new String();
        Iterator<Map.Entry<Territory, LinkedList<Territory>>> iter = gameMap.getGameMap();
        int count = 0;
        while(iter.hasNext()){
            mapNames += iter.next().getKey().getName() + ", ";
            count += 1;
            if(count % limitCount == 0 && iter.hasNext()){
                mapNames += "\n";
            }
        }
        mapNames = mapNames.substring(0, mapNames.length() - 2);//remove last ", "
        return mapNames;
    }

    /**
     * Get the territories owned by the player in the form of string
     * @param player
     * @return territories in the form of string
     */
    private String getPlayerTerritories(Player player) {
        String territoryStr = "This is a list of " + player.getName() + " territories:\n";
        territoryStr += "\n";
        Iterator<Territory> territoryIter = player.getTerritories();
        while(territoryIter.hasNext()){
            Territory territory = territoryIter.next();
            int unitsNum = countUnits(territory);
            String adjTerrStr = getAdjTerritories(territory);

            String oneTerritoryStr = " " + unitsNum + " units in " + territory.getName() + " (next to: " + adjTerrStr + ")";
            territoryStr += oneTerritoryStr + "\n";
        }
        return territoryStr;
    }

    /**
     * Get the number of units in territory
     * @param territory
     * @return number of units
     */
    private int countUnits(Territory territory){
        int count = 0;
        Iterator<Unit> unitIter = territory.getUnits();
        while(unitIter.hasNext()){
            count += unitIter.next().getAmount();
        }
        return count;
    }

    /**
     * Get the adjacent territories in string format
     * @param territory
     * @return string format of adjacent territories
     */
    private String getAdjTerritories(Territory territory){
        String adjStr = new String();
        Iterator<Territory> adjIter = territory.getAdjacentTerritories();
        while(adjIter.hasNext()){
            adjStr += adjIter.next().getName();
            if(adjIter.hasNext()){
                adjStr += ", ";
            }
        }
        return adjStr;
    }
}
