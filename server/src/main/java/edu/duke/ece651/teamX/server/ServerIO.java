package edu.duke.ece651.teamX.server;

import edu.duke.ece651.teamX.shared.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.locks.*;
import javax.xml.crypto.Data;

import com.google.common.collect.Iterators;

/**
 * This class handles the input and output for the Risc game.
 * 
 * @param socket: the socket of the client
 * @param in:     the input stream of this specfic socket
 * @param out:    the output stram of this specific socket
 * @param name:   the name of the player
 * @param map:    the map of the game
 */
public class ServerIO extends Thread {
  private Socket socket;
  private InputStream in;
  private OutputStream out;
  private DataOutputStream writeObject;
  private DataInputStream readObject;
  private String name;
  private GameMap gameMap;
  private Player player;
  private Lock lock;
  private Condition isReady;
  private Boolean isConnected;
  private ArrayList<String> playerMoves;

  // string to state that there was an IOException in ther server
  static String CONSTRUCTOR_ERROR = "Constructor Error: ";

  // strign to state that there was an IOError
  static String IO_ERROR = "IO Error: ";

  // string to state there was an Interrupted Exception Error
  static String IE_ERROR = "Interrupted Exception Error: ";

  /**
   * Constructor
   * 
   * @param socket:  the socket of the client
   * @param in:      input stream to take in from the client
   * @param out:     output stream to print to the client
   * @param gameMap: map of territories of the game
   * @param lock:    a lock to lock the critical sections of the code
   * @param isReady
   */
  public ServerIO(Socket socket, InputStream in, OutputStream out, String name, GameMap gameMap, Lock lock,
      Condition isReady) {
    // initialize IO
    this.socket = socket;
    this.in = in;
    this.out = out;
    writeObject = new DataOutputStream(out);
    readObject = new DataInputStream(in);
    // intialize other things for the game
    this.name = name;
    this.gameMap = gameMap;
    player = new TextPlayer(name);
    gameMap.assignToPlayer(player);
    this.lock = lock;
    this.isReady = isReady;
    this.isConnected = true;
    playerMoves = new ArrayList<String>();
  }

  /**
   * return if the client is connected
   */
  public Boolean getIsConnected() {
    return isConnected;
  }

  /**
   * Private helper function to return a string of the text map
   */
  private String printTextMap() {
    MapView<String> mapView = new TextMapView();
    String view = mapView.printMap(gameMap);
    return view;
  }

  /**
   * private helper function to get the number of units in a territory
   */
  private int getUnitNum(Territory t) {
    int count = 0;
    Iterator<Unit> it = t.getUnits();
    try {
      Unit unit = it.next();
      return unit.getAmount();
    } catch(NoSuchElementException e) {
      return 0;
    }
  }

  /**
     private helper function to reset the units in each territory
     if the user has to complete the Placement Phase again
   */
  private void resetUnits() {
    Iterator<Territory> it = player.getTerritories();
    while(it.hasNext()) {
      Territory t = it.next();
      Iterator<Unit> it2 = t.getUnits();
      Unit unit = it2.next();
      t.substractUnit(unit);
    }
  }

