public class Pawn extends ConcretePiece {
private int eat;
private String type;

    public Pawn(ConcretePlayer owner, int name) {
        this.owner=owner;
        this.name=name;
        this.eat=0;
        boolean b=this.owner.isPlayerOne();
        if(b==true)
        {
            this.type="♙";
        }
        else {
            this.type="♟";
        }
    }
    public void undoEat(){
        if(this.eat!=0){
            this.eat=this.eat-1;
        }
    }

    public int getEat(){
        return this.eat;
    }
    public void eat(){
        this.eat=this.eat+1;
    }


    @Override
    public String getType() {
        return this.type;
    }





}
