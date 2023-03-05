package edu.duke.ece651.teamX.shared;

public abstract class Unit {
    protected String name;
    protected int amount;
    /**
     * constructor name and amount
     * @param name
     * @param amount
     */
    public Unit(String name, int amount){

        this.name = name;
        this.amount = amount;
    }

    public void addAmount(int amount){
        this.amount+=amount;
    }

    public void substractAmount(int amount){
        this.amount-=amount;
        if(this.amount<0){
            this.amount=0;
        }
    }

    public int getAmount(){
        return amount;
    }
}
