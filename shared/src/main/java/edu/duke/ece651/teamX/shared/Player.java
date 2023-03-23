package edu.duke.ece651.teamX.shared;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class Player {
    protected String name;
    protected int numOfTerritory;
    protected ArrayList<Territory> myTerritories = new ArrayList<Territory> ();
    private int numOfUnitAlreadyPlaced = 0;
    private ArrayList<TraceCommand> log = new ArrayList<TraceCommand> ();



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

    /*
     * return the iterator of the territory list
     */
    public Iterator<Territory> getTerritories() {
        return myTerritories.iterator();
    }


    public String getName(){
        return this.name;
    }

    /**
     * add territory to the player
     * @param newTerritory
     */
    public void addTerritory(Territory newTerritory){
        if(this.myTerritories.contains(newTerritory)){
            throw new IllegalArgumentException("Territory already occupied by player");
        }else{
            myTerritories.add(newTerritory);
            newTerritory.changeOwner(this);
        }
    }


    /**
     * lose territory to the player
     * @param newTerritory
     */
    public void loseTerritory(Territory newTerritory){
        if(!this.myTerritories.contains(newTerritory)){
            throw new IllegalArgumentException("Territory is not occupied by this player");
        }else{
            myTerritories.remove(myTerritories.indexOf(newTerritory));
        }
    }

    /**
     * get how many units has placed
     * ATTENTION: this method is only for placement check in placement stage, it should not be used any other palces.
     * @return
     */
    public int getNumOfAlreadyPlaced(){
        return numOfUnitAlreadyPlaced;
    }

    /**
     * inital placement of units
     * 
     * @param t territory to place units
     * @param u units to place
     */
    public void place(Territory t,Unit u){
        if (myTerritories.contains(t)){
            t.addUnit(u);
            numOfUnitAlreadyPlaced+=u.getAmount();
        }else{
            throw new IllegalArgumentException("this Territory is not owned by this player");
        }
        
    }

    /**
     * try to move unit form one place to another
     * @param from
     * @param to
     * @param unit
     */
    public void tryMove(Territory from, Territory to, Unit unit){
        log.add(new TraceCommand("Move", from, to, unit,this));
        if(!from.getOwner().equals(to.getOwner())){
            throw new IllegalArgumentException("two territory must be owned by the same player");
        }
        from.substractUnit(unit);
        to.addUnit(unit);
    }
    /**
     * determine if the plaer  loses
     * @return
     */
    public boolean isLose(){
        return myTerritories.isEmpty();
    }
    /**
     * add fire information to territory
     * @param from
     * @param to
     * @param unit
     */
    public void fire(Territory from, Territory to, Unit unit){
        log.add(new TraceCommand("Fire", from, to, unit, this));
        from.substractUnit(unit);
        ArrayList<Unit> unitList = new ArrayList<Unit>();
        unitList.add(unit);
        to.addFireSource(this, unitList);
    }

    /**
     * clear log
     */
    public void clearLog(){
        this.log.clear();
    }


    /**
     * retrace base on log
     */
    public void retrace(){
        for(TraceCommand t:this.log){
            t.undo();
        }

        this.log.clear();
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
