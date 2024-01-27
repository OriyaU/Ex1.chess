import java.util.Comparator;

public class piecesComperator implements Comparator<Position> {


    public int compare(Position p1, Position p2){
        if(p1.getNumPieces()==p2.getNumPieces()){

            if(p1.getX()==p2.getX()){
                return p1.getY()-p2.getY();
            }
            return p1.getX()-p2.getX();
        }
        return p2.getNumPieces()- p1.getNumPieces();


    }
}
