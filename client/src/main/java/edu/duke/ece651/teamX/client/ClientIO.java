package edu.duke.ece651.teamX.client;

import java.io.*;
import java.net.*;
import edu.duke.ece651.teamX.shared.*;

public class ClientIO {

  private InputStream in;
  private OutputStream out;
  private ObjectOutputStream writeObject;
  private ObjectInputStream readObject;

  static String CONSTRUCTOR_ERROR = "Constructor Error: ";

  static String IO_ERROR = "IO Error: ";

  public ClientIO(InputStream in, OutputStream out) {
    try {
      this.in = in;
      this.out = out;
      writeObject = new ObjectOutputStream(out);
      readObject = new ObjectInputStream(in);
    } catch (IOException e) {
      System.out.println(CONSTRUCTOR_ERROR + e + "\n");
    }

  }

  public void initalizationPhase() {
    try {

      // print the map from the Server
      String stringFromServer = (String) readObject.readObject();
      System.out.println(stringFromServer);
    } catch (IOException e) {
      System.out.println(IO_ERROR + e + "\n");
    } catch (ClassNotFoundException e) {
      System.out.println(IO_ERROR + e + "\n");
    }
  }

}
