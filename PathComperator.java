import java.util.Comparator;

public class PathComperator implements Comparator<ConcretePiece> {

        private ConcretePlayer player;
    public PathComperator(ConcretePlayer player){
            this.player=player;
    }

    public int compare(ConcretePiece p1, ConcretePiece p2) {
        if(p1.getOwner()==p2.getOwner())
        {
            if(p1.getNumPath()==p2.getNumPath())
                return p1.getName()-p2.getName();
            return p1.getNumPath()-p2.getNumPath();
        }

        if (p1.getOwner()==this.player){
            return -1;
        }
        return 1;
    }


}
