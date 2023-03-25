package edu.duke.ece651.teamX.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppTest {
  /* @Test
    void test_GetMessage() {
      // App a = new App();
      // assertEquals("Hello from the client for teamX", a.getMessage());
      }*/

  /*@Test
  @ResourceLock(value = Resources.SYSTEM_OUT, mode = ResourceAccessMode.READ_WRITE)
  public void test_main() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes, true);
    InputStream input = getClass().getClassLoader().getResourceAsStream("input.txt");
    assertNotNull(input);
    InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("output.txt");
    assertNotNull(expectedStream);
    InputStream oldIn = System.in;
    PrintStream oldOut = System.out;
    try {
      System.setIn(input);
      System.setOut(out);
      App.main(new String[0]);
    } catch (IOException e) {
      return;
    }
    finally {
      System.setIn(oldIn);
      System.setOut(oldOut);
    }
    try {
      String expected = new String(expectedStream.readAllBytes());
      String actual = bytes.toString();
      assertEquals(expected, actual);
    } catch (IOException e) {
      return;
    }
    }*/

}