  /**
   * This function is for the placement phase of the game.
   * It allows the clients to place their territories
   */
  public void placementPhase() {
    int unitNum = 30;
    
    try {
      while(true) {
        // tell the user they are in the placement phase
        String initiatePlacement = "\n\nYou are now in the placement phase of the game. This means you will be given 30 units and you will place\n"
          + "them amongst your territories. Each territory must have units (you can't place 0).";
        writeObject.writeUTF(initiatePlacement);

        // for each territory ask the clients to place their units
        Iterator<Territory> it = player.getTerritories();
        while (it.hasNext()) {
          Territory t = it.next();
          while(true) {
            String prompt = "\nHow many units do you want to place in territory " + t.getName() + "?";
            writeObject.writeUTF(prompt);

            String temp = readObject.readUTF();
            int units = 0;
            if(temp.length() == 0) {
              units = 0;
            }
            try {
              units = Integer.parseInt(temp);
            } catch (NumberFormatException e) {
              units = 0;
            }
            //check if unit number being passed in is > 0 and < 30
            if(units > 0 && units <= unitNum) {
              Soldier unit = new Soldier(units);
              t.addUnit(unit);
              unitNum = unitNum - units;
              writeObject.writeUTF("Done");
              break;
            } else {
              writeObject.writeUTF("\nYou're trying to place either 0 units or more units than you have available");
              continue;
            }
          }
        }

        if(unitNum != 0) {
          writeObject.writeUTF("\nERROR: YOU DIDN'T PLACE ALL 30 UNITS. PLACEMENT PHASE WILL BEGIN AGAIN: ");
          unitNum = 30;
          resetUnits();
          continue;
        } else {
          writeObject.writeUTF("Done");
          break;
        }
      }
        // print out the unit information to the client
      Iterator<Territory> it2 = player.getTerritories();
      String prompt = "\nYour placements are as follows: ";
      writeObject.writeUTF(prompt);
      while (it2.hasNext()) {
        Territory t = it2.next();
        int size = getUnitNum(t);
        String unitString = " " + t.getName() + ": " + size + " units";
        writeObject.writeUTF(unitString);
      }
        
      // tell the players that they are waiting on other players
      writeObject.writeUTF("\nOther players are still placing their units. Please wait...");
      lock.lock();
      isReady.await();
      lock.unlock();
      writeObject.writeUTF("\nDone with placement phase. The game will now begin!");
    } catch (IOException e) {
      System.out.println(IO_ERROR + e + "\n");
    } catch (InterruptedException e) {
      System.out.println(IE_ERROR + e + "\n");
    }
  }

  /**
   * This function is the initialization phase of the game
   * It welcomes the users to the game, and prints the map
   * to the players
   */
  public void initializationPhase() {
    try {
      writeObject.writeUTF("\nWelcome to the Game of Risc.");
      writeObject.writeUTF("Your Player Name: " + player.getName() + "\n" + "Here is the map of the game\n");
      // generate Map and send to Client so they can print
      String view = printTextMap();
      writeObject.writeUTF(view);
      // get the players territories and print them to the client
      writeObject.writeUTF(printTerritories());

    } catch (IOException e) {
      System.out.println(IO_ERROR + e + "\n");
    }
  }

  private String printTerritories() {
    String territory = "\nThis is a list of your territories: \n";
    String toAdd = "\n";
    Iterator<Territory> it = player.getTerritories();
    while (it.hasNext()) {
      Territory t = it.next();
      String element = t.getName();
      Iterator<Territory> adj = gameMap.getAdjacentTerritories(t);
      toAdd += " " + element + " (next to: ";
      while (adj.hasNext()) {
        String adjTerritory = adj.next().getName();
        if (adj.hasNext()) {
          toAdd += adjTerritory + ", ";
        } else {
          toAdd += adjTerritory;
        }
      }
      toAdd += ")\n";
    }

    return territory + toAdd;
  }

  /**
   * This function is to print the users territories along with the units in each
   * one
   */
  private String printTerritoriesAndUnits() {
    String territory = "\nThis is a list of your territories: \n";
    String toAdd = "\n";
    Iterator<Territory> it = player.getTerritories();
    while (it.hasNext()) {
      Territory t = it.next();
      String element = t.getName();
      Iterator<Territory> adj = gameMap.getAdjacentTerritories(t);
      int units = getUnitNum(t);
      toAdd += " " + units + " units in " + element + " (next to: ";
      while (adj.hasNext()) {
        String adjTerritory = adj.next().getName();
        if (adj.hasNext()) {
          toAdd += adjTerritory + ", ";
        } else {
          toAdd += adjTerritory;
        }
      }
      toAdd += ")\n";
    }

    return territory + toAdd;
  }

  /**
   * private helper function to get the territory based on the name
   */
  private Territory getTerritory(String name) {
    Iterator<Territory> it = player.getTerritories();
    while (it.hasNext()) {
      Territory t = it.next();
      if (t.getName().equals(name)) {
        return t;
      }
    }

    return null;
  }

