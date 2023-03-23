package edu.duke.ece651.teamX.client;

import java.io.*;
import java.net.*;
import java.util.*;
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
  private Scanner scan;

  static String CONSTRUCTOR_ERROR = "Constructor Error: ";

  static String IO_ERROR = "IO Error: ";

  public ClientIO(InputStream in, OutputStream out) {
    this.in = in;
    this.out = out;
    writeObject = new DataOutputStream(out);
    readObject = new DataInputStream(in);
    scan = new Scanner(System.in);
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
      // print the territories for each player
      System.out.println(readObject.readUTF());

    } catch (IOException e) {
      System.out.println(IO_ERROR + e + "\n");
    }
  }

  public void placementPhase() {
    try {
      while(true) {
        // print the message for the placement phase
        System.out.println(readObject.readUTF());
        // placement phase
        for (int i = 0; i < 6; i++) {
          while(true) {
            System.out.println(readObject.readUTF());
            writeObject.writeUTF(scan.nextLine());
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
          System.out.println(check);
          continue;
        }
      }
      System.out.println(readObject.readUTF());
      // print out the placement summary
      for (int i = 0; i < 6; i++) {
        System.out.println(readObject.readUTF());
      }
      // print out wait and game being statements
      System.out.println(readObject.readUTF());
      System.out.println(readObject.readUTF());
    } catch (IOException e) {
      System.out.println(IO_ERROR + e + "\n");
    }
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
      scan.close();
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
  }
    
}
