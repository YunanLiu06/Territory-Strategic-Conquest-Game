package edu.duke.ece651.teamX.server;

import edu.duke.ece651.teamX.shared.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * This class is the RiscServer. This class represents the server for the
 * Risc game. An instance of this class holds a ServerSocket. The run method
 * listens on that socket , accepts the clients, and handles the game.
 * 
 * @param server_socket: the socket that the server listens on to accept
 *                       connections from clients
 * @param numPlayers:    the number of players in the game
 */
public class RiscServer {

  ServerSocket server_socket;
  Integer numPlayers;
  ArrayList<ServerIO> threads;
  MapGenerator mapGenerator = new FixMapGenerator();
  GameMap gameMap;
  ArrayList<String> playerNames = new ArrayList<String>();

  /**
   * This constructs a RiscServer with the specified ServerSocket.
   * 
   * @param sock:       the server socket to listen on
   * @param numPlayers: the number of Players in the game
   */
  public RiscServer(Integer numPlayers, ServerSocket server_socket) {
    this.server_socket = server_socket;
    this.numPlayers = numPlayers;
    this.threads = new ArrayList<ServerIO>();
    this.gameMap = mapGenerator.createMap(this.numPlayers);
    playerNames.add("BLUE");
    playerNames.add("GREEN");
    playerNames.add("RED");
    playerNames.add("BLACK");
  }

  /**
   * This is a helper method to accept a socket from the ServerSocket
   */
  private Socket acceptOrNull() {
    try {
      return server_socket.accept();
    } catch (IOException e) {
      return null;
    }
  }

  /*
   * This method is the main loop of the RiscServer. It accepts request, and plays
   * the game.
   */
  public void run() throws IOException {
    int num = 1;
    while (num <= numPlayers) {

      try {
        // create a socket for the client
        final Socket client_socket = acceptOrNull();
        String name = playerNames.get(num - 1);
        // create an instance of ServerIO, which handles the IO for the server
        ServerIO serverIO = new ServerIO(client_socket, client_socket.getInputStream(),
            client_socket.getOutputStream(), name, gameMap);
        threads.add(serverIO);
        // start the thread
        serverIO.start();
        num++;
      } catch (IOException e) {
        System.out.println("Server Networking Error: " + e);
      }
    }

    server_socket.close();
  }

}
