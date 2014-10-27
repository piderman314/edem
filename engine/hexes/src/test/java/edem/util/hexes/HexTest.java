package edem.util.hexes;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@Test
public class HexTest {

    private TestHex hex1;
    private TestHex hex2;
    private TestHex hex3;
    private TestHex hex4;
    
    @BeforeTest
    public void init() {
        hex1 = new TestHex();
        hex1.setIndex(new Index(1, 1, -2));
        
        hex2 = new TestHex();
        hex2.setIndex(new Index(0, 2, -2));
        
        hex3 = new TestHex();
        hex3.setIndex(new Index(2, 2, -4));
        
        hex4 = new TestHex();
        hex4.setIndex(new Index(0, 3, -3));
    }
    
    public void shouldBeNeighbours() {
        // when
        boolean neighbours = hex1.neighbours(hex2);
        
        // then
        assertThat(neighbours, is(true));
    }
    
    public void shouldNotBeNeighbours() {
        // when
        boolean neighbours = hex2.neighbours(hex3);
        
        // then
        assertThat(neighbours, is(false));
    }
    
    public void shouldNotBeNeighboursSameHex() {
        // when
        boolean neighbours = hex2.neighbours(hex2);
        
        // then
        assertThat(neighbours, is(false));
    }
    
    public void shouldBeNeighboursDiagonally() {
        // when
        boolean neighbours = hex1.neighboursDiagonally(hex4);
        
        // then
        assertThat(neighbours, is(true));
    }
    
    public void shouldNotBeNeighboursDiagonally() {
        // when
        boolean neighbours = hex2.neighboursDiagonally(hex3);
        
        // then
        assertThat(neighbours, is(false));
    }
    
    public void shouldNotBeNeighboursSameHexDiagonally() {
        // when
        boolean neighbours = hex2.neighboursDiagonally(hex2);
        
        // then
        assertThat(neighbours, is(false));
    }
    
    private static class TestHex extends AbstractHex {
        //
    }
    
}
