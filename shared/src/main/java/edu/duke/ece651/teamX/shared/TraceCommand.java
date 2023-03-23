package edu.duke.ece651.teamX.shared;

public class TraceCommand {
    private String command;
    private Territory from;
    private Territory to;
    private Unit unit;
    private Player player;
    
    public TraceCommand(String command, Territory from, Territory to, Unit u, Player p){
        this.command = command;
        this.from = from;
        this.to = to;
        this.unit = u;
        this.player = p;
    }

    public void undo(){
        if(this.command.equals("Move")){
            from.addUnit(unit);
            to.substractUnit(unit);
        }else{
            from.addUnit(unit);
            to.deleteFireSource(player);
        }
    }

    
}
