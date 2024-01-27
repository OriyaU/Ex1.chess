public class Position {
    private int x;
    private int y;
    private  int numPieces;
    public Position(int x, int y)
    {
        this.x=x;
        this.y=y;
        numPieces=0;
    }
    public void addPieces(){
        this.numPieces++;
    }
    public int getNumPieces(){
        return this.numPieces;
    }
    public void removePieces(){
        if(this.numPieces>0){
            this.numPieces--;
        }
    }

    public int getX(){
        return this.x;
    }
    public void setX(int y){
        this.y=y;
    };
    public void setY(int x){
        this.x=x;
    };

    public int getY(){
        return this.y;
    }
    public String toString(){
        return "("+ this.x+", "+this.y+")";
    }
}
