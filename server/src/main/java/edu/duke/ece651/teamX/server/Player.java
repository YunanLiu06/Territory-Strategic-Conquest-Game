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
        for(Territory t:myTerritories){
            t.initiateOnwer(this);
        }
    }


    public String getName(){
        return this.name;
    }

    public void addTerritory(Territory newTerritory){
        if(this.myTerritories.contains(newTerritory)){
            throw new IllegalArgumentException("Territory already occupied by player");
        }else{
            myTerritories.add(newTerritory);
        }
    }

    public void tryMove(Territory from, Territory to, Unit unit){
        if(!from.getOwner().equals(to.getOwner())){
            throw new IllegalArgumentException("two territory must be owned by the same player");
        }
        from.substractUnit(unit);
        to.addUnit(unit);
    }

    @Override
    public boolean equals(Object o) {
        if(o.getClass() == this.getClass()){
            Player p = (Player) o;
            return p.getName().equals(this.getName());
        }
        return false;
    }


}
