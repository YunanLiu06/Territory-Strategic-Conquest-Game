package edu.duke.ece651.teamX.client;

import java.io.*;
import java.net.*;
import edu.duke.ece651.teamX.shared.*;

/**
 * This class is represents the Client. The client communicates
 * back and forth with the server throughout the game.
 */
public class Client {

  private Socket socket = null;

  public Client(String address, int port) {

    try {
      // create socket
      socket = new Socket(address, port);
      // get and print message from server and exit
      InputStream in = socket.getInputStream();
      ObjectInputStream oin = new ObjectInputStream(in);
      String stringFromServer = (String) oin.readObject();
      System.out.println(stringFromServer);
      in.close();
      socket.close();

    } catch (UnknownHostException e) {
      System.out.println(e);
      return;
    } catch (IOException e) {
      System.out.println(e);
      return;
    } catch (ClassNotFoundException e) {
      System.out.println(e);
    }
  }
}
