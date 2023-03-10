package edu.duke.ece651.teamX.client;

import java.io.*;
import java.net.*;
import edu.duke.ece651.teamX.shared.*;

/**
 * This class is supposed to represent the client of the game
 * 
 * @param InputStream:  to take in input from server
 * @param OutputStream: to write output to the client
 */
public class ClientIO {

  private InputStream in;
  private OutputStream out;
  private DataOutputStream writeObject;
  private DataInputStream readObject;

  static String CONSTRUCTOR_ERROR = "Constructor Error: ";

  static String IO_ERROR = "IO Error: ";

  public ClientIO(InputStream in, OutputStream out) {
    this.in = in;
    this.out = out;
    writeObject = new DataOutputStream(out);
    readObject = new DataInputStream(in);
  }

  /**
   * This function is to grab the information from the server
   * The name of the player and the map
   */
  public void initalizationPhase() {
    try {

      // print the welcome message of the game
      System.out.println(readObject.readUTF());
      // print the player name
      System.out.println(readObject.readUTF());
      // print the map of the game
      System.out.println(readObject.readUTF());

    } catch (IOException e) {
      System.out.println(IO_ERROR + e + "\n");
    }
  }

}
