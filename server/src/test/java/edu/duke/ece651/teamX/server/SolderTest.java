package edu.duke.ece651.teamX.server;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
public class SolderTest {
    @Test
    public void test_soldier_basic() {
        Soldier s = new Soldier(10);
        assertEquals(s.getAmount(),10);
        s.addAmount(5);
        assertEquals(s.getAmount(),15);
        s.substractAmount(20);
        assertEquals(s.getAmount(),0);

    }
    
}
