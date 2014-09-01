package edem.engine.graphics.obj.data;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import edem.engine.graphics.obj.ObjException;

import org.testng.annotations.Test;

@Test
public class FloatVectorTest {

    public void shouldParseThreeVertices() throws ObjException {
        // given
        String line = " test 1.0 -1.0 3.0";
        TestFloatVector testFloatVector = new TestFloatVector();
        
        // when
        testFloatVector.parseLine(line);
        
        // then
        assertThat(testFloatVector.getX(), is(1.0f));
        assertThat(testFloatVector.getY(), is(-1.0f));
        assertThat(testFloatVector.getZ(), is(3.0f));
        assertThat(testFloatVector.getW(), is(1.0f));
    }
    
    public void shouldParseFourVertices() throws ObjException {
        // given
        String line = " test 1.0  -1.0      3.0 2.5";
        TestFloatVector testFloatVector = new TestFloatVector();
        
        // when
        testFloatVector.parseLine(line);
        
        // then
        assertThat(testFloatVector.getX(), is(1.0f));
        assertThat(testFloatVector.getY(), is(-1.0f));
        assertThat(testFloatVector.getZ(), is(3.0f));
        assertThat(testFloatVector.getW(), is(2.5f));
    }
    
    @Test(expectedExceptions = ObjException.class, expectedExceptionsMessageRegExp = "Cannot parse empty line")
    public void shouldThrowExceptionOnEmptyLine() throws ObjException {
        // given
        String line = "";
        TestFloatVector testFloatVector = new TestFloatVector();
        
        // when
        testFloatVector.parseLine(line);
    }
    
    @Test(expectedExceptions = ObjException.class, expectedExceptionsMessageRegExp = "Expected 3 or 4 vertices/weights")
    public void shouldThrowExceptionOnIncorrectNumberOfVertices() throws ObjException {
        // given
        String line = " test 1.0 2.0";
        TestFloatVector testFloatVector = new TestFloatVector();
        
        // when
        testFloatVector.parseLine(line);
    }
    
    @Test(expectedExceptions = ObjException.class, expectedExceptionsMessageRegExp = "Got a blaat line, but expected a test line")
    public void shouldThrowExceptionOnIncorrectPrefix() throws ObjException {
        // given
        String line = " blaat 1.0 2.0 3.0";
        TestFloatVector testFloatVector = new TestFloatVector();
        
        // when
        testFloatVector.parseLine(line);
    }
    
    private static final class TestFloatVector extends FloatVector {
        
        public TestFloatVector() {
            super(4);
        }
        
        @Override
        protected String getPrefix() {
            return "test";
        }
        
    }
    
}
