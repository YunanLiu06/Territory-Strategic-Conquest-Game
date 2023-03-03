package edu.duke.ece651.teamX.server;

import java.util.ArrayList;

public abstract class Player {
    protected String name;
    protected int numOfTerritory;
    protected ArrayList<Territory> myTerritories = new ArrayList<Territory> ();

    /**
     * constructor only name
     * @param name
     */
    public Player(String name){
        this.name = name;
    }

    /**
     * constructor name and Territories
     * @param name
     * @param myTerritories
     */
    public Player(String name,ArrayList<Territory> myTerritories){
        this.name = name;
        this.myTerritories = myTerritories;
    }


}
