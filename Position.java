import java.util.LinkedList;

public class Position {
    private int x;
    private int y;
    //private  int numPieces;

    private LinkedList<String> thePeaceBeenThere = new LinkedList<>();

    public void addPeaceBeenThere(ConcretePiece c) {
        String str = "";
        if (c.getOwner().isPlayerOne()) {
            if (c.getName() == 7) {
                str = "K" + c.getName();
            } else {
                str = "D" + c.getName();
            }
        } else {
            str = "A" + c.getName();
        }
        if (!thePeaceBeenThere.contains(str)) {
            thePeaceBeenThere.add(str);
        }

    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        //      numPieces=0;
    }

    public void removeThePeaceBeenThere(ConcretePiece c) {
        String name = "";

        if (c.getOwner().isPlayerOne()) {
            if (c.getName() == 7) {
                name = "K" + c.getName();
            } else {
                name = "D" + c.getName();
            }
        } else {
            name = "A" + c.getName();
        }

        if (this.thePeaceBeenThere.contains(name)) {
            this.thePeaceBeenThere.remove(name);
        }

    }


    public LinkedList<String> getThePeaceBeenThere() {
        return thePeaceBeenThere;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int y) {
        this.y = y;
    }

    ;

    public void setY(int x) {
        this.x = x;
    }

    ;

    public int getY() {
        return this.y;
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}