  /**
   * function to check move orders for client
   */
  private Boolean checkMoves(ArrayList<String> playerMoves) {
      for(int i = 0; i < playerMoves.size(); i++) {
        RuleChecker playerMoveRuleChecker;
        // split the move order and pass into tryMove
        String moveOrder = playerMoves.get(i);
        String[] split = moveOrder.split(" ");
        int amount = Integer.parseInt(split[2]);
        Territory from = gameMap.getTerritoryByName(split[0]);
        Territory to = gameMap.getTerritoryByName(split[1]);
        playerMoveRuleChecker = new PlayerMoveRuleChecker(from,to,amount);
        if(playerMoveRuleChecker.checkRule() == false) {
          // player.tryMove(from, to, new Soldier(amount));
          return false;
        }
      }

      return true;
  }

  /**
     private helper function to do the moves once they're validated
   */
  private void doMoves(ArrayList<String> playerMoves) {
    for(int i = 0; i < playerMoves.size(); i++) {
        String moveOrder = playerMoves.get(i);
        String[] split = moveOrder.split(" ");

        int amount = Integer.parseInt(split[2]);
        Territory from = gameMap.getTerritoryByName(split[0]);
        Territory to = gameMap.getTerritoryByName(split[1]);
       
        player.tryMove(from, to, new Soldier(amount));
      }
    
  }

  /**
   * function to attack for the client
   */
  public void attack(String attackOrder) {
    //split the attack order
    String[] split = attackOrder.split(" ");
    int amount = Integer.parseInt(split[2]);
    Territory from = gameMap.getTerritoryByName(split[0]);
    Territory to = gameMap.getTerritoryByName(split[1]);

    //pass fire into territory
    player.fire(from, to, new Soldier(amount));
  }

  /**
   * This funciton is to implement the rest of the game after the
   * intialization phase and placement phase.
   * The rest of the game will be played in this function:
   * for each turn, the client is able to commit their moves until a player loses
   */
  public void turnPhase() {
    try {
      writeObject.writeUTF("\nGame Map: \n" + printTextMap() + "\n");
      writeObject.writeUTF(printTerritoriesAndUnits() + "\n");

      //may want to clear the array list

      while(true) {
        //prompt the user for what action they want to commit 
        writeObject.writeUTF("\nWhat order would you like to do? Enter m for move, a for attack, and c for commit");
        String choice = readObject.readUTF();

        if(choice.length() == 0 || choice.length() > 1) {
          writeObject.writeUTF("\nERROR: CHOICE IS NOT VALID");
          continue;
        }

        if (choice.equals("m")) {
            // prompt the user
          writeObject.writeUTF("Enter the move: <territory from> <territory to> <unit amount>");
          String moveOrder = readObject.readUTF();
          if(moveOrder.length() == 0) {
            writeObject.writeUTF("\nERROR: <territory from> <territory to> <unit amount>");
            continue;
          } else {
            playerMoves.add(moveOrder);
          }
                    
        } else if (choice.equals("a")) {
          //prompt the user
          writeObject.writeUTF("Enter the attack: <territory from> <territory to> <unit amount>");
          String attackOrder = readObject.readUTF();
          if(attackOrder.length() == 0) {
            writeObject.writeUTF("\nERROR: <territory from> <territory to> <unit amount>");
            continue;

          } else {
            attack(attackOrder);
          }

        } else if (choice.equals("c")) {
          //error check moves and attacks
          //if anything is wrong, redo turn
          //if everything is fine, wait for other players to commit moves
          if(!playerMoves.isEmpty()) {
            if(!checkMoves(playerMoves)) {
              playerMoves.clear();
              writeObject.writeUTF("\nERROR: YOU ENTERED INVALID ORDERS. RE-ENTER ALL ORDERS.");
              //may have to change
              continue;
            }
          }

          writeObject.writeUTF("Waiting for other players to commit their moves...");
          lock.lock();
          isReady.await();
          lock.unlock();
          //do player's orders
          doMoves(playerMoves);
          gameMap.handleAllFires();
          writeObject.writeUTF("\n" + printTerritoriesAndUnits() + "\n");
          break;

        } else {
          writeObject.writeUTF("\nERROR: CHOICE IS NOT VALID");
          continue;
        }
      }
    } catch (IOException e) {
      System.out.println(IO_ERROR + e + "\n");
    } catch (InterruptedException e) {
      System.out.println(IE_ERROR + e + "\n");
    }
  }

  @Override
  public void run() {
    initializationPhase();
    placementPhase();
    turnPhase();
  }
}
