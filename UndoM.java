public class UndoM {
    private Position fromWhere;
    private Position toWhere;
    private ConcretePiece whoMove;
    private ConcretePiece whoDied;

    private Position whereDied;
    private int undoSquers;

    public UndoM(Position fromWhere,Position toWhere, ConcretePiece whoMove){
        this.toWhere=toWhere;
        this.fromWhere=fromWhere;
        this.whoMove=whoMove;
        this.whoDied=null;
        this.whereDied=null;
        if (fromWhere.getX()==toWhere.getX()) {
            this.undoSquers = Math.abs(fromWhere.getY()-toWhere.getY());
        } else {
            this.undoSquers= Math.abs((fromWhere.getX()-toWhere.getX()));
        }
    }
    public int getUndoSquers(){
        return this.undoSquers;
    }

    public Position getFromWhere() {
        return fromWhere;
    }

    public void setFromWhere(Position fromWhere) {
        this.fromWhere = fromWhere;
    }

    public Position getToWhere() {
        return toWhere;
    }

    public void setToWhere(Position toWhere) {
        this.toWhere = toWhere;
    }

    public ConcretePiece getWhoMove() {
        return whoMove;
    }

    public void setWhoMove(ConcretePiece whoMove) {
        this.whoMove = whoMove;
    }

    public ConcretePiece getWhoDied() {
        return whoDied;
    }

    public void setWhoDied(ConcretePiece whoDied) {
        this.whoDied = whoDied;
    }

    public Position getWhereDied() {
        return whereDied;
    }

    public void setWhereDied(Position whereDied) {
        this.whereDied = whereDied;
    }
}
