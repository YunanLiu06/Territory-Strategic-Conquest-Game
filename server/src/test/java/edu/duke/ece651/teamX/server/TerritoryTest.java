package edu.duke.ece651.teamX.server;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class TerritoryTest {
    @Test
    public void test_territory_basic() {
        TextPlayer playerA = new TextPlayer("PlayerA");
        Territory a = new Territory("TerritoryA");
        TextPlayer playB = new TextPlayer("PlayerB");
        Territory b = new Territory("TerritoryB",playB);
        Territory c = new Territory("TerritoryA");
        ArrayList<Territory> l = new ArrayList<Territory>();
        l.add(c);
        TextPlayer wrongPlayer = new TextPlayer("wrongPlayer",l);
        assertThrows(IllegalArgumentException.class, () -> c.initiateOnwer(wrongPlayer));
        a.initiateOnwer(playerA);
        assertThrows(IllegalArgumentException.class, () -> a.initiateOnwer(playerA));

        
        


        assertEquals(a, a);
        assertNotEquals(a, b);
        assertNotEquals(a, playerA);

    }

    @Test
    public void test_add_and_sub_unit(){
        TextPlayer playB = new TextPlayer("PlayerB");
        Territory b = new Territory("TerritoryB",playB);
        Soldier s1 = new Soldier( 10);
        assertThrows(IllegalArgumentException.class, () -> b.substractUnit(s1));
        Soldier s2 = new Soldier(21);
        Soldier oneSoldier = new Soldier(1);
        b.addUnit(s1);
        b.addUnit(s1);
        assertThrows(IllegalArgumentException.class, () -> b.substractUnit(s2));
        b.substractUnit(s1);
        b.substractUnit(s1);
        assertThrows(IllegalArgumentException.class, () -> b.substractUnit(oneSoldier));

        
    }
}
