package edem.util.hexes;

public class AbstractHex implements Hex {
    
    private Index index;

    @Override
    public void setIndex(Index index) {
        this.index = index;
    }

    @Override
    public Index getIndex() {
        return index;
    }

}
