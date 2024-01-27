import java.util.Comparator;

public class squaresComperator implements Comparator<ConcretePiece> {

    private ConcretePlayer player;
    public squaresComperator(ConcretePlayer player) {
        this.player = player;
    }
    public int compare(ConcretePiece c1, ConcretePiece c2){
        if(c1.getSquers()==c2.getSquers()) {
            if (c1.getName() == c2.getName()) {
                if (c1.getOwner() == this.player) {
                    return -1;
                }
                return 1;
            }
            return c1.getName() - c2.getName();
            }
        return c2.getSquers()- c1.getSquers();
    }

}
