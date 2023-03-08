package edu.duke.ece651.teamX.server;

import edu.duke.ece651.teamX.shared.*;
import java.net.*;
import java.io.*;
import java.util.LinkedList;

/**
 * This class is the RiscServer. This class represents the server for the
 * Risc game. An instance of this class holds a ServerSocket. The run method
 * listens on that socket , accepts the clients, and handles the game*
 */
public class RiscServer {

  ServerSocket server_socket;

  /**
   * This constructs a RiscServer with the specified ServerSocket.
   * 
   * @param sock: the server socket to listen on
   */
  public RiscServer(ServerSocket server_socket) {
    this.server_socket = server_socket;
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
  public void run() {
    try {
      final Socket client_socket = acceptOrNull();

      // create an instance of ServerIO, which handles the IO for the server
      ServerIO serverIO = new ServerIO(client_socket.getInputStream(), client_socket.getOutputStream());
      serverIO.initializationPhase();
      client_socket.close();
      server_socket.close();
    } catch (IOException e) {
      System.out.println("Server Networking Error: " + e);
    }
  }

}
