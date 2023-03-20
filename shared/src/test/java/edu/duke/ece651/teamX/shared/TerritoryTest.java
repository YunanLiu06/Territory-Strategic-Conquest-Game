package edu.duke.ece651.teamX.shared;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;

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

        a.addAdjacentTerritory(b);
        a.addAdjacentTerritory(b);
        int counter = 0;
        Iterator<Territory> it = a.getAdjacentTerritories();
        while(it.hasNext()){
            assertSame(it.next(), b);
            counter+=1;
        }
        assertEquals(1, counter);

    }

    @Test
    public void test_add_and_sub_unit(){
        TextPlayer playB = new TextPlayer("PlayerB");
        Territory b = new Territory("TerritoryB",playB);
        Soldier s1 = new Soldier( 10);
        Soldier s1_1 = new Soldier( 10);
        assertThrows(IllegalArgumentException.class, () -> b.substractUnit(s1));
        Soldier s2 = new Soldier(21);
        Soldier oneSoldier = new Soldier(1);
        b.addUnit(s1);
        b.addUnit(s1_1);
        assertEquals(20, s1.getAmount());
        Iterator<Unit> it = b.getUnits();
        while(it.hasNext()){
            Unit u = it.next();
            assertEquals(Soldier.class,u.getClass());
            assertEquals(u.getAmount(), 20);
        }

        assertThrows(IllegalArgumentException.class, () -> b.substractUnit(s2));
        b.substractUnit(s1_1);
        b.substractUnit(s1_1);
        assertThrows(IllegalArgumentException.class, () -> b.substractUnit(oneSoldier));

        
    }


    @Test
    public void test_fire_normal(){

        TextPlayer playA = new TextPlayer("PlayerA");
        TextPlayer playB = new TextPlayer("PlayerB");
        TextPlayer playC = new TextPlayer("PlayerC");
        Territory a = new Territory("TerritoryA",playA);
        playA.addTerritory(a);
        Soldier s1 = new Soldier( 10);
        Soldier s2 = new Soldier( 10);
        Soldier s3 = new Soldier( 10);
        a.addUnit(s1);
        
        ArrayList<Unit> units2 = new ArrayList<Unit>();
        units2.add(s2);

        
        ArrayList<Unit> units3 = new ArrayList<Unit>();
        units3.add(s3);

        a.addFireSource(playB, units2);
        a.addFireSource(playC, units3);

        a.handleFire();
        // assertSame(playA, a.getOwner());

        
    }

    @Test
    public void test_fire_no_defense(){

        TextPlayer playA = new TextPlayer("PlayerA");
        TextPlayer playB = new TextPlayer("PlayerB");
        TextPlayer playC = new TextPlayer("PlayerC");
        Territory a = new Territory("TerritoryA",playA);
        playA.addTerritory(a);
        Soldier s2 = new Soldier( 10);
        Soldier s3 = new Soldier( 10);

        ArrayList<Unit> units2 = new ArrayList<Unit>();
        units2.add(s2);

        
        ArrayList<Unit> units3 = new ArrayList<Unit>();
        units3.add(s3);

        a.addFireSource(playB, units2);
        a.addFireSource(playC, units3);

        a.handleFire();
        // assertSame(playC, a.getOwner());

        
    }
}
