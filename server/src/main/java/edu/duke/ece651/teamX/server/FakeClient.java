package edu.duke.ece651.teamX.server;

import java.io.*;
import java.net.*;
import edu.duke.ece651.teamX.shared.*;

public class FakeClient extends Thread{
  String ip;
  int port;
  Socket client_socket;
  // InputStream in;
  // OutputStream out;
  DataOutputStream writeObject;
  DataInputStream readObject;

  String IO_ERROR = "IO_ERROR :";
  String IE_ERROR = "IE_ERROR :";

  public FakeClient(String ip, int port) {
    this.ip = ip;
    this.port = port;
    //   this.in = in;
    // this.out = out;
  }

  public void connectToServer() {
    try {
     this.client_socket = new Socket(ip, 5000);
     this.readObject = new DataInputStream(client_socket.getInputStream());
     this.writeObject = new DataOutputStream(client_socket.getOutputStream());
    } catch (IOException e) {
      return;
    }
  }
    
    
  public void run() {
    connectToServer();
    initalizationPhase();
    placementPhase();
  }

  /**
   * This function is to grab the information from the server
   * The name of the player and the map
   */
  public void initalizationPhase() {
    try {

      // print the welcome message of the game
      readObject.readUTF();
      // print the player name
      readObject.readUTF();
      // print the map of the game
      readObject.readUTF();
      // print the territories for each player
      readObject.readUTF();

    } catch (IOException e) {
      return;
    }
  }

  public void placementPhase() {
    try {
      while(true) {
        // print the message for the placement phase
        readObject.readUTF();
        // placement phase
        for (int i = 0; i < 6; i++) {
          while(true) {
            readObject.readUTF();
            writeObject.writeUTF("5");
            String check = readObject.readUTF();
            if(check.equals("Done")) {
              break;
            } else {
              System.out.println(check);
              continue;
            }
          }
        }

        String check = readObject.readUTF();
        if(check.equals("Done")) {
          break;
        } else {
          // System.out.println(check);
          continue;
        }
      }
      readObject.readUTF();
      // print out the placement summary
      for (int i = 0; i < 6; i++) {
        readObject.readUTF();
      }
      // print out wait and game being statements
      readObject.readUTF();
      readObject.readUTF();
    } catch (IOException e) {
      System.out.println(IO_ERROR + e + "\n");
    }

    // playRestofGame();
  }

  /* public void playRestofGame() {
    String check = "";
    while(true) {
      turnPhase();
      printUpdate();
      try {
        check = readObject.readUTF();
      } catch (IOException e) {
        System.out.println(IO_ERROR + e + "\n");
      }
      if(check.equals("Loss")) {
        break;
      }
    }
    scan.close();
  }

  public void turnPhase() {
    try {
      // print the game map
      System.out.println(readObject.readUTF());

      // print the territories with the units to the client
      System.out.println(readObject.readUTF());

      
      while(true) {
        // print the prompt and take in the user's move
        System.out.println(readObject.readUTF());
        String option = scan.nextLine();
        writeObject.writeUTF(option);

        if(option.equals("m")) {
          // if move
          System.out.println(readObject.readUTF());
          String moveOrder = scan.nextLine();
          writeObject.writeUTF(moveOrder);

          if(moveOrder.length() == 0) {
            System.out.println(readObject.readUTF());
            continue;
          }
        }
        //if option is to commit
        else if(option.equals("c")) {
          String check = readObject.readUTF();
          if(check.equals("Waiting for other players to commit their moves...")) {
            System.out.println("\n\n" + check);
            break;
          } else {
            System.out.println(check);
            continue;
          }
        }
        //if option is to attack
        else if(option.equals("a")) {
          System.out.println(readObject.readUTF());
          String attackOrder = scan.nextLine();
          writeObject.writeUTF(attackOrder);

          if(attackOrder.length() == 0) {
            System.out.println(readObject.readUTF());
            continue;
          }
        }
        else {
          System.out.println(readObject.readUTF());
          continue;
        }
      }
    } catch (IOException e) {
      System.out.println(IO_ERROR + e + "\n");
    }
  }

  public void printUpdate() {
    try {
     System.out.println(readObject.readUTF());
    } catch(IOException e) {
      System.out.println(IO_ERROR + e + "\n");
    }
    }*/
}
