package edu.duke.ece651.teamX.client;

import java.io.*;
import java.net.*;
import edu.duke.ece651.teamX.shared.*;

public class RiscClient {

  Socket client_socket;

  public RiscClient(Socket client_socket) {
    this.client_socket = client_socket;
  }

  public void run() {
    try {
      ClientIO clientIO = new ClientIO(client_socket.getInputStream(), client_socket.getOutputStream());

      clientIO.initalizationPhase();
      clientIO.placementPhase();
      client_socket.close();
    } catch (IOException e) {
      System.out.println("RUN Error: " + e + "\n");
    }
  }

}
