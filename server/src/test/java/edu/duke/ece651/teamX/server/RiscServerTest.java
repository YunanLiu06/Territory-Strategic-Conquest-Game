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
    FakeClient client1 = new FakeClient("vcm-30720.vm.duke.edu", 5000);
    FakeClient client2 = new FakeClient("vcm-30720.vm.duke.edu", 5000);

    client1.connectToServer();
    client2.connectToServer();
  }

}
