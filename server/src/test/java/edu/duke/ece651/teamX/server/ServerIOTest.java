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
    // assertEquals(null, s.doMoves(moves));
    assertFalse(s.checkMoves(moves));
  }

  @Test
  public void test_checkAttacks() {
    ServerIO s = getTestServerIO();
    ArrayList<String> attacks = new ArrayList<String>();
    String a1 = "Roshar Mordor 5";
    attacks.add(a1);
    assertThrows(IllegalArgumentException.class, () -> {s.doAttacks(attacks);});
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

  @Test
  public void test_printStringMap() {
    ServerIO s = getTestServerIO();
    assertEquals(s.printTextMap(), s.printTextMap());
  }

  @Test
  public void test_getUnitNum() {
    ServerIO s = getTestServerIO();
    Territory Roshar = new Territory("Roshar");
    assertEquals(0, s.getUnitNum(Roshar));
  }

  @Test
  public void test_printTerritories() {
    ServerIO s = getTestServerIO();
    //assertThrows(NullPointerException.class, () -> {s.printTerritories();});
    String expected = "\nThis is a list of your territories: \n";
    String toAdd = "\n";
    String first = " Narnia (next to: Midkemia, Roshar)\n";
    String second = " Midkemia (next to: Narnia, Oz, Mordor)\n";
    String third = " Oz (next to: Midkemia, Gondor, Hogwarts)\n";
    String fourth = " Gondor (next to: Oz, Elantris, Agrabah)\n";
    String fifth = " Elantris (next to: Gondor, Scadrial, Atlantica)\n";
    String sixth = " Scadrial (next to: Elantris, Brigadoon)\n";
    String test = expected + toAdd + first + second + third + fourth + fifth + sixth;
    assertEquals(test, s.printTerritories());
  }

  @Test
  public void test_printTerritorieswithUnits() {
    ServerIO s = getTestServerIO();
    //assertThrows(NullPointerException.class, () -> {s.printTerritories();});
    String expected = "\nThis is a list of your territories: \n";
    String toAdd = "\n";
    String first = " 0 units in Narnia (next to: Midkemia, Roshar)\n";
    String second = " 0 units in Midkemia (next to: Narnia, Oz, Mordor)\n";
    String third = " 0 units in Oz (next to: Midkemia, Gondor, Hogwarts)\n";
    String fourth = " 0 units in Gondor (next to: Oz, Elantris, Agrabah)\n";
    String fifth = " 0 units in Elantris (next to: Gondor, Scadrial, Atlantica)\n";
    String sixth = " 0 units in Scadrial (next to: Elantris, Brigadoon)\n";
    String test = expected + toAdd + first + second + third + fourth + fifth + sixth;
    assertEquals(test, s.printTerritoriesAndUnits());
  }

}
