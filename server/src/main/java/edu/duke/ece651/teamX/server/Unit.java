package edu.duke.ece651.teamX.server;

public abstract class Unit {
    private String name;
    private int amount;
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
