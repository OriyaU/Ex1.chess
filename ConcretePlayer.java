public class ConcretePlayer implements Player {

    private boolean atack;
    private int wins;

    public ConcretePlayer(boolean atack) {

        this.atack = atack;
        this.wins = 0;
    }

    @Override
    public boolean isPlayerOne() {

        if (this.atack) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public int getWins() {
        return this.wins;
    }

    public void addWins() {
        this.wins++;
    }
}
