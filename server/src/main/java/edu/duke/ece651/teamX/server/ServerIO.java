package edu.duke.ece651.teamX.server;

import edu.duke.ece651.teamX.shared.*;
import java.net.*;
import java.io.*;
import java.util.*;

import javax.xml.crypto.Data;

import com.google.common.collect.Iterators;

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

  private int getUnitNum(Territory t) {
    int count = 0;
    Iterator<Unit> it = t.getUnits();
    Unit unit = it.next();
    return unit.getAmount();
  }

  /**
   * This function is for the placement phase of the game.
   * It allows the clients to place their territories
   */
  public void placementPhase() {
    try {
      // tell the user they are in the placement phase
      String initiatePlacement = "\n\nYou are now in the placement phase of the game. This means you will be given 30 units and you will place\n"
          + "them amongst your territories. Each territory must have units (you can't place 0).";
      writeObject.writeUTF(initiatePlacement);

      // for each territory ask the clients to place their units
      Iterator<Territory> it = player.getTerritories();
      while (it.hasNext()) {
        Territory t = it.next();
        String prompt = "\nHow many units do you want to place in territory " + t.getName() + "?";
        writeObject.writeUTF(prompt);
        // need to error check
        String temp = readObject.readUTF();
        int units = Integer.parseInt(temp);
        Unit unit = new Soldier(units);
        t.addUnit(unit);
      }
      // print out the unit information to the client
      Iterator<Territory> it2 = player.getTerritories();
      String prompt = "\nYour placements are as follows: ";
      writeObject.writeUTF(prompt);
      while (it2.hasNext()) {
        Territory t = it2.next();
        int size = getUnitNum(t);
        String unitString = " " + t.getName() + ": " + size + " units";
        writeObject.writeUTF(unitString);
      }
    } catch (IOException e) {
      System.out.println(IO_ERROR + e + "\n");
    }
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
    placementPhase();
  }
}
