package edu.duke.ece651.teamX.shared;

import java.util.ArrayList;
import java.util.Iterator;

public class Territory {
    private String name;
    private ArrayList<Unit> unitList = new ArrayList<Unit>();
    private Player owner;
    private ArrayList<Territory> adjacentTerritoy = new ArrayList<Territory>();
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

      /**
     * add territory to adjacent territories
     * 
     * NOTE: Terri t will not add this to t's adjacent territories
     * @param t
     */
    public void addAdjacentTerritory(Territory t) {
        if(!this.hasAdjacentTerritory(t)){
            adjacentTerritoy.add(t);
        }
    }

      /**
     * add territory to adjacent territories
     * @param t
     */
    public boolean hasAdjacentTerritory(Territory t) {
        return adjacentTerritoy.contains(t);
    }

    /**
     * return the iterator of adjacent territory list
     * @return
     */
    public Iterator<Territory>getAdjacentTerritories() {
        return adjacentTerritoy.iterator();
    }


    /*
     * return the name of the territory
     */
    public String getName() {
        return name;
    }

        /*
     * return the name of the territory
     */
    public Player getOwner() {
        return owner;
    }

    /*
     * return the iterator of the unit list
     */
    public Iterator<Unit> getUnits() {
        return unitList.iterator();
    }

    /*
     * add the territory to one player at the beginning of the game
     */
    public void initiateOnwer(Player player) {
        if(this.owner == null){
            player.addTerritory(this);
            this.owner=player;
        }else{
            throw new IllegalArgumentException("already initiate owner");
        }
        
    }

    /**
     * add unit to this territory
     * @param unit
     */
    public void addUnit(Unit unit){
        int flag = 0;
        for(Unit e:unitList){
            if(e.getClass().equals(unit.getClass())){
                e.addAmount(unit.getAmount());
                flag = 1;
                break;
            }
        }
        if(flag == 1){
            return;
        }else{
            unitList.add(unit);
        }
    }

    /**
     * substract unit to this territory
     * @param unit
     */
    public void substractUnit(Unit unit){
        int flag = 0;
        for(Unit e:unitList){
            if(e.getClass().equals(unit.getClass())){
                if(e.getAmount()<unit.getAmount()){
                    throw new IllegalArgumentException("cannot substract, not enough units");
                }
                e.substractAmount(unit.getAmount());
                flag = 1;
                break;
            }
        }
        if(flag == 1){
            return;
        }else{
            throw new IllegalArgumentException("cannot substract, not enough units");
        }
    }

    /*
     * change the owner of this territory
     */
    private void changeOwner(Player player) {
        this.owner=player;
    }


    @Override
    public boolean equals(Object o) {
        if(o.getClass() == this.getClass()){
            Territory p = (Territory) o;
            return p.getName().equals(this.getName());
        }
        return false;
    }
}
