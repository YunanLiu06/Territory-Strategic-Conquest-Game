package edu.duke.ece651.teamX.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

public class TextPlayerTest {




    @Test
    public void test_player_basic() {
        TextPlayer a = new TextPlayer("playerA");
        TextPlayer b = new TextPlayer("playerb");
        Territory t = new Territory("TestTerritory");
        ArrayList<Territory> l = new ArrayList<Territory>();
        l.add(t);
        TextPlayer c = new TextPlayer("Playerc", l);
        Iterator<Territory> it = c.getTerritories();
        Territory newt = new Territory("placeholder");
        while(it.hasNext()){
            newt = it.next();
        }
        assertSame(t,newt);
        assertThrows(IllegalArgumentException.class, () -> c.addTerritory(t));

        assertEquals(a,a);
        assertNotEquals(a, b);
        assertNotEquals(a,t);
    }


    @Test
    public void test_player_move() {

        Territory t1 = new Territory("TestTerritory1");
        Territory t2= new Territory("TestTerritory2");
        Territory t3= new Territory("TestTerritory3");
        ArrayList<Territory> l = new ArrayList<Territory>();
        l.add(t1);
        l.add(t2);
        Soldier soldierInT1 = new Soldier( 10);
        Soldier soldierInT2 = new Soldier( 10);
        t1.addUnit(soldierInT1);
        t2.addUnit(soldierInT2);
        TextPlayer a = new TextPlayer("playerA",l);
        TextPlayer b = new TextPlayer("playerb");
        t3.initiateOnwer(b);
        a.tryMove(t1, t2, new Soldier(10));
        assertEquals(0, soldierInT1.getAmount());
        assertEquals(20, soldierInT2.getAmount());
        assertThrows(IllegalArgumentException.class, () -> a.tryMove(t1, t2, new Soldier(10)));
        assertThrows(IllegalArgumentException.class, () -> a.tryMove(t1, t3, new Soldier(10)));

        
    }

    
}
