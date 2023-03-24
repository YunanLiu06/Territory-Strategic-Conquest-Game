package edu.duke.ece651.teamX.server;

import edu.duke.ece651.teamX.shared.*;
import java.net.*;
import java.io.*;
import java.lang.Thread.State;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import static java.lang.Thread.sleep;

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
  Lock lock;
  Condition isReady;

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
    this.lock = new ReentrantLock();
    this.isReady = lock.newCondition();
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

  /* public void closeAllClients() {
    for(int i = 0; i < threads.size(); i++) {
      threads[i].close();
    }
    }*/

  /*
   * This method is the main loop of the RiscServer. It accepts request, and plays
   * the game.
   */
  public void run() throws IOException {
    int num = 1;
    Socket client_socket = null;
    while (num <= numPlayers) {
      
      try {
        // create a socket for the client
        client_socket = acceptOrNull();
        String name = playerNames.get(num - 1);
        // create an instance of ServerIO, which handles the IO for the server
        ServerIO serverIO = new ServerIO(client_socket, client_socket.getInputStream(),
                                         client_socket.getOutputStream(), name, gameMap, lock, isReady, num);
        threads.add(serverIO);
        // start the thread
        serverIO.start();
        num++;
      } catch (IOException e) {
        System.out.println("Server Networking Error: " + e);
        client_socket.close();
      }
    }
    try {
      synchThreads();
    } catch (InterruptedException e) {
      System.out.println("Interrupted Exception: " + e);
    }
  }

  // remaining things: synchronize the threads (a while loop checking each thread
  // (is it in await state))
  // if all threads in await state, they finish the last placement phase
  // need to call signal all to tell of the threads to change their await state
  // check if the game has a winner or loser, this while loop
  // call this synchronize threads after the while loop, keep synchronizing
  // threads

  /**
   * Function to synchronize all the client threads
   */
  public void synchThreads() throws InterruptedException {
    while (true) {
      while (!isWaiting()) {
      }
      sleep(100);
      lock.lock();
      isReady.signalAll();
      lock.unlock();
      Boolean endGame = true;
      for(ServerIO t: threads) {
        endGame &= !t.getConnected();
      }
      if(endGame) {
        break;
      }
    }
  }

  /**
   * This function checks if any threads are waiting
   * 
   * @returns true if a thread is waiting and is also connected
   * @returns false if there aren't any threads waiting
   */
  public Boolean isWaiting() {
    for(ServerIO s: threads) {
      if (s.getState() != State.WAITING && s.getConnected() == true) {
        return false;
      }
    }
    return true;
  }

}
