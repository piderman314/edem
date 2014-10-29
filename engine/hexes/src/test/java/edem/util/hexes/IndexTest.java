package edem.util.hexes;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.testng.annotations.Test;

@Test
public class IndexTest {

    public void shouldAddIndices() throws HexException {
        // given
        Index index1 = Index.of(1, 1, -2);
        Index index2 = Index.of(0, 2, -2);
        
        // when
        Index index3 = index1.add(index2);
        
        // when
        assertThat(index3, is(Index.of(1, 3, -4)));
    }
    
    @Test(expectedExceptions = HexException.class, expectedExceptionsMessageRegExp = "1, 1 and 1 do not add up to 0.")
    public void shouldBeIllegalIndex() throws HexException {
        // when
        Index.of(1, 1, 1);
    }
    
    public void shouldCalculateDistance() throws HexException {
        // given
        Index index1 = Index.of(1, 1, -2);
        Index index2 = Index.of(0, 2, -2);
        
        // when
        int distance = index1.distanceTo(index2);
        
        // then
        assertThat(distance, is(1));
    }
        
}
