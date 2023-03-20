package edu.duke.ece651.teamX.shared;

import java.util.ArrayList;
import java.util.Iterator;


public class Territory {
    int ARRAY_SIZE_IS_ZERO = -1;
    int DO_AGAIN = -2;

    private String name;
    private ArrayList<Unit> unitList = new ArrayList<Unit>();
    private Player owner;
    private ArrayList<Territory> adjacentTerritoy = new ArrayList<Territory>();
    public ArrayList<Player> whoAttactsMe = new ArrayList<Player>();
    public ArrayList<ArrayList<Unit>> whatAttactsMe = new ArrayList<ArrayList<Unit>>();
    public Determinant determinant = new DiceGame();

    
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
                if (e.getAmount()==0){
                    unitList.remove(e);
                }
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



    @Override
    public boolean equals(Object o) {
        if(o.getClass() == this.getClass()){
            Territory p = (Territory) o;
            return p.getName().equals(this.getName());
        }
        return false;
    }

    public void addFireSource(Player p, ArrayList<Unit> u){
        if(whoAttactsMe.contains(p)){
            int place = whoAttactsMe.indexOf(p);
            for (Unit un:u){
                whatAttactsMe.get(place).add(un);
            }
        }else{
            whoAttactsMe.add(p);
            whatAttactsMe.add(u);
        }
    }

    private int determineWhoWin(Unit a, Unit b){
        boolean res = determinant.determine(a, b, 20, 20);
        if (res){
            return 1;
        }else{
            return 0;
        }
    }


    private boolean deleteDeadUnits(int i,Unit a,ArrayList<Player> whoAttactsMe, ArrayList<ArrayList<Unit>> whatAttactsMe){
        if(a.getAmount()==0){
            whatAttactsMe.get(i).remove(0);
            if(whatAttactsMe.get(i).size()==0){
                whatAttactsMe.remove(i);
                whoAttactsMe.remove(i);
                return true;
            }
        }
        return false;
    }

    private int fight(int i,ArrayList<Player> whoAttactsMe, ArrayList<ArrayList<Unit>> whatAttactsMe){
        int size = whoAttactsMe.size();
        int next = 0;
        if (i == size-1 ){
            next = 0;
        }else{
            next = i+1;
        }
        Unit a = whatAttactsMe.get(i).get(0);
        Unit b = whatAttactsMe.get(next).get(0);
        int result = determineWhoWin(a,b);
        if(result == 0){
            b.substractAmount(1);
        }else{
            a.substractAmount(1);
        }
        boolean modifyIntex =  deleteDeadUnits(i, a, whoAttactsMe, whatAttactsMe);
        deleteDeadUnits(next, b, whoAttactsMe, whatAttactsMe);
        if (modifyIntex){
            i-=1;
        }

        return i;
    }

    public void handleFire(){
        if(unitList.size()>0){
            whoAttactsMe.add(owner);
            whatAttactsMe.add(unitList);
        }
        int i=0;
        while(whoAttactsMe.size()>1){
            i = fight(i,whoAttactsMe,whatAttactsMe);
            i+=1;
            if(i >= whoAttactsMe.size()){
                i=0;
            }
        }
        this.owner.loseTerritory(this);
        this.owner = whoAttactsMe.get(0);
        whoAttactsMe.get(0).addTerritory(this);

        whoAttactsMe.clear();
        whatAttactsMe.clear();
    }

    public void changeOwner(Player p){
        this.owner = p;
    }

}
