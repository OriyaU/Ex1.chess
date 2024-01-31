import java.util.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class GameLogic implements PlayableLogic {

    private ConcretePiece[][] bord;
    private ConcretePlayer atack;
    private ConcretePlayer deff;
    private boolean atackTurn = true;
    private boolean finishGame = false;
    private Stack<UndoM> stack = new Stack<>();
    private ConcretePiece[] allPiecesA = new ConcretePiece[24];
    private ConcretePiece[] allPiecesD = new ConcretePiece[13];
    private LinkedList<ConcretePiece> everyPiece;

    private LinkedList<Pawn> pawns;

    private Position bordOfPosituon[][];


    public GameLogic() {
        this.atack = new ConcretePlayer(true);
        this.deff = new ConcretePlayer(false);
        reset();
    }

    @Override
    public boolean move(Position a, Position b) {

        //the right piece-it her turn
        ConcretePiece aP = (ConcretePiece) getPieceAtPosition(a);

        boolean at = aP.getOwner().isPlayerOne();
        if ((at && atackTurn) || (!at && !atackTurn)) {
            return false;
        }
        ConcretePiece n = (ConcretePiece) getPieceAtPosition(a);
        //just the king can be in the corners
        int bX = b.getX();
        int bY = b.getY();
        int aX = a.getX();
        int aY = a.getY();
        String p = n.getType();
        //corners
        if ((!p.equals("♔")) && ((bX == 0 && bY == 0) || (bX == 10 && bY == 0) || (bX == 0 && bY == 10) || ((bX == 10 && bY == 10)))) {
            return false;
        }

        //אלכסון
        if ((aX != bX) && (aY != bY)) {
            return false;
        }

        // u cant jump over other piece

        if (aY == bY) {
            if (aX < bX) {
                for (int i = aX + 1; i <= bX; i++) {

                    if (this.bord[i][aY] != null) {
                        return false;
                    }

                }
            } else {
                for (int i = aX - 1; i >= bX; i--) {

                    if (this.bord[i][aY] != null) {
                        return false;
                    }

                }
            }
        } else {
            if (aY < bY) {
                for (int i = aY + 1; i <= bY; i++) {
                    if ((this.bord[aX][i] != null)) {
                        return false;
                    }

                }
            } else {
                for (int i = aY - 1; i >= bY; i--) {
                    if (this.bord[aX][i] != null) {
                        return false;

                    }

                }

            }
        }
        //the move is possible

        this.bordOfPosituon[b.getX()][b.getY()].addPeaceBeenThere(aP);
        //saving last move
        stack.push(new UndoM(a, b, aP));

        //change the turns i the game
        this.atackTurn = !this.atackTurn;

        this.bord[aX][aY] = null;
        this.bord[bX][bY] = n;

        if (n.getType().equals("♔")) {
            if ((bX == 0 && bY == 0) || (bX == 10 && bY == 10) || (bX == 0 && bY == 10) || (bX == 10 && bY == 0)) {
                allPiecesD[6].addMove(b);
                this.sort(this.deff);
                this.sortKill(this.deff);
                this.sortSquares(this.deff);
                this.sortPeices();
                this.deff.addWins();
                this.finishGame = true;
            }

        }
        aP.addMove(b);
        if (!this.bord[bX][bY].getType().equals("♔")) {// the killer is not a king
            killPawn(b);
        }
        return true;
    }

    public void killPawn(Position posOfEat) {
        ConcretePiece pawneat = (ConcretePiece) getPieceAtPosition(posOfEat);

        int pX = posOfEat.getX();
        int pY = posOfEat.getY();

        String typeat = pawneat.getType();
        Position forward = new Position(pX, pY - 1);
        Position back = new Position(pX, pY + 1);
        Position right = new Position(pX + 1, pY);
        Position left = new Position(pX - 1, pY);
        int kingDye = 0;

        if (notAwallForPawn(forward)) {
            ConcretePiece pDanger = (ConcretePiece) getPieceAtPosition(forward);
            if (pDanger != null && !pDanger.getType().equals("♔")) {//its not king
                {
                    if (!pDanger.getType().equals(pawneat.getType())) {
                        Position forward2 = new Position(pX, pY - 2);
                        //wall
                        if (!notAwallForPawn(forward2)) {
                            this.bord[pX][pY - 1] = null;
                            ((Pawn) pawneat).eat();
                            stack.peek().setWhoDied(pDanger);
                            stack.peek().setWhereDied(forward);
                        } else {
                            if (getPieceAtPosition(forward2) != null && getPieceAtPosition(forward2).getType().equals(pawneat.getType())) {
                                this.bord[pX][pY - 1] = null;
                                ((Pawn) pawneat).eat();
                                stack.peek().setWhoDied(pDanger);
                                stack.peek().setWhereDied(forward);
                            }

                        }
                    }
                }
            }
            if (pDanger != null && pDanger.getType().equals("♔")) {
                killKing(forward);
            }


        }
        if (notAwallForPawn(back)) {
            ConcretePiece pDanger = (ConcretePiece) getPieceAtPosition(back);
            if (pDanger != null && !pDanger.getType().equals("♔") && !typeat.equals("♔")) {//its not king
                {
                    if (!pDanger.getType().equals(pawneat.getType())) {
                        Position back2 = new Position(pX, pY + 2);
                        //wall
                        if (!notAwallForPawn(back2)) {
                            this.bord[pX][pY + 1] = null;
                            ((Pawn) pawneat).eat();
                            stack.peek().setWhoDied(pDanger);
                            stack.peek().setWhereDied(back);
                        } else {
                            if (getPieceAtPosition(back2) != null && getPieceAtPosition(back2).getType().equals(pawneat.getType())) {
                                this.bord[pX][pY + 1] = null;
                                ((Pawn) pawneat).eat();
                                stack.peek().setWhoDied(pDanger);
                                stack.peek().setWhereDied(back);
                            }

                        }
                    }
                }
            }
            if (pDanger != null && pDanger.getType().equals("♔")) {
                killKing(back);
            }

        }

        if (notAwallForPawn(right)) {
            ConcretePiece pDanger = (ConcretePiece) getPieceAtPosition(right);
            if (pDanger != null && !pDanger.getType().equals("♔") && !typeat.equals("♔")) {//its not king
                {
                    if (!pDanger.getType().equals(pawneat.getType())) {
                        Position right2 = new Position(pX + 2, pY);
                        //wall
                        if (!notAwallForPawn(right2)) {
                            this.bord[pX + 1][pY] = null;
                            ((Pawn) pawneat).eat();
                            stack.peek().setWhoDied(pDanger);
                            stack.peek().setWhereDied(right);
                        } else {
                            if (getPieceAtPosition(right2) != null && getPieceAtPosition(right2).getType().equals(pawneat.getType())) {
                                this.bord[pX + 1][pY] = null;
                                ((Pawn) pawneat).eat();
                                stack.peek().setWhoDied(pDanger);
                                stack.peek().setWhereDied(right);
                            }

                        }
                    }
                }
            }
            if (pDanger != null && pDanger.getType().equals("♔")) {
                killKing(right);
            }
        }

        if (notAwallForPawn(left)) {
            ConcretePiece pDanger = (ConcretePiece) getPieceAtPosition(left);

            if (pDanger != null && !pDanger.getType().equals("♔") && !typeat.equals("♔")) {//its not king
                {
                    if (!pDanger.getType().equals(pawneat.getType())) {

                        Position left2 = new Position(pX - 2, pY);
                        //wall
                        if (!notAwallForPawn(left2)) {
                            this.bord[pX - 1][pY] = null;
                            ((Pawn) pawneat).eat();
                            stack.peek().setWhoDied(pDanger);
                            stack.peek().setWhereDied(left);
                        } else {
                            if (getPieceAtPosition(left2) != null && getPieceAtPosition(left2).getType().equals(pawneat.getType())) {
                                this.bord[pX - 1][pY] = null;
                                ((Pawn) pawneat).eat();
                                stack.peek().setWhoDied(pDanger);
                                stack.peek().setWhereDied(left);
                            }

                        }
                    }
                }
            }
            if (pDanger != null && pDanger.getType().equals("♔")) {
                killKing(left);
            }
        }
        {
        }

    }

    public void killKing(Position pos) {
        int posX = pos.getX();
        int posY = pos.getY();
        int hakafa = 0;
        Position up = new Position(posX, posY - 1);
        if (!notAwallForKing(up))
            hakafa++;
        else {
            if (getPieceAtPosition(up) != null && getPieceAtPosition(up).getType().equals("♟"))
                hakafa++;
        }
        Position down = new Position(posX, posY + 1);
        if (!notAwallForKing(down))
            hakafa++;
        else {
            if (getPieceAtPosition(down) != null && getPieceAtPosition(down).getType().equals("♟"))
                hakafa++;
        }
        Position left = new Position(posX - 1, posY);
        if (!notAwallForKing(left))
            hakafa++;
        else {
            if (getPieceAtPosition(left) != null && getPieceAtPosition(left).getType().equals("♟"))
                hakafa++;
        }
        Position right = new Position(posX + 1, posY);
        if (!notAwallForKing(right))
            hakafa++;
        else {
            if (getPieceAtPosition(right) != null && getPieceAtPosition(right).getType().equals("♟"))
                hakafa++;
        }
        if (hakafa == 4) {
            this.atack.addWins();
            this.finishGame = true;
            this.sort(this.atack);
            this.sortKill(this.atack);
            this.sortSquares(this.atack);
            sortPeices();
        }

    }

    public boolean notAwallForPawn(Position pos) {
        //false== its a wall
        int pX = pos.getX();
        int pY = pos.getY();
        if ((pX == 0 && pY == 0) || (pX == 10 && pY == 0) || (pX == 0 && pY == 10) || ((pX == 10 && pY == 10))) {
            return false;
        }
        if (pX > 10 || pX < 0 || pY < 0 || pY > 10) {
            return false;
        } else {
            return true;
        }
    }

    public boolean notAwallForKing(Position pos) {
        //false== its a wall
        int pX = pos.getX();
        int pY = pos.getY();

        if (pX > 10 || pX < 0 || pY < 0 || pY > 10) {
            return false;
        } else {
            return true;
        }
    }


    @Override
    public Piece getPieceAtPosition(Position position) {

        return this.bord[position.getX()][position.getY()];
    }

    @Override
    public ConcretePlayer getFirstPlayer() {
        return this.deff;
    }

    @Override
    public Player getSecondPlayer() {

        return this.atack;
    }

    @Override
    public boolean isGameFinished() {

        return this.finishGame;
    }

    public void sort(ConcretePlayer winner) {
        Collections.sort(this.everyPiece, new PathComperator(winner));
        for (ConcretePiece cp : this.everyPiece) {
            if (cp.getNumPath() > 1) {
                System.out.println(cp.toString() + cp.getLinkedList());
            }
        }
        System.out.println("***************************************************************************");

    }

    public void sortKill(ConcretePlayer winner) {
        Collections.sort(this.pawns, new killComperator(winner));
        for (Pawn p : this.pawns) {
            if (p.getEat() > 0) {
                System.out.println(p.toString() + p.getEat() + " kills");
            }
        }
        System.out.println("***************************************************************************");
    }

    public void sortSquares(ConcretePlayer winner) {
        Collections.sort(this.everyPiece, new squaresComperator(winner));
        for (ConcretePiece cp : this.everyPiece) {
            if (cp.getSquers() >= 1) {
                System.out.println(cp.toString() + cp.getSquers() + " squares");
            }
        }
        System.out.println("***************************************************************************");
    }

    public void sortPeices() {
        LinkedList<Position> allPsition = new LinkedList<>();
        for (int i = 0; i < 11; i++) {
            for (int b = 0; b < 11; b++) {
                if (this.bordOfPosituon[i][b].getThePeaceBeenThere().size() > 1)
                    allPsition.push(this.bordOfPosituon[i][b]);

            }
        }

        allPsition.sort(new piecesComperator());

        for (Position p : allPsition) {

            if (p.getThePeaceBeenThere().size() > 1) {
                System.out.println(p.toString() + p.getThePeaceBeenThere().size() + " pieces");
            }
        }
        System.out.println("***************************************************************************");


    }

    @Override
    public boolean isSecondPlayerTurn() {

        if (this.atackTurn)
            return true;
        else
            return false;
    }

    @Override

    public void reset() {
        this.bordOfPosituon = new Position[11][11];
        for (int i = 0; i < 11; i++) {
            for (int b = 0; b < 11; b++) {
                this.bordOfPosituon[i][b] = new Position(i, b);
            }
        }
        this.bord = new ConcretePiece[11][11];
        this.atackTurn = true;
        this.finishGame = false;
        LinkedList<ConcretePiece> everyPiece = new LinkedList<>();
        LinkedList<Pawn> pawns = new LinkedList<>();

        for (int i = 0; i < 24; i++) {
            allPiecesA[i] = new Pawn(this.atack, i + 1);
            everyPiece.add(allPiecesA[i]);
            pawns.add((Pawn) allPiecesA[i]);
        }

        for (int i = 0; i < 13; i++) {
            allPiecesD[i] = new Pawn(this.deff, i + 1);
            if (i == 6) {
                allPiecesD[i] = new King(this.deff, i + 1);
                everyPiece.add(allPiecesD[i]);
                continue;
            }
            everyPiece.add(allPiecesD[i]);
            pawns.add((Pawn) allPiecesD[i]);
        }


        this.bord[3][0] = allPiecesA[0];
        allPiecesA[0].addMove(new Position(3, 0));
        this.bordOfPosituon[3][0].addPeaceBeenThere(allPiecesA[0]);
        this.bord[4][0] = allPiecesA[1];
        allPiecesA[1].addMove(new Position(4, 0));
        this.bordOfPosituon[4][0].addPeaceBeenThere(allPiecesA[1]);
        this.bord[5][0] = allPiecesA[2];
        allPiecesA[2].addMove(new Position(5, 0));
        this.bordOfPosituon[5][0].addPeaceBeenThere(allPiecesA[2]);
        this.bord[6][0] = allPiecesA[3];
        allPiecesA[3].addMove(new Position(6, 0));
        this.bordOfPosituon[6][0].addPeaceBeenThere(allPiecesA[3]);
        this.bord[7][0] = allPiecesA[4];
        allPiecesA[4].addMove(new Position(7, 0));
        this.bordOfPosituon[7][0].addPeaceBeenThere(allPiecesA[4]);
        this.bord[5][1] = allPiecesA[5];
        allPiecesA[5].addMove(new Position(5, 1));
        this.bordOfPosituon[5][1].addPeaceBeenThere(allPiecesA[5]);
        this.bord[0][3] = allPiecesA[6];
        allPiecesA[6].addMove(new Position(0, 3));
        this.bordOfPosituon[0][3].addPeaceBeenThere(allPiecesA[6]);
        this.bord[10][3] = allPiecesA[7];
        allPiecesA[7].addMove(new Position(10, 3));
        this.bordOfPosituon[10][3].addPeaceBeenThere(allPiecesA[7]);
        this.bord[0][4] = allPiecesA[8];
        allPiecesA[8].addMove(new Position(0, 4));
        this.bordOfPosituon[0][4].addPeaceBeenThere(allPiecesA[8]);
        this.bord[10][4] = allPiecesA[9];
        allPiecesA[9].addMove(new Position(10, 4));
        this.bordOfPosituon[10][4].addPeaceBeenThere(allPiecesA[9]);
        this.bord[0][5] = allPiecesA[10];
        allPiecesA[10].addMove(new Position(0, 5));
        this.bordOfPosituon[0][5].addPeaceBeenThere(allPiecesA[10]);
        this.bord[1][5] = allPiecesA[11];
        allPiecesA[11].addMove(new Position(1, 5));
        this.bordOfPosituon[1][5].addPeaceBeenThere(allPiecesA[11]);
        this.bord[9][5] = allPiecesA[12];
        allPiecesA[12].addMove(new Position(9, 5));
        this.bordOfPosituon[9][5].addPeaceBeenThere(allPiecesA[12]);
        this.bord[10][5] = allPiecesA[13];
        allPiecesA[13].addMove(new Position(10, 5));
        this.bordOfPosituon[10][5].addPeaceBeenThere(allPiecesA[13]);
        this.bord[0][6] = allPiecesA[14];
        allPiecesA[14].addMove(new Position(0, 6));
        this.bordOfPosituon[0][6].addPeaceBeenThere(allPiecesA[14]);
        this.bord[10][6] = allPiecesA[15];
        allPiecesA[15].addMove(new Position(10, 6));
        this.bordOfPosituon[10][6].addPeaceBeenThere(allPiecesA[15]);
        this.bord[0][7] = allPiecesA[16];
        allPiecesA[16].addMove(new Position(0, 7));
        this.bordOfPosituon[0][7].addPeaceBeenThere(allPiecesA[16]);
        this.bord[10][7] = allPiecesA[17];
        allPiecesA[17].addMove(new Position(10, 7));
        this.bordOfPosituon[10][7].addPeaceBeenThere(allPiecesA[17]);
        this.bord[5][9] = allPiecesA[18];
        allPiecesA[18].addMove(new Position(5, 9));
        this.bordOfPosituon[5][9].addPeaceBeenThere(allPiecesA[18]);
        this.bord[3][10] = allPiecesA[19];
        allPiecesA[19].addMove(new Position(3, 10));
        this.bordOfPosituon[3][10].addPeaceBeenThere(allPiecesA[19]);
        this.bord[4][10] = allPiecesA[20];
        allPiecesA[20].addMove(new Position(4, 10));
        this.bordOfPosituon[4][10].addPeaceBeenThere(allPiecesA[20]);
        this.bord[5][10] = allPiecesA[21];
        allPiecesA[21].addMove(new Position(5, 10));
        this.bordOfPosituon[5][10].addPeaceBeenThere(allPiecesA[21]);
        this.bord[6][10] = allPiecesA[22];
        allPiecesA[22].addMove(new Position(6, 10));
        this.bordOfPosituon[6][10].addPeaceBeenThere(allPiecesA[22]);
        this.bord[7][10] = allPiecesA[23];
        allPiecesA[23].addMove(new Position(7, 10));
        this.bordOfPosituon[7][10].addPeaceBeenThere(allPiecesA[23]);

        this.bord[5][3] = allPiecesD[0];
        allPiecesD[0].addMove(new Position(5, 3));
        this.bordOfPosituon[5][3].addPeaceBeenThere(allPiecesD[0]);
        this.bord[4][4] = allPiecesD[1];
        allPiecesD[1].addMove(new Position(4, 4));
        this.bordOfPosituon[4][4].addPeaceBeenThere(allPiecesD[1]);
        this.bord[5][4] = allPiecesD[2];
        allPiecesD[2].addMove(new Position(5, 4));
        this.bordOfPosituon[5][4].addPeaceBeenThere(allPiecesD[2]);
        this.bord[6][4] = allPiecesD[3];
        allPiecesD[3].addMove(new Position(6, 4));
        this.bordOfPosituon[6][4].addPeaceBeenThere(allPiecesD[3]);
        this.bord[3][5] = allPiecesD[4];
        allPiecesD[4].addMove(new Position(3, 5));
        this.bordOfPosituon[3][5].addPeaceBeenThere(allPiecesD[4]);
        this.bord[4][5] = allPiecesD[5];
        allPiecesD[5].addMove(new Position(4, 5));
        this.bordOfPosituon[4][5].addPeaceBeenThere(allPiecesD[5]);
        this.bord[5][5] = allPiecesD[6];
        allPiecesD[6].addMove(new Position(5, 5));
        this.bordOfPosituon[5][5].addPeaceBeenThere(allPiecesD[6]);
        this.bord[6][5] = allPiecesD[7];
        allPiecesD[7].addMove(new Position(6, 5));
        this.bordOfPosituon[6][5].addPeaceBeenThere(allPiecesD[7]);
        this.bord[7][5] = allPiecesD[8];
        allPiecesD[8].addMove(new Position(7, 5));
        this.bordOfPosituon[7][5].addPeaceBeenThere(allPiecesD[8]);
        this.bord[4][6] = allPiecesD[9];
        allPiecesD[9].addMove(new Position(4, 6));
        this.bordOfPosituon[4][6].addPeaceBeenThere(allPiecesD[9]);
        this.bord[5][6] = allPiecesD[10];
        allPiecesD[10].addMove(new Position(5, 6));
        this.bordOfPosituon[5][6].addPeaceBeenThere(allPiecesD[10]);
        this.bord[6][6] = allPiecesD[11];
        allPiecesD[11].addMove(new Position(6, 6));
        this.bordOfPosituon[6][6].addPeaceBeenThere(allPiecesD[11]);
        this.bord[5][7] = allPiecesD[12];
        allPiecesD[12].addMove(new Position(5, 7));
        this.bordOfPosituon[5][7].addPeaceBeenThere(allPiecesD[12]);

        this.everyPiece = everyPiece;
        this.pawns = pawns;


    }

    @Override
    public void undoLastMove() {
        try {
            UndoM lastM = stack.pop();
            Position fromKiler = lastM.getFromWhere();
            Position toKiler = lastM.getToWhere();
            ConcretePiece p = lastM.getWhoMove();
            LinkedList<ConcretePiece> whoDied = lastM.getWhoDied();
            LinkedList<Position> whereDied = lastM.getWhereDied();
            int size = whoDied.size();
            while (whoDied.size() > 0) {
                ConcretePiece co = whoDied.removeLast();
                Position po = whereDied.removeLast();
                this.bord[po.getX()][po.getY()] = co;

            }
            int lessSqwers = lastM.getUndoSquers();
            p.setSquers(p.getSquers() - lessSqwers);
            p.getLinkedList().removeLast();
            this.bordOfPosituon[toKiler.getX()][toKiler.getY()].removeThePeaceBeenThere(p);
            this.bord[fromKiler.getX()][fromKiler.getY()] = p;
            this.bord[toKiler.getX()][toKiler.getY()] = null;
            for (int i = 0; i < size; i++) {
                ((Pawn) p).undoEat();
            }
            this.atackTurn = !this.atackTurn;
        } catch (EmptyStackException e) {
            reset();
        }
    }

    @Override
    public int getBoardSize() {
        return 11;
    }


}
