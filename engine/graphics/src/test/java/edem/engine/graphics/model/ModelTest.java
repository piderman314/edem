package edem.engine.graphics.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

import edem.engine.graphics.obj.ObjException;
import edem.engine.graphics.obj.data.Face.Indices;
import edem.engine.graphics.obj.data.Vertex;
import edem.engine.graphics.obj.data.VertexNormal;
import edem.engine.graphics.obj.data.VertexTexture;

import org.testng.annotations.Test;

@Test
public class ModelTest {

    public void shouldCreateBuffersVerticesOnly() throws ObjException {
        // given
        Vertex vertex1 = new Vertex();
        vertex1.parseLine("v 1.0 2.0 3.0");
        
        Vertex vertex2 = new Vertex();
        vertex2.parseLine("v 4.0 5.0 6.0 7.0");

        Indices index1 = Indices.of(2, 0, 0);
        Indices index2 = Indices.of(1, 0, 0);
        
        // when
        Model model = new Model(Arrays.asList(vertex1, vertex2), null, null, Arrays.asList(index1, index2));
        
        // then
        assertVertexBuffer(model, new float[]{4.0f, 5.0f, 6.0f, 7.0f, 1.0f, 2.0f, 3.0f, 1.0f});
        assertIndexBuffer(model, new int[]{1, 2});
    }
    
    public void shouldCreateBuffersVerticesOnlyWithRepeatingIndices() throws ObjException {
        // given
        Vertex vertex1 = new Vertex();
        vertex1.parseLine("v 1.0 2.0 3.0");
        
        Vertex vertex2 = new Vertex();
        vertex2.parseLine("v 4.0 5.0 6.0 7.0");

        Indices index1 = Indices.of(2, 0, 0);
        Indices index2 = Indices.of(1, 0, 0);
        Indices index3 = Indices.of(2, 0, 0);
        
        // when
        Model model = new Model(Arrays.asList(vertex1, vertex2), null, null, Arrays.asList(index1, index2, index3));
        
        // then
        assertVertexBuffer(model, new float[]{4.0f, 5.0f, 6.0f, 7.0f, 1.0f, 2.0f, 3.0f, 1.0f});
        assertIndexBuffer(model, new int[]{1, 2, 1});
    }
    
    public void shouldCreateBuffersAllVertices() throws ObjException {
        // given
        Vertex vertex = new Vertex();
        vertex.parseLine("v 1.0 2.0 3.0 4.0");

        VertexTexture vertexTexture = new VertexTexture();
        vertexTexture.parseLine("vt 5.0 6.0 7.0");
        
        VertexNormal vertexNormal = new VertexNormal();
        vertexNormal.parseLine("vn 1.1 1.2 -3.0");

        Indices index = Indices.of(1, 1, 1);
        
        // when
        Model model = new Model(Arrays.asList(vertex), Arrays.asList(vertexTexture), Arrays.asList(vertexNormal), Arrays.asList(index));
        
        // then
        assertVertexBuffer(model, new float[]{1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 1.1f, 1.2f, -3.0f});
        assertIndexBuffer(model, new int[]{1});
    }
    
    public void shouldCreateBuffersAllVerticesMixedIndices() throws ObjException {
        // given
        Vertex vertex = new Vertex();
        vertex.parseLine("v 1.0 2.0 3.0 4.0");

        VertexTexture vertexTexture = new VertexTexture();
        vertexTexture.parseLine("vt 5.0 6.0 7.0");
        
        VertexNormal vertexNormal = new VertexNormal();
        vertexNormal.parseLine("vn 1.1 1.2 -3.0");

        Indices index1 = Indices.of(1, 0, 1);
        Indices index2 = Indices.of(1, 1, 0);
        
        // when
        Model model = new Model(Arrays.asList(vertex), Arrays.asList(vertexTexture), Arrays.asList(vertexNormal), Arrays.asList(index1, index2));
        
        // then
        assertVertexBuffer(model, new float[]{1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f, 0.0f, 0.0f, 0.0f, 5.0f, 6.0f, 7.0f, 1.1f, 1.2f, -3.0f, 0.0f, 0.0f, 0.0f});
        assertIndexBuffer(model, new int[]{1, 2});
    }
    
    public void shouldCreateBuffersWithoutTexture() throws ObjException {
        // given
        Vertex vertex = new Vertex();
        vertex.parseLine("v 1.0 2.0 3.0 4.0");

        VertexNormal vertexNormal = new VertexNormal();
        vertexNormal.parseLine("vn 1.1 1.2 -3.0");

        Indices index = Indices.of(1, 0, 1);
        
        // when
        Model model = new Model(Arrays.asList(vertex), null, Arrays.asList(vertexNormal), Arrays.asList(index));
        
        // then
        assertVertexBuffer(model, new float[]{1.0f, 2.0f, 3.0f, 4.0f, 1.1f, 1.2f, -3.0f});
        assertIndexBuffer(model, new int[]{1});
    }
    
    @Test(expectedExceptions = ObjException.class, 
            expectedExceptionsMessageRegExp = "Trying to index Face.Indices\\(vertexIndex=2, vertexTextureIndex=0, vertexNormalIndex=0\\) but there is not enough data.")
    public void shouldThrowExceptionOnIllegalIndex() throws ObjException {
        // given
        Vertex vertex = new Vertex();
        vertex.parseLine("v 1.0 2.0 3.0");
        
        Indices index = Indices.of(2, 0, 0);
        
        // when
        Model model = new Model(Arrays.asList(vertex), null, null, Arrays.asList(index));
    }
    
    private void assertVertexBuffer(Model model, float[] expectedVertices) {
        FloatBuffer vertexBuffer = model.getVertexBuffer();
        assertThat(vertexBuffer, is(notNullValue()));
        
        float[] vertices = new float[vertexBuffer.limit()];
        vertexBuffer.get(vertices);
        assertThat(vertices, is(expectedVertices));
    }
    
    private void assertIndexBuffer(Model model, int[] expectedIndices) {
        IntBuffer indexBuffer = model.getIndexBuffer();
        assertThat(indexBuffer, is(notNullValue()));
        
        int[] indices = new int[indexBuffer.limit()];
        indexBuffer.get(indices);
        assertThat(indices, is(expectedIndices));
    }
    
}
