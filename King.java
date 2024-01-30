public class King extends ConcretePiece {
    private String type;

    public King(ConcretePlayer owner, int name) {
        this.owner = owner;
        this.name = name;
        this.type = "♔";
    }

    @Override
    public String getType() {
        return this.type;
    }

}
