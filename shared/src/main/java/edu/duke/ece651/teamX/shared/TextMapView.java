package edu.duke.ece651.teamX.shared;

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
    
}
