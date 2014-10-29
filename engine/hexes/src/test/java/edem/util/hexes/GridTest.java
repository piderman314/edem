package edem.util.hexes;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;

import java.util.Collection;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class GridTest {

    private TestHex center;
    private TestHex hex1;
    private TestHex hex2;
    private TestHex hex3;
    private TestHex hex4;
    private TestHex hex5;
    private TestHex hex6;
    private Grid<TestHex> grid;
            
    @BeforeMethod
    public void init() throws HexException {
        center = new TestHex();
        center.setIndex(Index.of(1, 1, -2));
        
        hex1 = new TestHex();
        hex1.setIndex(Index.of(2, 0, -2));
        
        hex2 = new TestHex();
        hex2.setIndex(Index.of(0, 2, -2));
        
        hex3 = new TestHex();
        hex3.setIndex(Index.of(1, 2, -3));
        
        hex4 = new TestHex();
        hex4.setIndex(Index.of(1, 0, -1));
        
        hex5 = new TestHex();
        hex5.setIndex(Index.of(2, 1, -3));
        
        hex6 = new TestHex();
        hex6.setIndex(Index.of(0, 1, -1));
        
        grid = new Grid<>();
        grid.put(center);
    }
    
    public void shouldReturnPartialAreaList() {
        // given
        grid.put(hex1);
        grid.put(hex4);
        
        // when
        Collection<TestHex> hexes = grid.areaAround(center, 2);
        
        // then
        assertThat(hexes, containsInAnyOrder(center, hex1, hex4));
    }
    
        public void shouldReturnFullAreaList() {
        // given
        grid.put(hex1);
        grid.put(hex5);
        grid.put(hex3);
        grid.put(hex4);
        grid.put(hex2);
        grid.put(hex6);
        
        // when
        Collection<TestHex> hexes = grid.areaAround(center, 1);
        
        // then
        assertThat(hexes, containsInAnyOrder(center, hex1, hex2, hex3, hex4, hex5, hex6));
    }
    
    public void shouldReturnPartialRingList() {
        // given
        grid.put(hex1);
        grid.put(hex4);
        
        // when
        Collection<TestHex> hexes = grid.ringAround(center, 1);
        
        // then
        assertThat(hexes, containsInAnyOrder(hex1, hex4));
    }
    
    public void shouldReturnEmptyRingList() {
        // given
        grid.put(hex1);
        grid.put(hex4);
        
        // when
        Collection<TestHex> hexes = grid.ringAround(center, 2);
        
        // then
        assertThat(hexes, is(empty()));
    }
            
}
