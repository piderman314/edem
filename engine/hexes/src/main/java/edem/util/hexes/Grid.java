package edem.util.hexes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

public class Grid <HEX extends Hex> {

    private final Map<Index, HEX> grid = new HashMap<>();
    
    public HEX get(Index index) {
        return grid.get(index);
    }
    
    public HEX put(HEX hex) {
        return put(hex.getIndex(), hex);
    }
    
    public HEX put(Index index, HEX hex) {
        hex.setIndex(index);
        return grid.put(index, hex);
    }
    
    @Nonnull
    public Collection<HEX> allNeighbours(@Nonnull final HEX hex) {
        return allNeighbours(hex, false);
    }
    
    @Nonnull
    public Collection<HEX> allNeighbours(@Nonnull final HEX hex, boolean includeThisHex) {
        List<HEX> neighbours = new ArrayList<>();
        
        if (includeThisHex) {
            neighbours.add(hex);
        }
        
        for (Index neighbourIndexDelta : Index.NEIGHBOUR_INDEX_DELTAS) {
            HEX neighbour = get(hex.getIndex().add(neighbourIndexDelta));
            if (neighbour != null) {
                neighbours.add(neighbour);
            }
        }
        
        return neighbours;
    }
    
}
