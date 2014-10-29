package edem.util.hexes;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Index {

    static final Index[] NEIGHBOUR_INDEX_DELTAS = new Index[] {
        new Index(+1, -1,  0),
        new Index(-1, +1,  0),
        new Index(+1,  0, -1),
        new Index(-1,  0, +1),
        new Index( 0, +1, -1),
        new Index( 0, -1, +1)
    };
    
    private final int x;
    private final int y;
    private final int z;

    public Index add(Index other) {
        return new Index(x + other.getX(), y + other.getY(), z + other.getZ());
    }
    
    public int distanceTo(Index other) {
        return (Math.abs(x - other.getX()) + Math.abs(y - other.getY()) + Math.abs(z - other.getZ())) / 2;
    }
    
    public static Index of(int x, int y, int z) throws HexException {
        if (x + y + z != 0) {
            throw new HexException(x + ", " + y + " and " + z + " do not add up to 0.");
        }
        
        return new Index(x, y, z);
    }
    
}
