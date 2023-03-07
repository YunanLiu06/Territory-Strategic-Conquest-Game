package edu.duke.ece651.teamX.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TextMapViewTest {
  @Test
  public void test_text_map_view() {
    MapGenerator mapGenerator = new FixMapGenerator();
    GameMap gameMap = mapGenerator.createMap(2);
    MapView<String> mapView = new TextMapView();
    String view = mapView.printMap(gameMap);
    String expected = 
    "                         Map                         \n"+
    "--------------------------------------------------\n"+
    "Narnia, Midkemia, Oz, Gondor, Elantris, Scadrial, \n"+
    "Roshar, Mordor, Hogwarts, Agrabah, Atlantica, Brigadoon\n";
    assertEquals(expected, view);
  }

}
