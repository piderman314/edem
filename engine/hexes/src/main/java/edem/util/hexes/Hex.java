package edem.util.hexes;

public interface Hex {

    default boolean neighbours(Hex other) {
        if (getIndex().equals(other.getIndex())) {
            return false;
        }
        
        int diffX = Math.abs(getIndex().getX() - other.getIndex().getX());
        int diffY = Math.abs(getIndex().getY() - other.getIndex().getY());
        int diffZ = Math.abs(getIndex().getZ() - other.getIndex().getZ());
        
        return  diffX <= 1 
                && diffY <= 1 
                && diffZ <= 1;
    }
    
    default boolean neighboursDiagonally(Hex other) {
        if (getIndex().equals(other.getIndex())) {
            return false;
        }
        
        int diffX = Math.abs(getIndex().getX() - other.getIndex().getX());
        int diffY = Math.abs(getIndex().getY() - other.getIndex().getY());
        int diffZ = Math.abs(getIndex().getZ() - other.getIndex().getZ());
        
        boolean diffXInRange = diffX <= 2 && diffX >= 1;
        boolean diffYInRange = diffY <= 2 && diffY >= 1;
        boolean diffZInRange = diffZ <= 2 && diffZ >= 1;
        
        return diffXInRange && diffYInRange && diffZInRange;
    }
    
    void setIndex(Index index);
    Index getIndex();
    
}
