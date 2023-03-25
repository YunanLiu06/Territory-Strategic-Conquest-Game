package edu.duke.ece651.teamX.server;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.io.*;

import edu.duke.ece651.teamX.shared.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.locks.*;
import javax.xml.crypto.Data;

public class ServerIOTest {
  private ServerIO getTestServerIO() {
    Socket socket = null;
    InputStream in = System.in;
    OutputStream out = System.out;
    DataOutputStream writeObject = new DataOutputStream(out);
    DataInputStream readObject = new DataInputStream(in);
    String name = "GREEN";
    MapGenerator mapGenerator = new FixMapGenerator();
    GameMap gameMap = mapGenerator.createMap(2);
    Lock lock = new ReentrantLock();
    Condition isReady = lock.newCondition();

    ServerIO s = new ServerIO(socket, in, out, name, gameMap, lock, isReady, 1);
    return s;
  }
  
  @Test
  public void test_checkMoves() {
    ServerIO s = getTestServerIO();
    ArrayList<String> moves = new ArrayList<String>();
    String m1 = "Roshar Mordor 5";
    moves.add(m1);
    assertFalse(s.checkMoves(moves));
  }

  @Test
  public void test_checkAttacks() {
    ServerIO s = getTestServerIO();
    ArrayList<String> attacks = new ArrayList<String>();
    String a1 = "Roshar Mordor 5";
    attacks.add(a1);
    assertFalse(s.checkAttacks(attacks));
  }

  @Test
  public void test_getTerritory() {
    ServerIO s = getTestServerIO();
    assertNull(s.getTerritory("Roshar"));
  }

  @Test
  public void test_getConnected() {
    ServerIO s = getTestServerIO();
    assertTrue(s.getConnected());
  }

}
