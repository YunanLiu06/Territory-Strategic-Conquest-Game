package edu.duke.ece651.teamX.server;

import edu.duke.ece651.teamX.shared.*;
import java.net.*;
import java.io.*;
import java.util.LinkedList;

/**
 * This class handles the input and output for the Risc game.
 */
public class ServerIO {

  private InputStream in;
  private OutputStream out;
  private ObjectOutputStream writeObject;
  private ObjectInputStream readObject;

 
  // string to state that there was an IOException in ther server
  static String CONSTRUCTOR_ERROR = "Constructor Error: ";

  // strign to state that there was an IOError
  static String IO_ERROR = "IO Error: ";

  public ServerIO(InputStream in, OutputStream out) {
    try {
      this.in = in;
      this.out = out;
      writeObject = new ObjectOutputStream(out);
      readObject = new ObjectInputStream(in);
    } catch (IOException e) {
      System.out.println(CONSTRUCTOR_ERROR + e + "\n");
    }
  }

  /**
   * Private helper function to return a string of the text map
   */
  private String printTextMap() {
    MapGenerator mapGenerator = new FixMapGenerator();
    GameMap gameMap = mapGenerator.createMap(2);
    MapView<String> mapView = new TextMapView();
    String view = mapView.printMap(gameMap);
    return view;
  }

  /**
   * This function is the initialization phase of the game
   It welcomes the users to the game, gets how many players are in the game,
   
   */
  public void initializationPhase() {
    try {

      // generate Map and send to Client so they can print and exit
      String view = printTextMap();
      writeObject.writeObject(view);

    } catch (IOException e) {
      System.out.println(IO_ERROR + e + "\n");
    }
  }
}
