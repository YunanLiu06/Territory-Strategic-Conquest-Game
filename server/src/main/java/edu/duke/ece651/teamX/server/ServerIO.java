package edu.duke.ece651.teamX.server;

import edu.duke.ece651.teamX.shared.*;
import java.net.*;
import java.io.*;
import java.util.*;

import javax.xml.crypto.Data;

/**
 * This class handles the input and output for the Risc game.
 * 
 * @param socket: the socket of the client
 * @param in:     the input stream of this specfic socket
 * @param out:    the output stram of this specific socket
 * @param name:   the name of the player
 * @param map:    the map of the game
 */
public class ServerIO extends Thread {
  private Socket socket;
  private InputStream in;
  private OutputStream out;
  private DataOutputStream writeObject;
  private DataInputStream readObject;
  private String name;
  private GameMap gameMap;
  private Player player;

  // string to state that there was an IOException in ther server
  static String CONSTRUCTOR_ERROR = "Constructor Error: ";

  // strign to state that there was an IOError
  static String IO_ERROR = "IO Error: ";

  public ServerIO(Socket socket, InputStream in, OutputStream out, String name, GameMap gameMap) {
    // initialize IO
    this.socket = socket;
    this.in = in;
    this.out = out;
    writeObject = new DataOutputStream(out);
    readObject = new DataInputStream(in);
    // intialize other things for the game
    this.name = name;
    this.gameMap = gameMap;
    player = new TextPlayer(name);
    gameMap.assignToPlayer(player);
  }

  /**
   * Private helper function to return a string of the text map
   */
  private String printTextMap() {
    MapView<String> mapView = new TextMapView();
    String view = mapView.printMap(gameMap);
    return view;
  }

  /**
   * This function is the initialization phase of the game
   * It welcomes the users to the game, and prints the map
   * to the players
   */
  public void initializationPhase() {
    try {
      writeObject.writeUTF("\nWelcome to the Game of Risc.");
      writeObject.writeUTF("Your Player Name: " + player.getName() + "\n" + "Here is the map of the game\n");
      // generate Map and send to Client so they can print
      String view = printTextMap();
      writeObject.writeUTF(view);
      // get the players territories and print them to the client
      writeObject.writeUTF(printTerritories());

    } catch (IOException e) {
      System.out.println(IO_ERROR + e + "\n");
    }
  }

  private String printTerritories() {
    String territory = "\nThis is a list of your territories: \n";
    String toAdd = "\n";
    Iterator<Territory> it = player.getTerritories();
    while (it.hasNext()) {
      Territory t = it.next();
      String element = t.getName();
      Iterator<Territory> adj = gameMap.getAdjacentTerritories(t);
      toAdd += " " + element + " (next to: ";
      while (adj.hasNext()) {
        String adjTerritory = adj.next().getName();
        if (adj.hasNext()) {
          toAdd += adjTerritory + ", ";
        } else {
          toAdd += adjTerritory;
        }
      }
      toAdd += ")\n";
    }

    return territory + toAdd;
  }

  @Override
  public void run() {
    initializationPhase();
  }
}
