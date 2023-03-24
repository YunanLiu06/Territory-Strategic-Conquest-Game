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
  public void test_server() {
    ServerIO s = getTestServerIO();
  }

}
