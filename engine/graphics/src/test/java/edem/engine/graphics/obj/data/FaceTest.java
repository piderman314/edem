package edem.engine.graphics.obj.data;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import edem.engine.graphics.obj.ObjException;
import edem.engine.graphics.obj.data.Face.Indices;

import org.testng.annotations.Test;

@Test
public class FaceTest {

    public void shouldParseSingleIndex() throws ObjException {
        // given
        String line = "f 1 2 3";
        Face face = new Face();
        
        // when
        face.parseLine(line);
        
        // then
        assertThat(face.getIndicesList(), contains(Indices.of(1, 0, 0), Indices.of(2, 0, 0), Indices.of(3, 0, 0)));
    }
    
    public void shouldParseTwoIndices() throws ObjException {
        // given
        String line = "f  1/2 2/3  3/4";
        Face face = new Face();
        
        // when
        face.parseLine(line);
        
        // then
        assertThat(face.getIndicesList(), contains(Indices.of(1, 2, 0), Indices.of(2, 3, 0), Indices.of(3, 4, 0)));
    }
    
    public void shouldParseThreeIndices() throws ObjException {
        // given
        String line = "f 1/2/3 2/3/4 3/4/5";
        Face face = new Face();
        
        // when
        face.parseLine(line);
        
        // then
        assertThat(face.getIndicesList(), contains(Indices.of(1, 2, 3), Indices.of(2, 3, 4), Indices.of(3, 4, 5)));
    }
    
    public void shouldParseVertexAndNormalOnly() throws ObjException {
        // given
        String line = "f 1//3 2//4 3//5";
        Face face = new Face();
        
        // when
        face.parseLine(line);
        
        // then
        assertThat(face.getIndicesList(), contains(Indices.of(1, 0, 3), Indices.of(2, 0, 4), Indices.of(3, 0, 5)));
    }
    
    @Test(expectedExceptions = ObjException.class, expectedExceptionsMessageRegExp = "Cannot parse empty line")
    public void shouldThrowExceptionOnEmptyLine() throws ObjException {
        // given
        String line = "";
        Face face = new Face();
        
        // when
        face.parseLine(line);
    }
    
    @Test(expectedExceptions = ObjException.class, expectedExceptionsMessageRegExp = "Expected at least 3 vertices")
    public void shouldThrowExceptionOnIncorrectNumberOfVertices() throws ObjException {
        // given
        String line = " f 1 2";
        Face face = new Face();
        
        // when
        face.parseLine(line);
    }
    
    @Test(expectedExceptions = ObjException.class, expectedExceptionsMessageRegExp = "Got a blaat line, but expected a f line")
    public void shouldThrowExceptionOnIncorrectPrefix() throws ObjException {
        // given
        String line = " blaat 1 2 3";
        Face face = new Face();
        
        // when
        face.parseLine(line);
    }
    
}
