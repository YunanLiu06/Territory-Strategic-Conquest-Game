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

  @Test
  public void test_print_territories(){
    MapGenerator mapGenerator = new FixMapGenerator();
    GameMap gameMap = mapGenerator.createMap(2);
    Player p1 = new TextPlayer("P1");
    Player p2 = new TextPlayer("P2");
    gameMap.assignToPlayer(p1);
    gameMap.assignToPlayer(p2);

    Territory narnia = gameMap.getTerritoryByName("Narnia");
    narnia.addUnit(new Soldier(10));

    MapView<String> mapView = new TextMapView();
    String territoryView = mapView.printTerritories(gameMap);
    String expected = 
    "This is a list of P1 territories:\n"+
    "\n"+
    " 10 units in Narnia (next to: Midkemia, Roshar)\n"+
    " 0 units in Midkemia (next to: Narnia, Oz, Mordor)\n"+
    " 0 units in Oz (next to: Midkemia, Gondor, Hogwarts)\n"+
    " 0 units in Gondor (next to: Oz, Elantris, Agrabah)\n"+
    " 0 units in Elantris (next to: Gondor, Scadrial, Atlantica)\n"+
    " 0 units in Scadrial (next to: Elantris, Brigadoon)\n"+
    "\n"+
    "\n"+
    "This is a list of P2 territories:\n"+
    "\n"+
    " 0 units in Roshar (next to: Narnia, Mordor)\n"+
    " 0 units in Mordor (next to: Midkemia, Roshar, Hogwarts)\n"+
    " 0 units in Hogwarts (next to: Oz, Mordor, Agrabah)\n"+
    " 0 units in Agrabah (next to: Gondor, Hogwarts, Atlantica)\n"+
    " 0 units in Atlantica (next to: Elantris, Agrabah, Brigadoon)\n"+
    " 0 units in Brigadoon (next to: Scadrial, Atlantica)\n"+
    "\n"+
    "\n";
    assertEquals(expected, territoryView);
  }

}
