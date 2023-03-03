package edu.duke.ece651.teamX.server;

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
}
