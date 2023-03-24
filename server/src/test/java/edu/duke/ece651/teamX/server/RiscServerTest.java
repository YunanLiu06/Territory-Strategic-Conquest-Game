package edu.duke.ece651.teamX.server;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.io.*;
import java.lang.*;
import java.util.*;
import java.net.*;

public class RiscServerTest {
  @Test
  public void test_RiscServer() throws IOException, InterruptedException {
    RiscServer server = new RiscServer(2, new ServerSocket(5000));
    // server.run();
    Thread client1 = new Thread(new FakeClient("127.0.0.1", 5000));
    Thread client2 = new Thread(new FakeClient2("127.0.0.1", 5000));
    
    client1.start();
    client2.start();
    server.run();
    //Thread.sleep(50);

    client1.join();
    client2.join();
  }

}
