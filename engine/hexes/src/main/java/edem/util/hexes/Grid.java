package edem.util.hexes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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
    
    public Collection<HEX> ringAround(@Nonnull final HEX hex, int radius) {
        return around(hex, radius, Index::ringDeltas);
    }
    
    public Collection<HEX> areaAround(@Nonnull final HEX hex, int radius) {
        return around(hex, radius, Index::areaDeltas);
    }
    
    private Collection<HEX> around(@Nonnull final HEX hex, int radius, Function<Integer, List<Index>> deltaSupplier) {
        List<HEX> neighbours = new ArrayList<>();
        
        for (Index neighbourIndexDelta : deltaSupplier.apply(radius)) {
            
            HEX neighbour = get(hex.getIndex().add(neighbourIndexDelta));
            if (neighbour != null) {
                neighbours.add(neighbour);
            }
        }
        
        return neighbours;
    }
    
}
