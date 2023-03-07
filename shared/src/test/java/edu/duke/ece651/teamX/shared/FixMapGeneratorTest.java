package edu.duke.ece651.teamX.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class FixMapGeneratorTest {
    @Test
    public void test_generate_two_player(){
        MapGenerator mapGenerator = new FixMapGenerator();
        GameMap gameMap = mapGenerator.createMap(2);
        assertThrows(IllegalArgumentException.class, ()->mapGenerator.createMap(5));
        Iterator<Map.Entry<Territory, LinkedList<Territory>>> iter = gameMap.getGameMap();
        String[] names = {"Narnia", "Midkemia", "Oz", "Gondor", "Elantris", "Scadrial",
                          "Roshar", "Mordor", "Hogwarts", "Agrabah", "Atlantica", "Brigadoon"};
        int[] adj = {2, 3, 3, 3, 3, 2, 2, 3, 3, 3, 3, 2};
        checkMapWithExpected(iter, names, adj);
    }

    @Test
    public void test_generate_three_player(){
        MapGenerator mapGenerator = new FixMapGenerator();
        GameMap gameMap = mapGenerator.createMap(3);
        Iterator<Map.Entry<Territory, LinkedList<Territory>>> iter = gameMap.getGameMap();
        String[] names = {"Narnia", "Midkemia", "Oz", "Gondor", "Elantris", "Scadrial",
                          "Roshar", "Mordor", "Hogwarts", "Agrabah", "Atlantica", "Brigadoon",
                          "Dillford", "Grouchland", "Jonathanland", "Karatas", "Loganville", "Metropolis"};
        int[] adj = {2, 3, 3, 3, 3, 2, 3, 4, 4, 4, 4, 3, 2, 3, 3, 3, 3, 2};
        checkMapWithExpected(iter, names, adj);
    }

    @Test
    public void test_generate_four_player(){
        MapGenerator mapGenerator = new FixMapGenerator();
        GameMap gameMap = mapGenerator.createMap(4);
        Iterator<Map.Entry<Territory, LinkedList<Territory>>> iter = gameMap.getGameMap();
        String[] names = {"Narnia", "Midkemia", "Oz", "Gondor", "Elantris", "Scadrial",
                          "Roshar", "Mordor", "Hogwarts", "Agrabah", "Atlantica", "Brigadoon",
                          "Dillford", "Grouchland", "Jonathanland", "Karatas", "Loganville", "Metropolis",
                          "Millbrook", "Monstropolis", "Pleasantville", "Portoroso", "Pribrezhny", "Seabrook"};
        int[] adj = {2, 3, 3, 3, 3, 2, 3, 4, 4, 4, 4, 3, 3, 4, 4, 4, 4, 3, 2, 3, 3, 3, 3, 2};
        checkMapWithExpected(iter, names, adj);
    }

    private void checkMapWithExpected(Iterator<Map.Entry<Territory, LinkedList<Territory>>> iter, String[] names, int[] adj){
        for(int m=0; m<names.length;m++){
            Map.Entry<Territory, LinkedList<Territory>> entry = iter.next();
            assertEquals(names[m], entry.getKey().getName());
            assertEquals(adj[m], entry.getValue().size());
        }
    }

}
