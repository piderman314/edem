package edem.util.hexes;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Index {

    private final int x;
    private final int y;
    private final int z;

    public Index add(Index other) {
        return new Index(x + other.getX(), y + other.getY(), z + other.getZ());
    }
    
    public static Index of(int x, int y, int z) throws HexException {
        if (x + y + z != 0) {
            throw new HexException(x + ", " + y + " and " + z + " do not add up to 0.");
        }
        
        return new Index(x, y, z);
    }
    
}
