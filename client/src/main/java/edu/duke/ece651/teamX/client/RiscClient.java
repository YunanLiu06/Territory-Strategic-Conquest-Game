package edu.duke.ece651.teamX.client;

import java.io.*;
import java.net.*;
import edu.duke.ece651.teamX.shared.*;

/**
 * Class to represent the clients of the game
 * Has a socket, and runs the necessary functions to play the game:
 * initalizationPhase, placementPhase, and turnPhase
 */
public class RiscClient {

  Socket client_socket;

  /**
   * Constructor: initalizes the client socket
   */
  public RiscClient(Socket client_socket) {
    this.client_socket = client_socket;
  }

  /**
   * Function to call the necessary functions to play the game:
   * initializationPhase
   * placementPhase
   * turnPhase
   * and close the socket once finished
   */
  public void run() {
    try {
      ClientIO clientIO = new ClientIO(client_socket.getInputStream(), client_socket.getOutputStream());

      clientIO.initalizationPhase();
      clientIO.placementPhase();
      clientIO.turnPhase();
      client_socket.close();
    } catch (IOException e) {
      System.out.println("RUN Error: " + e + "\n");
    }
  }

}
