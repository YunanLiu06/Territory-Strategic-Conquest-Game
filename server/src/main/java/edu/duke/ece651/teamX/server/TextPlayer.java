package edu.duke.ece651.teamX.server;

import java.util.ArrayList;

public class TextPlayer extends Player {

    /**
     * constructor only name
     * @param name
     */
    public TextPlayer(String name){
        super(name);
    }

    /**
     * constructor name and Territories
     * @param name
     * @param myTerritories
     */
    public TextPlayer(String name,ArrayList<Territory> myTerritories){
        super(name,myTerritories);
    }






    
}
