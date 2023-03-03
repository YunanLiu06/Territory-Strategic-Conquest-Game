package edu.duke.ece651.teamX.server;

import java.util.ArrayList;

public class Territory {
    private String name;
    private ArrayList<Unit> unitList;
    private Player owner;
    // public ArrayList<Tuple<Player,Unit>> whoAttactsMe;
    
    /**
     * constructor only name
     * @param name
     */
    public Territory(String name) {
        this.name = name;
    }

    /**
     * constructor only name and player
     * @param name
     * @param owner
     */
    public Territory(String name, Player owner) {
        this.name = name;
        this.owner = owner;
    }


    /*
     * return the name of the territory
     */
    public String getName() {
        return name;
    }
}
