/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ece651.teamX.server;

import java.net.*;
import java.io.*;
import java.util.*;
import edu.duke.ece651.teamX.shared.*;

/**
 * This class represents the entry point for the game RISC.
 * The main function creates a server for the game, greets the user, and asks
 * how many players will be playing the game. From there, the server builds the
 * game, and the game is played.
 * The game has a server and a scanner to take in the amount of players
 */
public class App {

  Scanner scan = new Scanner(System.in);

  // string to greet the clients to the game
  static String GREETING = "WELCOME TO THE GAME OF RISC! Before we begin, we need some information to set up the game.\n";

  // string to get the number of players
  static String PLAYER_PROMPT = "How many players are playing? Our current system supports 2,3, and 4.\n";

  public App() {
  }

  private Integer welcomePhase() {
    System.out.println("\n" + GREETING + PLAYER_PROMPT);
    return Integer.parseInt(scan.nextLine());
  }

  /**
   * Create a RiscServer, take in the number of players, run the game
   */
  public static void main(String[] args) throws IOException {
    // create App
    App a = new App();

    // NEED: need to come back and check if the number passed in was not a number
    Integer numPlayers = -1;
    int counter = 0;
    while (numPlayers < 2 || numPlayers > 4) {
      if (counter > 0) {
        System.out.println(
            "Our system does not currently support that number of players. Please enter the number 2,3, or 4.\n");
      }
      numPlayers = a.welcomePhase();
      counter++;
    }

    // create RiscServer
    System.out.println("\nBuilding the game...");
    System.out.println("Players can now connect to the game from their machines.\n");
    RiscServer rs = new RiscServer(numPlayers, new ServerSocket(5000));
    rs.run();
  }
}
