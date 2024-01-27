import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

abstract  class ConcretePiece implements Piece{
    protected ConcretePlayer owner;
    protected int name;

    protected LinkedList<Position> allPieces=new LinkedList<>();

    protected int numPath=0;
    protected int squers=0;

    public void addMove(Position p)
    {
        if(!this.allPieces.isEmpty()) {
            if (p.getY() == this.allPieces.getLast().getY()) {
                this.squers = Math.abs(p.getX() - this.allPieces.getLast().getX()) + this.squers;
            } else {
                this.squers = this.squers + Math.abs(p.getY() - this.allPieces.getLast().getY());
            }
        }
        this.allPieces.add(p);
        this.numPath=1+this.numPath;
    }

    public int getSquers() {
        return squers;
    }
    public void setSquers(int num){
        this.squers=num;
    }

     public   LinkedList<Position> getLinkedList (){
        return this.allPieces;
    }

    public  int getNumPath(){
        return numPath;
    }

    public int getName(){
        return name;
    }

    @Override
     public ConcretePlayer getOwner(){
        return this.owner;
    }

    public String toString(){
        if(this.owner.isPlayerOne()) {
            if(this.name==7)
            {
                return "K" + this.name + ": ";
            }
            else{
            return "D" + this.name + ": ";
            }
        }
        return "A"+this.name+": ";
    }



}
