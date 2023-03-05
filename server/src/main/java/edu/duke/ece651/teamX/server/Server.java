package edu.duke.ece651.teamX.server;

import edu.duke.ece651.teamX.shared.*;
import java.net.*;
import java.io.*;
import java.util.LinkedList;

/**
 * This class is the Server Class for the Game RISK
 * This class creates a socket, connects to its clients (the players of the
 * game)
 * and handles certain tasks
 */

public class Server {

  private Socket socket = null;
  private ServerSocket server = null;

  public Server(int port) {
    try {
      // create a server socket
      server = new ServerSocket(port);
      // accept connection from client
      socket = server.accept();
      // get output streams to send client message
      OutputStream out = socket.getOutputStream();
      ObjectOutputStream oout = new ObjectOutputStream(out);

      // generate Map and send to Client so they can print and exit
      LinkedList<String> territoryNames = new LinkedList<>();
      territoryNames.add("Hogwarts");
      GameMap gameMap = new GameMap(1, territoryNames, null);
      // private: Territory territory = Map.getTerritoryByIndex(0, 1);
      Territory territory = new Territory("Hogwarts");

      String player = "\nExample Player:\n";
      String line = "---------------------------\n";
      String territoryName = "4 units in " + territory.getName() + "\n";

      oout.writeObject(player + line + territoryName);
      // close everything
      oout.close();
      socket.close();
      server.close();
    }

    catch (IOException e) {
      System.out.println("Server Error: " + e);
    }
  }
}
