import java.util.Comparator;

public class killComperator implements Comparator<Pawn> {
    private ConcretePlayer player;

    public killComperator(ConcretePlayer p) {
        this.player = p;
    }

    //I know that i coud have insted of Concrate peace, just use Pawn.how ever, I know that the king never et
    public int compare(Pawn p1, Pawn p2) {
        if (p1.getEat() == p2.getEat()) {
            if (p1.getName() == p2.getName()) {
                if (p1.getOwner() == this.player)
                    return -1;
                return 1;
            }
            return p1.getName() - p2.getName();
        }
        return p2.getEat() - p1.getEat();

    }


}
