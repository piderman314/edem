package edem.util.hexes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Index {

    private static final Map<Integer, List<Index>> AREA_DELTAS = new HashMap<>();
    private static final Map<Integer, List<Index>> RING_DELTAS = new HashMap<>();
    
    private final int x;
    private final int y;
    private final int z;

    public Index add(Index other) {
        return new Index(x + other.getX(), y + other.getY(), z + other.getZ());
    }
    
    public int distanceTo(Index other) {
        return (Math.abs(x - other.getX()) + Math.abs(y - other.getY()) + Math.abs(z - other.getZ())) / 2;
    }
    
    public static List<Index> areaDeltas(int radius) {
        List<Index> areaDeltas = AREA_DELTAS.get(radius);
        
        if (areaDeltas == null) {
            areaDeltas = new ArrayList<>();
            
            for (int i = -radius; i <= radius; i++) {
                for (int j = -radius; j <= radius; j++) {
                    for (int k = -radius; k <= radius; k++) {
                        if (i + j + k == 0) {
                            areaDeltas.add(new Index(i, j, k));
                        }
                    }
                }
            }
            
            AREA_DELTAS.put(radius, areaDeltas);
        }
        
        return areaDeltas;
    }
    
    public static List<Index> ringDeltas(int radius) {
        List<Index> ringDeltas = RING_DELTAS.get(radius);
        
        if (ringDeltas == null) {
            ringDeltas = new ArrayList<>(areaDeltas(radius));
            
            if (radius != 0) {
                ringDeltas.removeAll(areaDeltas(radius - 1));
            }
            
            RING_DELTAS.put(radius, ringDeltas);
        }
        
        return ringDeltas;
    }
    
    public static Index of(int x, int y, int z) throws HexException {
        if (x + y + z != 0) {
            throw new HexException(x + ", " + y + " and " + z + " do not add up to 0.");
        }
        
        return new Index(x, y, z);
    }
    
}
