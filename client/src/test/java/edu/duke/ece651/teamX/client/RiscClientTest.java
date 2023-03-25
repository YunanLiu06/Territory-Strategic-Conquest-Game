package edu.duke.ece651.teamX.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.*;
import java.io.*;
import java.net.*;

public class RiscClientTest {
  @Test
  public void test_client() throws IOException {
    Thread server = new Thread(new FakeServer(2, new ServerSocket(5000)));
    server.start();
    RiscClient rc1 = new RiscClient(new Socket("127.0.0.1", 5000));
    RiscClient rc2 = new RiscClient(new Socket("127.0.0.1", 5000));
    InputStream in1 = rc1.client_socket.getInputStream();
    OutputStream out1 = rc1.client_socket.getOutputStream();
    DataOutputStream writeObject1 = new DataOutputStream(out1);
    DataInputStream readObject1 = new DataInputStream(in1);
    InputStream in2 = rc2.client_socket.getInputStream();
    OutputStream out2 = rc2.client_socket.getOutputStream();
    DataOutputStream writeObject2 = new DataOutputStream(out2);
    DataInputStream readObject2 = new DataInputStream(in2);
    
    /* rc1.run();
    rc2.run();


    readObject1.readUTF();
    writeObject1.writeUTF("5");
    writeObject1.writeUTF("5");
    writeObject1.writeUTF("5");
    writeObject1.writeUTF("5");
    writeObject1.writeUTF("5");

    readObject2.readUTF();
    writeObject2.writeUTF("5");
    writeObject2.writeUTF("5");
    writeObject2.writeUTF("5");
    writeObject2.writeUTF("5");
    writeObject2.writeUTF("5");*/
  }

}
