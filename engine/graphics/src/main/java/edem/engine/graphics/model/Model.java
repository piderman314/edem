package edem.engine.graphics.model;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edem.engine.graphics.obj.ObjException;
import edem.engine.graphics.obj.data.Face.Indices;
import edem.engine.graphics.obj.data.Vertex;
import edem.engine.graphics.obj.data.VertexNormal;
import edem.engine.graphics.obj.data.VertexTexture;
import lombok.AccessLevel;
import lombok.Getter;

import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.BufferUtils;

@Getter(AccessLevel.PACKAGE)
public class Model {

    private final FloatBuffer vertexBuffer;
    private final IntBuffer indexBuffer;
    
    public Model(List<Vertex> vertexList, List<VertexTexture> vertexTextureList, List<VertexNormal> vertexNormalList, List<Indices> indexList) throws ObjException {
        Map<BufferType, Number[]> arrays = createArrays(vertexList, vertexTextureList, vertexNormalList, indexList);
        
        float[] vertexArray = ArrayUtils.toPrimitive((Float[]) arrays.get(BufferType.VERTEX));
        vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray);
        vertexBuffer.flip();
        
        int[] indexArray = ArrayUtils.toPrimitive((Integer[]) arrays.get(BufferType.INDEX));
        indexBuffer = BufferUtils.createIntBuffer(indexArray.length);
        indexBuffer.put(indexArray);
        indexBuffer.flip();
    }
    
    private Map<BufferType, Number[]> createArrays(
            List<Vertex> vertexList, List<VertexTexture> vertexTextureList, List<VertexNormal> vertexNormalList, List<Indices> indicesList) throws ObjException {
        Map<Indices, Integer> indexMap = new HashMap<>();
        
        List<Float> vertexFloatList = new ArrayList<>();
        List<Float> vertexTextureFloatList = new ArrayList<>();
        List<Float> vertexNormalFloatList = new ArrayList<>();
        List<Integer> indexIntBuffer = new ArrayList<>();
        
        int index = 0;
        for (Indices indices : indicesList) {
            if (!indexMap.containsKey(indices)) {
                indexIntBuffer.add(indexMap.get(indices));
                continue;
            }
            
            try {
                if (indices.getVertexIndex() != 0) {
                    vertexFloatList.addAll(vertexList.get(indices.getVertexIndex()).getCoordinateList());
                } else {
                    vertexFloatList.addAll(Vertex.EMPTY_VERTEX.getCoordinateList());
                }

                if (indices.getVertexTextureIndex() != 0) {
                    vertexTextureFloatList.addAll(vertexTextureList.get(indices.getVertexTextureIndex()).getCoordinateList());
                } else {
                    vertexTextureFloatList.addAll(VertexTexture.EMPTY_VERTEX_TEXTURE.getCoordinateList());
                }

                if (indices.getVertexNormalIndex() != 0) {
                    vertexNormalFloatList.addAll(vertexNormalList.get(indices.getVertexNormalIndex()).getCoordinateList());
                } else {
                    vertexNormalFloatList.addAll(VertexNormal.EMPTY_VERTEX_NORMAL.getCoordinateList());
                }

                index++;
                indexMap.put(indices, index);
                indexIntBuffer.add(index);
            } catch (IndexOutOfBoundsException ioobe) {
                throw new ObjException("Trying to index " + indices + " but there is not enough data.", ioobe);
            }
        }
        
        List<Float> finalVertexBuffer = new ArrayList<>();

        // vertices will always be there
        finalVertexBuffer.addAll(vertexFloatList);
        
        if (vertexTextureFloatList.parallelStream().anyMatch(f -> f != 0.0f)) {
            finalVertexBuffer.addAll(vertexTextureFloatList);
        }
        
        if (vertexNormalFloatList.parallelStream().anyMatch(f -> f != 0.0f)) {
            finalVertexBuffer.addAll(vertexNormalFloatList);
        }
        
        Map<BufferType, Number[]> bufferMap = new HashMap<>();
        bufferMap.put(BufferType.VERTEX, finalVertexBuffer.toArray(new Number[]{}));
        bufferMap.put(BufferType.INDEX, indexIntBuffer.toArray(new Number[]{}));
        
        return bufferMap;
    }
    
    public void uploadToGPU() {
        
    }
    
    public void render() {
        
    }
    
    private static enum BufferType {
        VERTEX,
        INDEX,
        ;
    }
    
}
